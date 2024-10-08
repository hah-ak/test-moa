package my.application.api.entities.mysql.reservation;

import java.time.LocalDateTime;
import java.util.List;

public class CompanyReservationMainService {
    private Long id;
    private Long revCompanyId;
    private String content;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private LocalDateTime startBreakTime;
    private LocalDateTime endBreakTime;
    private Integer term;
    private String ServiceDayOfTheWeek;
    private List<StaffMainService> staff;
    private List<CompanyReservationDetailService> detailServices;
}
