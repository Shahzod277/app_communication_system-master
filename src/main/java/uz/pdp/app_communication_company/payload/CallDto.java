package uz.pdp.app_communication_company.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CallDto {
    @NotNull
    private double duration;

    @NotNull
    private boolean direction;
}
