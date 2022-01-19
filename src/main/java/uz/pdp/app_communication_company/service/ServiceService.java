package uz.pdp.app_communication_company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_communication_company.entity.Employer;
import uz.pdp.app_communication_company.entity.Role;
import uz.pdp.app_communication_company.entity.ServiceCategory;
import uz.pdp.app_communication_company.entity.template.RoleName;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.ServiceDto;
import uz.pdp.app_communication_company.repository.ServiceCategoryRepository;
import uz.pdp.app_communication_company.repository.ServiceRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    public ApiResponse addService(ServiceDto serviceDto) {
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.SIM_CARD_MANAGER)) {
                Optional<ServiceCategory> optionalServiceCategory = serviceCategoryRepository.findById(serviceDto.getServiceCategoryId());
                if (!optionalServiceCategory.isPresent())
                    return new ApiResponse("Kategoriya topilmadi",false);
                uz.pdp.app_communication_company.entity.Service service=new uz.pdp.app_communication_company.entity.Service();
                service.setServiceType(serviceDto.getServiceType());
                service.setServiceCategory(optionalServiceCategory.get());
                service.setServicePrice(serviceDto.getServicePrice());
                service.setDescription(serviceDto.getDescription());
                service.setValidityPeriod(serviceDto.getValidityPeriod());
                serviceRepository.save(service);
                return new ApiResponse("Service saqlandi",true);
            }
        }
return new ApiResponse("Siz ximat qo'sha olmaysiz",false);
    }

    public ApiResponse updateService(ServiceDto serviceDto, Integer id) {
        Optional<uz.pdp.app_communication_company.entity.Service> optionalService = serviceRepository.findById(id);
        if (!optionalService.isPresent())
            return new ApiResponse("Service topilmadi", false);
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.SIM_CARD_MANAGER)) {
                Optional<ServiceCategory> optionalServiceCategory = serviceCategoryRepository.findById(serviceDto.getServiceCategoryId());
                if (!optionalServiceCategory.isPresent())
                    return new ApiResponse("Kategoriya topilmadi",false);
                uz.pdp.app_communication_company.entity.Service service=optionalService.get();
                service.setServiceType(serviceDto.getServiceType());
                service.setServiceCategory(optionalServiceCategory.get());
                service.setServicePrice(serviceDto.getServicePrice());
                service.setDescription(serviceDto.getDescription());
                service.setValidityPeriod(serviceDto.getValidityPeriod());
                serviceRepository.save(service);
                return new ApiResponse("Service o'zgartirildi",true);
            }
        }
        return new ApiResponse("Siz ximat qo'sha olmaysiz",false);
    }

    public ApiResponse deleteService(Integer id) {
        Optional<uz.pdp.app_communication_company.entity.Service> optionalService = serviceRepository.findById(id);
        if (!optionalService.isPresent())
            return new ApiResponse("Service topilmadi", false);
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.SIM_CARD_MANAGER)||
                    role.getRoleName().equals(RoleName.DIRECTOR)) {
                serviceRepository.deleteById(id);
                return new ApiResponse("Service o'chirildi",true);
            }
        }
        return new ApiResponse("Siz ximatni o'chira olmaysiz",false);
    }
}
