package my.application.user.entities.mysql.company.staff;

import jakarta.persistence.*;
import lombok.Getter;
import my.application.user.entities.mysql.company.Company;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Staff {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Company company;
    private String name;
    private String rank;
    private String introduce;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<StaffServiceInfo> services;

    protected Staff() {}
    public Staff(Company company, String name, String rank, String introduce, List<StaffServiceInfo> services) {
        this.company = company;
        this.name = name;
        this.rank = rank;
        this.introduce = introduce;
        this.services = services;
    }

    public void updateStaff(String name, String rank, String introduce, List<StaffServiceInfo> services) {
        this.name = name;
        this.rank = rank;
        this.introduce = introduce;
        this.services = services == null ? new ArrayList<>() : services;
    }

    public void updateStaff(String name, String rank, String introduce) {
        this.name = name;
        this.rank = rank;
        this.introduce = introduce;
    }
}
