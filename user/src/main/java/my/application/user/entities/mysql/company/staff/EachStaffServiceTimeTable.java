package my.application.user.entities.mysql.company.staff;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Time;

@Entity
public class EachStaffServiceTimeTable {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private StaffServiceInfo staffServiceInfo;
    private Integer dayOfTheWeek;
    private Time startBreakTime;
    private Time endBreakTime;
    private Boolean sameBusinessTime;
    private Time exceptionOpenTime;
    private Time exceptionCloseTime;
}
