package my.application.user.entities.mysql.company.staff;


import jakarta.persistence.*;
import lombok.Getter;
import my.application.user.entities.mysql.company.CompanyServiceProduct;

import java.util.List;

@Entity
@Getter
public class StaffServiceInfo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Staff staff;
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyServiceProduct service;
    private Long price;
    private String currency;
    private String staffExplain;
    @OneToMany(mappedBy = "staffServiceInfo")
    private List<EachStaffServiceTimeTable> eachStaffServiceTimeTables;

    protected StaffServiceInfo() {}
    public StaffServiceInfo(Staff staff, CompanyServiceProduct service, Long price, String currency, String staffExplain, List<EachStaffServiceTimeTable> eachStaffServiceTimeTables) {
        this.staff = staff;
        this.service = service;
        this.price = price;
        this.currency = currency;
        this.staffExplain = staffExplain;
        this.eachStaffServiceTimeTables = eachStaffServiceTimeTables;
    }

    public void updateStaffServiceInfo(
        Long price,
        String currency,
        String staffExplain
    ) {
        this.price = price;
        this.currency = currency;
        this.staffExplain = staffExplain;
    }

}
