package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_communication_company.entity.Employer;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {
    boolean existsByUsername(String username);


    Optional<Employer> findByUsername(String username);
}
