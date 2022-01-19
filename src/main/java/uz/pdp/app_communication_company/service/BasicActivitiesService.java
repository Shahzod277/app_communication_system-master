package uz.pdp.app_communication_company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_communication_company.entity.Detail;
import uz.pdp.app_communication_company.entity.Packages;
import uz.pdp.app_communication_company.entity.SimCard;
import uz.pdp.app_communication_company.entity.Tariff;
import uz.pdp.app_communication_company.entity.template.PackageType;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.CallDto;
import uz.pdp.app_communication_company.repository.*;

import java.util.Date;
import java.util.List;

@Service
public class BasicActivitiesService {
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    SimCardRepository simCardRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    DetailRepository detailRepository;

    public ApiResponse call(CallDto callDto) {
        SimCard simCardInSystem = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tariff tariff = simCardInSystem.getTariff();
        List<Packages> packages = simCardInSystem.getPackages();
        if (callDto.isDirection()) {//true bo'lsa bir xil yonalish
            if (tariff.getMinutesWithinNetwork()>=callDto.getDuration()) {
                tariff.setMinutesWithinNetwork(tariff.getMinutesWithinNetwork()-callDto.getDuration());
                Detail detail=new Detail();
                detail.setDate(new Date());
                detail.setSimCard(simCardInSystem);
                detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+callDto.getDuration()+"vaqt gaplashdi");
                detailRepository.save(detail);
                return new ApiResponse("Qo'ng'iroq tugadi" ,true);
            }
            double qolganVaqt=callDto.getDuration()-tariff.getMinutesWithinNetwork();
            for (Packages aPackage : packages) {
                if (aPackage.getPackageType().equals(PackageType.MINUTES)) {
                    if (aPackage.getAmount()>=qolganVaqt) {
                        aPackage.setAmount(aPackage.getAmount()-qolganVaqt);
                        Detail detail=new Detail();
                        detail.setDate(new Date());
                        detail.setSimCard(simCardInSystem);
                        detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+callDto.getDuration()+"vaqt gaplashdi");
                        detailRepository.save(detail);
                        return new ApiResponse("Qo'ng'iroq tugadi" ,true);
                    }
                    double qolganVaqt2=qolganVaqt-aPackage.getAmount();
                    if (simCardInSystem.getBalance()>=(qolganVaqt2*tariff.getMinutesWithinNetworkOnServicesExpired())) {
                        simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganVaqt2*tariff.getMinutesWithinNetworkOnServicesExpired());
                        simCardInSystem.setPackages(packages);
                        simCardInSystem.setTariff(tariff);
                        Detail detail=new Detail();
                        detail.setDate(new Date());
                        detail.setSimCard(simCardInSystem);
                        detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+callDto.getDuration()+"vaqt gaplashdi");
                        detailRepository.save(detail);
                        return new ApiResponse("Qo'ng'iroq tugadi" ,true);

                    }
                }
            }
            if (simCardInSystem.getBalance()>=(qolganVaqt*tariff.getMinutesWithinNetworkOnServicesExpired())) {
                simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganVaqt*tariff.getMinutesWithinNetworkOnServicesExpired());
                simCardInSystem.setPackages(packages);
                simCardInSystem.setTariff(tariff);
                Detail detail=new Detail();
                detail.setDate(new Date());
                detail.setSimCard(simCardInSystem);
                detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+callDto.getDuration()+"vaqt gaplashdi");
                detailRepository.save(detail);
                return new ApiResponse("Qo'ng'iroq tugadi" ,true);
        }
        return new ApiResponse("Mablag' yetarli emas",false);

    }else {
            if (tariff.getMinutesWithoutNetwork()>=callDto.getDuration()) {
                tariff.setMinutesWithoutNetwork(tariff.getMinutesWithoutNetwork()-callDto.getDuration());
                return new ApiResponse("Qo'ng'iroq tugadi" ,true);
            }
            double qolganVaqt=callDto.getDuration()-tariff.getMinutesWithoutNetwork();
            for (Packages aPackage : packages) {
                if (aPackage.getPackageType().equals(PackageType.MINUTES)) {
                    if (aPackage.getAmount()>=qolganVaqt) {
                        aPackage.setAmount(aPackage.getAmount()-qolganVaqt);
                        return new ApiResponse("Qo'ng'iroq tugadi" ,true);
                    }
                    double qolganVaqt2=qolganVaqt-aPackage.getAmount();
                    if (simCardInSystem.getBalance()>=(qolganVaqt2*tariff.getMinutesWithoutNetworkOnServicesExpired())) {
                        simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganVaqt2*tariff.getMinutesWithoutNetworkOnServicesExpired());
                        simCardInSystem.setPackages(packages);
                        simCardInSystem.setTariff(tariff);
                        return new ApiResponse("Qo'ng'iroq tugadi" ,true);

                    }
                }
            }
            if (simCardInSystem.getBalance()>=(qolganVaqt*tariff.getMinutesWithoutNetworkOnServicesExpired())) {
                simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganVaqt*tariff.getMinutesWithoutNetworkOnServicesExpired());
                simCardInSystem.setPackages(packages);
                simCardInSystem.setTariff(tariff);
                return new ApiResponse("Qo'ng'iroq tugadi" ,true);
            }
            return new ApiResponse("Mablag' yetarli emas",false);


        }
    }

    public ApiResponse mbSpending(double mbOfAmount) {
        SimCard simCardInSystem = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tariff tariff = simCardInSystem.getTariff();
        List<Packages> packages = simCardInSystem.getPackages();
            if (tariff.getMegabytes()>=mbOfAmount) {
                tariff.setMegabytes(tariff.getMegabytes()-mbOfAmount);
                Detail detail=new Detail();
                detail.setDate(new Date());
                detail.setSimCard(simCardInSystem);
                detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+mbOfAmount+"megabaytni safladi");
                detailRepository.save(detail);
                return new ApiResponse("MB yechildi" ,true);
            }
            double qolganMb=mbOfAmount-tariff.getMegabytes();
            for (Packages aPackage : packages) {
                if (aPackage.getPackageType().equals(PackageType.MB)) {
                    if (aPackage.getAmount()>=qolganMb) {
                        aPackage.setAmount(aPackage.getAmount()-qolganMb);
                        Detail detail=new Detail();
                        detail.setDate(new Date());
                        detail.setSimCard(simCardInSystem);
                        detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+mbOfAmount+"megabaytni safladi");
                        detailRepository.save(detail);
                        return new ApiResponse("MB yechildi" ,true);
                    }
                    double qolganMb2=qolganMb-aPackage.getAmount();
                    if (simCardInSystem.getBalance()>=(qolganMb2*tariff.getMegabytesOnServicesExpired())) {
                        simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganMb2*tariff.getMegabytesOnServicesExpired());
                        simCardInSystem.setPackages(packages);
                        simCardInSystem.setTariff(tariff);
                        Detail detail=new Detail();
                        detail.setDate(new Date());
                        detail.setSimCard(simCardInSystem);
                        detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+mbOfAmount+"megabaytni safladi");
                        detailRepository.save(detail);
                        return new ApiResponse("MB yechildi" ,true);

                    }
                }
            }
            if (simCardInSystem.getBalance()>=(qolganMb*tariff.getMegabytesOnServicesExpired())) {
                simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganMb*tariff.getMegabytesOnServicesExpired());
                simCardInSystem.setPackages(packages);
                simCardInSystem.setTariff(tariff);
                Detail detail=new Detail();
                detail.setDate(new Date());
                detail.setSimCard(simCardInSystem);
                detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+mbOfAmount+"megabaytni safladi");
                detailRepository.save(detail);
                return new ApiResponse("MB yechildi" ,true);
            }
            return new ApiResponse("Mablag' yetarli emas",false);

    }

    public ApiResponse smsSpending(double smsAmount) {
        SimCard simCardInSystem = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tariff tariff = simCardInSystem.getTariff();
        List<Packages> packages = simCardInSystem.getPackages();
        if (tariff.getSms()>=smsAmount) {
            tariff.setSms(tariff.getSms()-smsAmount);
            Detail detail=new Detail();
            detail.setDate(new Date());
            detail.setSimCard(simCardInSystem);
            detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+smsAmount+"sms safladi");
            detailRepository.save(detail);
            return new ApiResponse("sms yechildi" ,true);
        }
        double qolganSms=smsAmount-tariff.getSms();
        for (Packages aPackage : packages) {
            if (aPackage.getPackageType().equals(PackageType.SMS)) {
                if (aPackage.getAmount()>=qolganSms) {
                    aPackage.setAmount(aPackage.getAmount()-qolganSms);
                    Detail detail=new Detail();
                    detail.setDate(new Date());
                    detail.setSimCard(simCardInSystem);
                    detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+smsAmount+"sms safladi");
                    detailRepository.save(detail);
                    return new ApiResponse("sms yechildi" ,true);
                }
                double qolganSms2=qolganSms-aPackage.getAmount();
                if (simCardInSystem.getBalance()>=(qolganSms2*tariff.getSmsOnServicesExpired())) {
                    simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganSms2*tariff.getSmsOnServicesExpired());
                    simCardInSystem.setPackages(packages);
                    simCardInSystem.setTariff(tariff);
                    Detail detail=new Detail();
                    detail.setDate(new Date());
                    detail.setSimCard(simCardInSystem);
                    detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+smsAmount+"sms safladi");
                    detailRepository.save(detail);
                    return new ApiResponse("sms yechildi" ,true);

                }
            }
        }
        if (simCardInSystem.getBalance()>=(qolganSms*tariff.getSmsOnServicesExpired())) {
            simCardInSystem.setBalance(simCardInSystem.getBalance()-qolganSms*tariff.getSmsOnServicesExpired());
            simCardInSystem.setPackages(packages);
            simCardInSystem.setTariff(tariff);
            Detail detail=new Detail();
            detail.setDate(new Date());
            detail.setSimCard(simCardInSystem);
            detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+smsAmount+"sms safladi");
            detailRepository.save(detail);
            return new ApiResponse("sms yechildi" ,true);
        }
        return new ApiResponse("Mablag' yetarli emas",false);

    }
}
