package my.application.user.entities.mysql.company;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Time;

@Entity
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dayOfTheWeek","company"}),
})
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
