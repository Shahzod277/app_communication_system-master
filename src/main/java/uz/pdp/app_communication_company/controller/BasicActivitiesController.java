package uz.pdp.app_communication_company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_communication_company.entity.Tariff;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.CallDto;
import uz.pdp.app_communication_company.service.BasicActivitiesService;


@RestController
@RequestMapping("/api/activities")
public class BasicActivitiesController {

    @Autowired
    BasicActivitiesService basicActivitiesService;

    @PostMapping("/call")
    public HttpEntity<?> call(@RequestBody CallDto callDto){
        ApiResponse apiResponse = basicActivitiesService.call(callDto);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PostMapping("/mbSpending/{mbOfAmount}")
    public HttpEntity<?> mbSpending(@PathVariable double mbOfAmount){
        ApiResponse apiResponse = basicActivitiesService.mbSpending(mbOfAmount);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PutMapping("/smsSpending/{amount}")
    public HttpEntity<?> smsSpending(@PathVariable double amount){
        ApiResponse apiResponse = basicActivitiesService.smsSpending(amount);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }



}
