package my.application.user.entities.mysql.company;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.application.user.dto.company.CompanyUpdateDTO;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    @Setter(AccessLevel.NONE)
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String simpleExp;
    private Boolean holidayWork;
    @Column(unique = true)
    private String taxId;
    @OneToMany(mappedBy = "company")
    private List<BusinessWeekTimeTable> businessWeekTimeTable;
    @OneToMany(mappedBy = "company")
    private List<ExceptionDayOffTimeTable> exceptionDayOffTimeTable;
    @OneToMany(mappedBy = "company")
    private List<ExceptionBusinessTimeTable> exceptionBusinessTimeTable;
    @OneToMany(mappedBy = "company")
    private List<CompanyServiceProduct> services;

    public Company(CompanyUpdateDTO dto) {
        this.name = dto.name();
        this.simpleExp = dto.simpleExp();
        this.holidayWork = dto.holidayWork();
        this.taxId = dto.taxId();
        this.businessWeekTimeTable = dto.businessWeekTimeTable();
        this.exceptionDayOffTimeTable = dto.exceptionDayOffTimeTable();
        this.exceptionBusinessTimeTable = dto.exceptionBusinessTimeTable();
        this.services = dto.services();
    }

}
