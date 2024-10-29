package my.application.api.entities.mysql.reservation;

import jakarta.persistence.*;
import my.application.api.dto.reservation.ReceiptDTO;

import java.sql.Time;
import java.time.LocalDate;

@Entity
public class ReservationReceipt {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate serviceDate;
    private Time serviceTime;
    private String message;
    // 식별자들만 받는다.
    private Long staffServiceInfo;
    private Long member;

    public ReservationReceipt(ReceiptDTO dto, Long member) {
        this.serviceDate = dto.date();
        this.serviceTime = dto.time();
        this.message = dto.message();
        this.staffServiceInfo = dto.serviceNumber();
        this.member = member;
    }

    protected ReservationReceipt() {}
}
