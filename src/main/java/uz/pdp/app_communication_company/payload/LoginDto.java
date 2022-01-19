package uz.pdp.app_communication_company.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;

}
