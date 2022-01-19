package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_communication_company.entity.Branch;
import uz.pdp.app_communication_company.entity.Detail;

import java.util.UUID;

public interface DetailRepository extends JpaRepository<Detail, UUID> {
}
