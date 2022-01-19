package uz.pdp.app_communication_company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_communication_company.entity.Employer;
import uz.pdp.app_communication_company.entity.Payment;
import uz.pdp.app_communication_company.entity.Role;
import uz.pdp.app_communication_company.entity.SimCard;
import uz.pdp.app_communication_company.entity.template.RoleName;
import uz.pdp.app_communication_company.payload.ApiResponse;
import uz.pdp.app_communication_company.repository.EmployerRepository;
import uz.pdp.app_communication_company.repository.PaymentRepository;
import uz.pdp.app_communication_company.repository.SimCardRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class DashboadService {
    @Autowired
    SimCardRepository simCardRepository;
    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    PaymentRepository paymentRepository;

    public ApiResponse getBuySimCard() {
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                List<SimCard> simCardList = simCardRepository.findAll();
                return new ApiResponse("Topildi", true,simCardList);
            }
        }
        return new ApiResponse("Sizda bunday huquq yo'q", false);
    }

    public ApiResponse dailyIncome(Date date) {
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                List<Payment> allByDate = paymentRepository.findAllByDate(date);
                return new ApiResponse("Topildi", true,allByDate);
            }
        }
        return new ApiResponse("Sizda bunday huquq yo'q", false);
    }

    public ApiResponse monthlyIncome(Integer mothId) {
        Employer employerInSystem = (Employer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = employerInSystem.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                Date fromDate=new Date();
                fromDate.setMonth(mothId);
                fromDate.setDate(1);
                Date toDate=new Date();
                toDate.setMonth(mothId);
                toDate.setDate(31);
                List<Payment> byPeriod = paymentRepository.findByPeriod(fromDate, toDate);
                return new ApiResponse("Topildi", true,byPeriod);
            }
        }
        return new ApiResponse("Sizda bunday huquq yo'q", false);

    }
}
