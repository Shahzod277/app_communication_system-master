package uz.pdp.app_communication_company.payload;

import lombok.Data;
import uz.pdp.app_communication_company.entity.template.PackageType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PackageDto {
    @NotNull
    private  String name;

    @NotNull
    private boolean addOnBuyNew;

    @NotNull
    private PackageType packageType;

    @NotNull
    private double amount;


    @NotNull
    private double packagePrice;

    @NotNull
    private Integer validityPeriod;

    private List<Integer> tariffIds;
}

