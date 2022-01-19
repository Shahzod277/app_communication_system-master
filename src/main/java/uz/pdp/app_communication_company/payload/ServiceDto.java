package uz.pdp.app_communication_company.payload;

import lombok.Data;
import uz.pdp.app_communication_company.entity.ServiceCategory;
import uz.pdp.app_communication_company.entity.template.ServiceType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
public class ServiceDto {
    @NotNull
    private ServiceType serviceType;

    @NotNull
    private Integer validityPeriod;

    @NotNull
    private Integer serviceCategoryId;

    @NotNull
    private double servicePrice;

    private String description;


}
