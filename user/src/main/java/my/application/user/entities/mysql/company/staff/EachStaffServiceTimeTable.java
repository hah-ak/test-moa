package my.application.user.entities.mysql.company.staff;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.application.user.dto.company.staff.StaffServiceInfoDTO;

import java.sql.Time;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EachStaffServiceTimeTable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private StaffServiceInfo staffServiceInfo;
    private Integer dayOfTheWeek;
    private Time startBreakTime;
    private Time endBreakTime;
    private Time exceptionOpenTime;
    private Time exceptionCloseTime;

    public EachStaffServiceTimeTable(
            StaffServiceInfo staffServiceInfo,
            Integer dayOfTheWeek,
            Time startBreakTime,
            Time endBreakTime,
            Time exceptionOpenTime,
            Time exceptionCloseTime
    ) {
        this.staffServiceInfo = staffServiceInfo;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startBreakTime = startBreakTime;
        this.endBreakTime = endBreakTime;
        this.exceptionOpenTime = exceptionOpenTime;
        this.exceptionCloseTime = exceptionCloseTime;
    }

    public EachStaffServiceTimeTable(StaffServiceInfo staffServiceInfo, StaffServiceInfoDTO.TimeTable timeTable) {
        this.staffServiceInfo = staffServiceInfo;
        this.dayOfTheWeek = timeTable.getDayOfTheWeek();
        this.startBreakTime = timeTable.getStartBreakTime();
        this.endBreakTime = timeTable.getEndBreakTime();
        this.exceptionOpenTime = timeTable.getExceptionOpenTime();
        this.exceptionCloseTime = timeTable.getExceptionCloseTime();
    }

    public void updateEachStaffServiceTimeTable(
            Integer dayOfTheWeek,
            Time startBreakTime,
            Time endBreakTime,
            Time exceptionOpenTime,
            Time exceptionCloseTime
    ) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.startBreakTime = startBreakTime;
        this.endBreakTime = endBreakTime;
        this.exceptionOpenTime = exceptionOpenTime;
        this.exceptionCloseTime = exceptionCloseTime;
    }

    public void updateEachStaffServiceTimeTable(StaffServiceInfoDTO.TimeTable timeTable) {
        this.dayOfTheWeek = timeTable.getDayOfTheWeek();
        this.startBreakTime = timeTable.getStartBreakTime();
        this.endBreakTime = timeTable.getEndBreakTime();
        this.exceptionOpenTime = timeTable.getExceptionOpenTime();
        this.exceptionCloseTime = timeTable.getExceptionCloseTime();
    }
}
