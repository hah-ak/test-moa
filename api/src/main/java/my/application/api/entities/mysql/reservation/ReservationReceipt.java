package my.application.api.entities.mysql.reservation;

import jakarta.persistence.*;
import my.application.api.entities.mysql.company.staff.StaffServiceInfo;

import java.sql.Time;
import java.time.LocalDate;

@Entity
public class ReservationReceipt {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate serviceDate;
    private Time serviceTime;
    private String message;
    @ManyToOne
    private StaffServiceInfo staffServiceInfo;
    private Long member;
}
