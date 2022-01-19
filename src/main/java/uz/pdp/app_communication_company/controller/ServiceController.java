package uz.pdp.app_communication_company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.ServiceDto;
import uz.pdp.app_communication_company.service.ServiceService;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    ServiceService serviceService;

    @PostMapping("/add")
    public HttpEntity<?> addService(@RequestBody ServiceDto serviceDto){
        ApiResponse apiResponse=serviceService.addService(serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }


    @PutMapping("/update/{id}")
    public HttpEntity<?> updateService(@RequestBody ServiceDto serviceDto, @PathVariable Integer id){
        ApiResponse apiResponse=serviceService.updateService(serviceDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }


    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteService(@PathVariable Integer id){
        ApiResponse apiResponse=serviceService.deleteService( id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }
}
