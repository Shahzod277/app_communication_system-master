package uz.pdp.app_communication_company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.app_communication_company.entity.template.PackageType;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Packages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private  String name;

    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    @Column(nullable = false)
    private double packagePrice;

    @Column(nullable = false)
    private Integer validityPeriod;
    @Column(nullable = false)
    private double amount;

    @ManyToMany
    private List<Tariff> tariff;

    private boolean addOnBuyNew; // true bo'lsa yangi paket sotib olganda eskisiniki qo'shiladi, false da yo'q

}
