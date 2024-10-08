package my.application.api.entities.mysql.reservation;

import java.time.LocalDateTime;

public class ClientReservation {
    private Long id;
    private LocalDateTime time;
    private String message;
    private CompanyReservationDetailService service;
    private Staff staff;
}
