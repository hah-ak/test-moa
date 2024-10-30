package my.application.user.entities.mysql.company.staff;


import jakarta.persistence.*;
import lombok.Getter;
import my.application.user.entities.mysql.company.CompanyService;

import java.util.List;

@Entity
@Getter
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
    private List<EachStaffServiceTimeTable> eachStaffServiceTimeTables;
}
