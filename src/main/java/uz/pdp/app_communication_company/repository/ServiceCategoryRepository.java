package uz.pdp.app_communication_company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_communication_company.entity.Branch;
import uz.pdp.app_communication_company.entity.ServiceCategory;
@RepositoryRestResource(path = "serviceCategory")
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer> {
}
