package my.application.user.repositories.mysql.staff;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import my.application.user.entities.mysql.company.staff.QEachStaffServiceTimeTable;
import my.application.user.entities.mysql.company.staff.QStaff;
import my.application.user.entities.mysql.company.staff.QStaffServiceInfo;
import my.application.user.entities.mysql.company.staff.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffRepositoryImpl implements StaffRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Autowired
    public StaffRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.queryFactory = new JPAQueryFactory(entityManagerFactory.createEntityManager());
    }


    @Override
    public Optional<Staff> findByIdWithAllTimeTable(String name) {
        QStaff staff = QStaff.staff;
        QStaffServiceInfo serviceInfo = QStaffServiceInfo.staffServiceInfo;
        QEachStaffServiceTimeTable eachStaffServiceTimeTable = QEachStaffServiceTimeTable.eachStaffServiceTimeTable;

        Staff result = queryFactory.selectFrom(staff)
                .leftJoin(staff.services, serviceInfo)
                .fetchJoin()
                .leftJoin(serviceInfo.eachStaffServiceTimeTables, eachStaffServiceTimeTable)
                .fetchJoin()
                .where(staff.name.eq(name))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
