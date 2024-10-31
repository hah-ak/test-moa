package my.application.user.entities.mysql.company;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import my.application.user.entities.mysql.company.staff.Staff;

import java.util.List;

@Entity
@Getter
@Setter
public class CompanyServiceProduct {
    @Setter(AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;
    private String content;
    @OneToMany(mappedBy = "service")
    private List<Staff> staff;
}
