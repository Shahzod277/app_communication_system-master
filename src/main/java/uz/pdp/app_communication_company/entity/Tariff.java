package uz.pdp.app_communication_company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean legalOrPhysical;

    private  double price;

    private  String name;

    private  double tariffTransitionPrice;

    private Integer validityPeriod;

    private double megabytes;
    private double minutesWithinNetwork;
    private double minutesWithoutNetwork;
    private double sms;


    private double megabytesOnServicesExpired;
    private double minutesWithinNetworkOnServicesExpired;
    private double minutesWithoutNetworkOnServicesExpired;
    private double smsOnServicesExpired;




}
