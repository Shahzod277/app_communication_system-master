package uz.pdp.app_communication_company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.TuniketDto;
import uz.pdp.app_communication_company.service.TuniketService;


@RestController
@RequestMapping("/api/tuniket")
public class TuniketController {

    @Autowired
    TuniketService tuniketService;

    @PostMapping("/add")
    public HttpEntity<?> add(@PathVariable TuniketDto tuniketDto){
        ApiResponse apiResponse = tuniketService.add(tuniketDto);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }


}
