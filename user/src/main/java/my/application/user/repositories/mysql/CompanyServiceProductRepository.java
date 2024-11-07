package my.application.user.repositories.mysql;

import my.application.user.entities.mysql.company.Company;
import my.application.user.entities.mysql.company.CompanyServiceProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyServiceProductRepository extends CrudRepository<CompanyServiceProduct, Long> {
    List<CompanyServiceProduct> findByIdIn(List<Long> ids);
}
