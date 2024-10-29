package my.application.user.entities.mysql.company;

import jakarta.persistence.*;
import my.application.user.entities.mysql.company.staff.Staff;

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
