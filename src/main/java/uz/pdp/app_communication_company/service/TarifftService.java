package uz.pdp.app_communication_company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_communication_company.entity.*;
import uz.pdp.app_communication_company.entity.template.RoleName;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.repository.EmployerRepository;
import uz.pdp.app_communication_company.repository.PaymentRepository;
import uz.pdp.app_communication_company.repository.SimCardRepository;
import uz.pdp.app_communication_company.repository.TariffRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class TarifftService {
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    SimCardRepository simCardRepository;
    @Autowired
    PaymentRepository paymentRepository;


    public ApiResponse addTariff(Tariff tariff) {
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {
                Tariff newTariff=new Tariff();
                newTariff.setMegabytes(tariff.getMegabytes());
                newTariff.setLegalOrPhysical(tariff.isLegalOrPhysical());
                newTariff.setMegabytesOnServicesExpired(tariff.getMegabytesOnServicesExpired());
                newTariff.setMinutesWithinNetwork(tariff.getMinutesWithinNetwork());
                newTariff.setSms(tariff.getSms());
                newTariff.setPrice(tariff.getPrice());
                newTariff.setValidityPeriod(tariff.getValidityPeriod());
                newTariff.setMinutesWithoutNetwork(tariff.getMinutesWithoutNetwork());
                newTariff.setMinutesWithinNetworkOnServicesExpired(tariff.getMinutesWithinNetworkOnServicesExpired());
                newTariff.setSmsOnServicesExpired(tariff.getSmsOnServicesExpired());
                newTariff.setTariffTransitionPrice(tariff.getTariffTransitionPrice());
                newTariff.setMinutesWithoutNetworkOnServicesExpired(tariff.getMinutesWithoutNetworkOnServicesExpired());
                tariffRepository.save(newTariff);
                return new ApiResponse("Tarif saqlandi", true);
            }
        }
        return new ApiResponse("Siz tarif rejasini qo'sha olmaysiz",false);
    }

    public ApiResponse buyTariff(Integer id) {
        SimCard simCardInSystem = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Tariff> optionalTariff = tariffRepository.findById(id);
        if (!optionalTariff.isPresent())
            return new ApiResponse("Tariff topilmadi", false);
        if (simCardInSystem.getBalance()>=(optionalTariff.get().getTariffTransitionPrice()+optionalTariff.get().getPrice())){
            simCardInSystem.setTariff(optionalTariff.get());
            simCardInSystem.setBalance(simCardInSystem.getBalance()-(optionalTariff.get().getTariffTransitionPrice()+optionalTariff.get().getPrice()));
            simCardRepository.save(simCardInSystem);
            Payment payment=new Payment();
            payment.setPaymentAmount(optionalTariff.get().getPrice());
            payment.setSimCard(simCardInSystem);
            payment.setDate(new Date());
            payment.setWhatFor(simCardInSystem.getUsername()+" dan "+optionalTariff.get().getName()+" tarifi uchun oylik to'lov");
            paymentRepository.save(payment);
            Detail detail=new Detail();
            detail.setDate(new Date());
            detail.setSimCard(simCardInSystem);
            detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+optionalTariff.get().getName()+" sotib oldi");
            return new ApiResponse("Tarif faollashtirildi", true);
        }

        return new ApiResponse("Mablag' yetarli emas", false);
    }

    public ApiResponse update(Tariff tariff, Integer id) {
        Optional<Tariff> optionalTariff = tariffRepository.findById(id);
        if (!optionalTariff.isPresent())
            return new ApiResponse("Tarif topilmadi", false);
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {
                Tariff newTariff=optionalTariff.get();
                newTariff.setMegabytes(tariff.getMegabytes());
                newTariff.setLegalOrPhysical(tariff.isLegalOrPhysical());
                newTariff.setMegabytesOnServicesExpired(tariff.getMegabytesOnServicesExpired());
                newTariff.setMinutesWithinNetwork(tariff.getMinutesWithinNetwork());
                newTariff.setSms(tariff.getSms());
                newTariff.setPrice(tariff.getPrice());
                newTariff.setValidityPeriod(tariff.getValidityPeriod());
                newTariff.setMinutesWithoutNetwork(tariff.getMinutesWithoutNetwork());
                newTariff.setMinutesWithinNetworkOnServicesExpired(tariff.getMinutesWithinNetworkOnServicesExpired());
                newTariff.setSmsOnServicesExpired(tariff.getSmsOnServicesExpired());
                newTariff.setTariffTransitionPrice(tariff.getTariffTransitionPrice());
                newTariff.setMinutesWithoutNetworkOnServicesExpired(tariff.getMinutesWithoutNetworkOnServicesExpired());
                tariffRepository.save(newTariff);
                return new ApiResponse("Tarif saqlandi", true);
            }
        }
        return new ApiResponse("Siz tarif rejasini o'zgartira olmaysiz olmaysiz",false);

    }

}
