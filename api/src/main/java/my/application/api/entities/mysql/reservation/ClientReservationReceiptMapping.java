package my.application.api.entities.mysql.reservation;

import jakarta.persistence.*;

@Entity
public class ClientReservationReceiptMapping {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private ReservationReceipt reservationReceipt;
    /**
     * jpa상에서는 직접 연결하지 않는다.
     * */
    private Long member;
}
