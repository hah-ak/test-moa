package my.application.user.entities.mysql.company.staff;

import jakarta.persistence.*;
import my.application.user.entities.mysql.company.Company;

import java.util.List;

@Entity
public class Staff {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Company company;
    private String name;
    private String rank;
    private String introduce;
    @OneToMany(mappedBy = "staff")
    private List<StaffServiceInfo> services;
}
