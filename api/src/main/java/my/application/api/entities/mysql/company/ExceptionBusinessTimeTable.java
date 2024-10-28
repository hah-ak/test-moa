package my.application.api.entities.mysql.company;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Time;
import java.time.LocalDate;

@Entity
public class ExceptionBusinessTimeTable {
    @Id @GeneratedValue
    private Long id;
    private Time time;
    private LocalDate date;
    @ManyToOne
    private Company company;
    private String reason;
}
