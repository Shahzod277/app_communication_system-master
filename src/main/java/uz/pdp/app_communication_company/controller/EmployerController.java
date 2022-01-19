package uz.pdp.app_communication_company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_communication_company.entity.Employer;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.EmployerDto;
import uz.pdp.app_communication_company.service.EmployerService;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    @Autowired
    EmployerService employerService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody EmployerDto employerDto){
        ApiResponse apiResponse=employerService.addEmployer(employerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse=employerService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@RequestBody EmployerDto employerDto, @PathVariable Integer id){
        ApiResponse apiResponse=employerService.update(employerDto,id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }
}
