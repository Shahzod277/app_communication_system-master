package uz.pdp.app_communication_company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(optional = false)
    private SimCard simCard;

    private double paymentAmount;

    private String whatFor;



}
