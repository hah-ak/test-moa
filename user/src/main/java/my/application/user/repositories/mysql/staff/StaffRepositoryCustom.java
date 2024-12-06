package my.application.user.repositories.mysql.staff;

import my.application.user.entities.mysql.company.staff.Staff;

import java.util.Optional;

public interface StaffRepositoryCustom {
    Optional<Staff> findByIdWithAllTimeTable(String name);
}
