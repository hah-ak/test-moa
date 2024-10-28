package my.application.api.entities.mysql.company;

import jakarta.persistence.*;
import my.application.api.entities.mysql.company.staff.Staff;
import my.application.api.entities.mysql.company.staff.StaffServiceInfo;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CompanyService {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;
    private String content;
    @OneToMany(mappedBy = "service")
    private List<Staff> staff;
}
