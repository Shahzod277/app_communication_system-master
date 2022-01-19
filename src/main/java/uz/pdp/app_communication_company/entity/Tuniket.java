package uz.pdp.app_communication_company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tuniket {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private boolean comeOrOut;// true kirgan, false chiqqan

    @ManyToOne(optional = false)
    private Employer employer;

    @Column(nullable = false,updatable = false)
    @CreatedDate
    private Date date;
}
