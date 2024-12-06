package my.application.api;

import my.application.api.services.study.Child;
import my.application.api.services.study.Parent;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.zone.ZoneOffsetTransition;
import java.util.List;

public class ApiTest {

    @Test
    void aweifj() {
        System.out.println(System.getProperty("user.home"));
    }

    @Test
    void cmdTest() {
        new ProcessBuilder("bash","echo","test");
    }

    @Test
    void whang() {
    }

    void print(Parent parent) {
        parent.print();
    }

    @Test
    void awiefjaweif() {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime localDateTime1 = LocalDateTime.of(now, LocalTime.of(9, 0, 0));
        ZonedDateTime zonedDateTime = localDateTime1.atZone(ZoneId.of("Asia/Seoul"));
        LocalDateTime nowTime = zonedDateTime.toLocalDateTime();
        LocalDateTime localDateTime = nowTime.minusHours(48);
        LocalDateTime localDateTime2 = nowTime.minusHours(72);

        System.out.println(localDateTime.atZone(ZoneId.of("Asia/Seoul")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());;
        System.out.println(localDateTime2.atZone(ZoneId.of("Asia/Seoul")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
    }

}
