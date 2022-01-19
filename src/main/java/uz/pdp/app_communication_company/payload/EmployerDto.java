package uz.pdp.app_communication_company.payload;

import lombok.Data;
import uz.pdp.app_communication_company.entity.Branch;
import uz.pdp.app_communication_company.entity.Role;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class EmployerDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String fullName;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    @NotNull
    private Integer workplaceId;


    private Set<Integer> rolesId;
}
