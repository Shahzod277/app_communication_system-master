package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_communication_company.entity.Client;

import java.util.UUID;

@RepositoryRestResource(path = "client")
public interface ClientRepository extends JpaRepository<Client, UUID> {
}
