package uz.pdp.app_communication_company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_communication_company.entity.Branch;
import uz.pdp.app_communication_company.entity.Employer;
import uz.pdp.app_communication_company.entity.Role;
import uz.pdp.app_communication_company.entity.template.RoleName;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.EmployerDto;
import uz.pdp.app_communication_company.repository.BranchRepository;
import uz.pdp.app_communication_company.repository.EmployerRepository;
import uz.pdp.app_communication_company.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployerService {

    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BranchRepository branchRepository;



    public ApiResponse addEmployer(EmployerDto employerDto) {
        boolean existsByUsername = employerRepository.existsByUsername(employerDto.getUsername());
        if (existsByUsername)
            return new ApiResponse("Bunday username ro'yxatga olib bo'lingan", false);
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                Optional<Branch> optionalBranch = branchRepository.findById(employerDto.getWorkplaceId());
                if (!optionalBranch.isPresent()) {
                    return new ApiResponse("Filial topilmadi",false);
                }
                Set<Role> roleSet=new HashSet<>();
                Set<Integer> rolesId = employerDto.getRolesId();
                for (Integer integer : rolesId) {
                    Optional<Role> optionalRole = roleRepository.findById(integer);
                    if (!optionalRole.isPresent())
                        return new ApiResponse("Role topilmadi", false);
                    roleSet.add(optionalRole.get());
                }
                Employer employer=new Employer();
                employer.setFullName(employerDto.getFullName());
                employer.setPassword(employerDto.getPassword());
                employer.setUsername(employerDto.getUsername());
                employer.setRoles(roleSet);
                employer.setWorkplace(optionalBranch.get());
                employerRepository.save(employer);
                return new ApiResponse("Hodim ro'yxatga olindi",true);
            }
        }
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.MANAGER)||role.getRoleName().equals(RoleName.EMPLOYEE_MANAGER)) {
                Set<Role> roleSet=new HashSet<>();
                Set<Integer> employerDtoRolesId = employerDto.getRolesId();
                for (Integer integer : employerDtoRolesId) {
                    Optional<Role> optionalRole = roleRepository.findById(integer);
                    if (!optionalRole.isPresent()) {
                        return new ApiResponse("Role topilmadi",false);
                    }
                    if (optionalRole.get().getRoleName().equals(RoleName.DIRECTOR)||optionalRole.get().getRoleName().equals(RoleName.MANAGER)){
                        return new ApiResponse("Siz direktor va manager qo'sha olmaysiz", false);
                    }
                    roleSet.add(optionalRole.get());
                }
                Optional<Branch> optionalBranch = branchRepository.findById(employerDto.getWorkplaceId());
                if (!optionalBranch.isPresent()) {
                    return new ApiResponse("Filial topilmadi.",false);
                }
                Employer employer=new Employer();
                employer.setFullName(employerDto.getFullName());
                employer.setPassword(employerDto.getPassword());
                employer.setUsername(employerDto.getUsername());
                employer.setRoles(roleSet);
                employer.setWorkplace(optionalBranch.get());
                employerRepository.save(employer);
                return new ApiResponse("Hodim ro'yxatga olindi",true);
            }
        }

        return new ApiResponse("Sizda hodim qo'shish huquqi yo'q", false);
    }

    public ApiResponse delete(Integer id) {
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)||role.getRoleName().equals(RoleName.EMPLOYEE_MANAGER)) {
                employerRepository.deleteById(id);
                return new ApiResponse("Hodim o'chirildi",true);
            }
        }
        return new ApiResponse("Sizda hodimni o'chirish huquqi yo'q",false);

    }

    public ApiResponse update(EmployerDto employerDto, Integer id) {
        Optional<Employer> optionalEmployer = employerRepository.findById(id);
        if (!optionalEmployer.isPresent())
            return new ApiResponse("Hodim topilmadi", false);
        boolean existsByUsername = employerRepository.existsByUsername(employerDto.getUsername());
        if (existsByUsername)
            return new ApiResponse("Bunday username ro'yxatga olib bo'lingan", false);
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                Optional<Branch> optionalBranch = branchRepository.findById(employerDto.getWorkplaceId());
                if (!optionalBranch.isPresent()) {
                    return new ApiResponse("Filial topilmadi",false);
                }
                Set<Role> roleSet=new HashSet<>();
                Set<Integer> rolesId = employerDto.getRolesId();
                for (Integer integer : rolesId) {
                    Optional<Role> optionalRole = roleRepository.findById(integer);
                    if (!optionalRole.isPresent())
                        return new ApiResponse("Role topilmadi", false);
                    roleSet.add(optionalRole.get());
                }
                Employer employer=optionalEmployer.get();
                employer.setFullName(employerDto.getFullName());
                employer.setPassword(employerDto.getPassword());
                employer.setUsername(employerDto.getUsername());
                employer.setRoles(roleSet);
                employer.setWorkplace(optionalBranch.get());
                employerRepository.save(employer);
                return new ApiResponse("Hodim saqlandi",true);
            }
        }
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.MANAGER)||role.getRoleName().equals(RoleName.EMPLOYEE_MANAGER)) {
                Set<Role> roleSet=new HashSet<>();
                Set<Integer> employerDtoRolesId = employerDto.getRolesId();
                for (Integer integer : employerDtoRolesId) {
                    Optional<Role> optionalRole = roleRepository.findById(integer);
                    if (!optionalRole.isPresent()) {
                        return new ApiResponse("Role topilmadi",false);
                    }
                    if (optionalRole.get().getRoleName().equals(RoleName.DIRECTOR)||optionalRole.get().getRoleName().equals(RoleName.MANAGER)){
                        return new ApiResponse("Siz direktor va manager qo'sha olmaysiz", false);
                    }
                    roleSet.add(optionalRole.get());
                }
                Optional<Branch> optionalBranch = branchRepository.findById(employerDto.getWorkplaceId());
                if (!optionalBranch.isPresent()) {
                    return new ApiResponse("Filial topilmadi.",false);
                }
                Employer employer=optionalEmployer.get();
                employer.setFullName(employerDto.getFullName());
                employer.setPassword(employerDto.getPassword());
                employer.setUsername(employerDto.getUsername());
                employer.setRoles(roleSet);
                employer.setWorkplace(optionalBranch.get());
                employerRepository.save(employer);
                return new ApiResponse("Hodim saqlandi",true);
            }
        }

        return new ApiResponse("Sizda hodim tahrirlash huquqi yo'q", false);
    }
}
