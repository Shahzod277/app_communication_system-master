package uz.pdp.app_communication_company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.PackageDto;
import uz.pdp.app_communication_company.service.PackageService;


@RestController
@RequestMapping("/api/package")
public class PackageController {

    @Autowired
    PackageService packageService;

    @PostMapping("/add")
    public HttpEntity<?> add(@PathVariable PackageDto packageDto){
        ApiResponse apiResponse = packageService.add(packageDto);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @GetMapping("/buyPackage/{id}")
    public HttpEntity<?> buyPackage(@PathVariable Integer id){
        ApiResponse apiResponse = packageService.buyPackage(id);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@RequestBody PackageDto packageDto, @PathVariable Integer id){
        ApiResponse apiResponse = packageService.update(packageDto, id);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }


}
