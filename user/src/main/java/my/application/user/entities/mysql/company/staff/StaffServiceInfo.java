package my.application.user.entities.mysql.company.staff;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import my.application.user.dto.company.staff.StaffServiceInfoDTO;
import my.application.user.dto.company.staff.StaffUpdateDTO;
import my.application.user.entities.mysql.company.CompanyServiceProduct;

import java.util.ArrayList;
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
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String currency;
    private String staffExplain;
    @OneToMany(mappedBy = "staffServiceInfo", cascade = CascadeType.ALL)
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

    public StaffServiceInfo(Staff staff, CompanyServiceProduct service, StaffServiceInfoDTO dto, List<EachStaffServiceTimeTable> eachStaffServiceTimeTables) {
        this.staff = staff;
        this.service = service;
        this.price = dto.getPrice();
        this.currency = dto.getCurrency();
        this.staffExplain = dto.getExplain();
        this.eachStaffServiceTimeTables = eachStaffServiceTimeTables == null ? new ArrayList<>() : eachStaffServiceTimeTables;
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
