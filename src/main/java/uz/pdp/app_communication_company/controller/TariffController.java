package uz.pdp.app_communication_company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_communication_company.entity.Tariff;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.service.TarifftService;


@RestController
@RequestMapping("/api/tariff")
public class TariffController {

    @Autowired
    TarifftService tarifftService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody Tariff tariff){
        ApiResponse apiResponse = tarifftService.addTariff(tariff);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PostMapping("/buyTariff/{id}")
    public HttpEntity<?> buyTariff(@PathVariable Integer id){
        ApiResponse apiResponse = tarifftService.buyTariff(id);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@RequestBody Tariff tariff,@PathVariable Integer id){
        ApiResponse apiResponse = tarifftService.update(tariff, id);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

}
