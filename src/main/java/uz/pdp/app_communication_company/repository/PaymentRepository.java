package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.app_communication_company.entity.Payment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByDate(Date date);

    @Query(value = "select * from payment where date between fromdate=:fromDate and todate=:toDate", nativeQuery = true)
    List<Payment> findByPeriod(Date fromDate, Date toDate);
}
