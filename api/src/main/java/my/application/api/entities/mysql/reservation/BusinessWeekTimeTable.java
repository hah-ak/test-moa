package my.application.api.entities.mysql.reservation;

import java.sql.Time;

public class BusinessWeekTimeTable {
    private Long id;
    private Company company;
    private Integer dayOfTheWeek;
    private Time openTime;
    private Time closeTime;
    private Time startbreakTime;
    private Time endbreakTime;

}
