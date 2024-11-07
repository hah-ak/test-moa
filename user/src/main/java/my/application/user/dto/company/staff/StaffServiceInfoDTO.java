package my.application.user.dto.company.staff;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StaffServiceInfoDTO {

    @NotNull
    private Long companyServiceProduct;
    @NotNull
    private Long price;
    @NotNull
    private String currency;
    private List<TimeTable> timetable = new ArrayList<>();

    @Getter
    public static class TimeTable {
        @NotNull
        private Integer dayOfTheWeek;
        @NotNull
        private Time startBreakTime;
        @NotNull
        private Time endBreakTime;
        @NotNull
        private Time exceptionOpenTime;
        @NotNull
        private Time exceptionCloseTime;
    }

}
