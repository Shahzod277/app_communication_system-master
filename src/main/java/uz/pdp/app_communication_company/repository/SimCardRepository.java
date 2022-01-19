package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_communication_company.entity.Branch;
import uz.pdp.app_communication_company.entity.SimCard;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "simCard")
public interface SimCardRepository extends JpaRepository<SimCard, UUID> {
    Optional<SimCard> findBySimCardCode(String simCardCode);
}
