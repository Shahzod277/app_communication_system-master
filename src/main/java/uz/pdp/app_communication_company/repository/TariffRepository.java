package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_communication_company.entity.Tariff;

import java.util.Set;

public interface TariffRepository extends JpaRepository<Tariff, Integer> {
    Set<Tariff> getAllById(Integer id);
}
