package uz.pdp.app_communication_company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"company_code","sim_card_number"})})
public class SimCard implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    private String startWith="+998";

    @Column(nullable = false)
    private String companyCode;//93 or 94...

    @Column(nullable = false)
    private String simCardNumber;

    @Column(nullable = false)
    private String pinCode;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Tariff tariff;

    @ManyToMany
    private List<Packages> packages;

    @ManyToMany
    private Set<Service> service;

    @Column(nullable = false, unique = true)
    private String simCardCode;

    private double balance;

    @ManyToMany
    private Set<Role> roles;

    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean active;

    public void setActive(boolean active) {
        this.active = this.balance>0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.pinCode;
    }

    @Override
    public String getUsername() {
        return this.simCardCode;
    }

    //Akkountning muddati o'tmaganligi
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    //Akkaunt bloklanganligi
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }


    //Akkaunt ishonchliligi
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
