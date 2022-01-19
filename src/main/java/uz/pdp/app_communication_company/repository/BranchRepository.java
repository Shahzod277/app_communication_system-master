package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_communication_company.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
