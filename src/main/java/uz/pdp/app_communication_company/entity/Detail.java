package uz.pdp.app_communication_company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Detail {
    @Id
    @GeneratedValue
    private UUID id;

    private Date date;

    @ManyToOne
    private SimCard simCard;

    private String contentOfOperation;

}
