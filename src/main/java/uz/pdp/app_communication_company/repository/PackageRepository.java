package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_communication_company.entity.Packages;

public interface PackageRepository extends JpaRepository<Packages, Integer> {
}
