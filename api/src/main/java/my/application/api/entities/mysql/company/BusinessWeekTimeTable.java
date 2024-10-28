package my.application.api.entities.mysql.company;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Time;

@Entity
public class BusinessWeekTimeTable {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private Company company;
    private Integer dayOfTheWeek;
    private Time openTime;
    private Time closeTime;
    private Time startbreakTime;
    private Time endbreakTime;

}
