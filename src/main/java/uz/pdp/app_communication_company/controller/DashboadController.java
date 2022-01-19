package uz.pdp.app_communication_company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.service.DashboadService;

import java.util.Date;


@RestController
@RequestMapping("/api/dashboad")
public class DashboadController {

    @Autowired
    DashboadService dashboadService;

    @GetMapping("/getBuySimCard")
    public HttpEntity<?> getBuySimCard(){
        ApiResponse apiResponse=dashboadService.getBuySimCard();
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @GetMapping("/dailyIncome{date}")
    public HttpEntity<?> dailyIncome(@PathVariable Date date){
        ApiResponse apiResponse=dashboadService.dailyIncome(date);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }
    @GetMapping("/monthlyIncome{mothId}")
    public HttpEntity<?> monthlyIncome(@PathVariable Integer mothId){
        ApiResponse apiResponse=dashboadService.monthlyIncome(mothId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }


}
