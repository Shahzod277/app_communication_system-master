package uz.pdp.app_communication_company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_communication_company.entity.Employer;
import uz.pdp.app_communication_company.entity.Tuniket;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.TuniketDto;
import uz.pdp.app_communication_company.repository.EmployerRepository;
import uz.pdp.app_communication_company.repository.TuniketRepository;


import java.util.Optional;

@Service
public class TuniketService {
    @Autowired
    TuniketRepository tuniketRepository;
    @Autowired
    EmployerRepository employerRepository;

    public ApiResponse add(TuniketDto tuniketDto) {
        Optional<Employer> optionalEmployer = employerRepository.findById(tuniketDto.getEmployerId());
        if (!optionalEmployer.isPresent())
            return new ApiResponse("Siz korxona hodimlari orasida yo'qsiz", false);
        Tuniket tuniket=new Tuniket();
        tuniket.setComeOrOut(tuniketDto.isComeOrOut());
        tuniket.setEmployer(optionalEmployer.get());
        return new ApiResponse("Tuniket muaffaqiyatli saqlandi",true);
    }
}
