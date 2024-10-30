package my.application.user.repositories.mysql;

import my.application.user.entities.mysql.company.staff.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Long> {

}
