package uz.pdp.app_communication_company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_communication_company.entity.*;
import uz.pdp.app_communication_company.entity.template.RoleName;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.payload.PackageDto;
import uz.pdp.app_communication_company.repository.*;

import java.util.*;

@Service
public class PackageService {
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    SimCardRepository simCardRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    DetailRepository detailRepository;



    public ApiResponse update(PackageDto packageDto, Integer id) {
        Optional<Packages> optionalPackages = packageRepository.findById(id);
        if (!optionalPackages.isPresent())
            return new ApiResponse("package topilmadi", false);
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.MANAGER)) {
                List<Tariff> tariffList = tariffRepository.findAllById(packageDto.getTariffIds());
                Packages packages=new Packages();
                packages.setPackagePrice(packageDto.getPackagePrice());
                packages.setPackageType(packageDto.getPackageType());
                packages.setAmount(packageDto.getAmount());
                packages.setName(packageDto.getName());
                packages.setTariff(tariffList);
                packages.setValidityPeriod(packageDto.getValidityPeriod());
                packages.setAddOnBuyNew(packageDto.isAddOnBuyNew());
                packageRepository.save(packages);
                return new ApiResponse("Package saqlandi", true);
            }
        }
        return new ApiResponse("Siz package o'zgartira olmaysiz", false);

    }

    public ApiResponse add(PackageDto packageDto) {
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.MANAGER)) {
                List<Tariff> tariffList = tariffRepository.findAllById(packageDto.getTariffIds());
                Packages packages=new Packages();
                packages.setPackagePrice(packageDto.getPackagePrice());
                packages.setPackageType(packageDto.getPackageType());
                packages.setAmount(packageDto.getAmount());
                packages.setName(packageDto.getName());
                packages.setTariff(tariffList);
                packages.setValidityPeriod(packageDto.getValidityPeriod());
                packages.setAddOnBuyNew(packageDto.isAddOnBuyNew());
                packageRepository.save(packages);
                return new ApiResponse("Package saqlandi", true);
            }
        }
        return new ApiResponse("Siz package qo'sha olmaysiz", false);
    }

    public ApiResponse buyPackage(Integer id) {

        SimCard simCardInSystem = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Packages> optionalPackages = packageRepository.findById(id);
        if (!optionalPackages.isPresent())
            return new ApiResponse("Package topilmadi", false);
        if (simCardInSystem.getBalance()>=(optionalPackages.get().getPackagePrice())){
            List<Packages> packages = simCardInSystem.getPackages();
            packages.add(optionalPackages.get());
            simCardInSystem.setPackages(packages);
            simCardInSystem.setBalance(simCardInSystem.getBalance()-optionalPackages.get().getPackagePrice());
            simCardRepository.save(simCardInSystem);
            Payment payment=new Payment();
            payment.setPaymentAmount(optionalPackages.get().getPackagePrice());
            payment.setSimCard(simCardInSystem);
            payment.setDate(new Date());
            payment.setWhatFor(simCardInSystem.getUsername()+" dan "+optionalPackages.get().getName()+" paketi uchun to'lov");
            paymentRepository.save(payment);
            Detail detail=new Detail();
            detail.setDate(new Date());
            detail.setSimCard(simCardInSystem);
            detail.setContentOfOperation(simCardInSystem+" ushbu foydalanuvchi "+optionalPackages.get().getName()+" sotib oldi");
            detailRepository.save(detail);
            return new ApiResponse("Package faollashtirildi", true);
        }

        return new ApiResponse("Mablag' yetarli emas", false);
    }
}
