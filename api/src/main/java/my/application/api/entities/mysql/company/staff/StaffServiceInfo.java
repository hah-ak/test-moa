package my.application.api.entities.mysql.company.staff;


import jakarta.persistence.*;
import my.application.api.entities.mysql.company.CompanyService;
import my.application.api.entities.mysql.reservation.ReservationReceipt;

import java.util.List;

@Entity
public class StaffServiceInfo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Staff staff;
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyService service;
    private Integer price;
    private String currency;
    private String staffExplain;
    @OneToMany(mappedBy = "staffServiceInfo")
    private List<ReservationReceipt> reservations;
    @OneToMany(mappedBy = "staffServiceInfo")
    private List<EachStaffServiceTimeTable> eachStaffServiceTimeTables;
}
