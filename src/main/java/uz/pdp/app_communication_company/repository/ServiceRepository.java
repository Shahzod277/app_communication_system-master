package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_communication_company.entity.Branch;
import uz.pdp.app_communication_company.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
