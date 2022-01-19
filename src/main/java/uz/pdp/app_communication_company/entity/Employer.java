package uz.pdp.app_communication_company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    private String username;

    private String password;

    @ManyToOne
    private Branch workplace;

    @ManyToMany
    private Set<Role> roles;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled=true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return this.enabled;
    }
}
