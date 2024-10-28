package my.application.api.entities.mysql.company;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String simpleExp;
    private Boolean holidayWork;
    private String taxId;
    @OneToMany(mappedBy = "company")
    private List<BusinessWeekTimeTable> businessWeekTimeTable;
    @OneToMany(mappedBy = "company")
    private List<ExceptionDayOffTimeTable> exceptionDayOffTimeTable;
    @OneToMany(mappedBy = "company")
    private List<ExceptionBusinessTimeTable> exceptionBusinessTimeTable;
    @OneToMany(mappedBy = "company")
    private List<CompanyService> services;

}
