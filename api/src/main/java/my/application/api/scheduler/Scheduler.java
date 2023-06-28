package my.application.api.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class Scheduler {


//    @Scheduled(cron = "*/5 * * * * *" , zone = "UTC")
//    public void cronTest2() {
//        log.info(ZonedDateTime.now(ZoneId.of("UTC")).toString());
//    }
//    @Scheduled(cron = "0 33-59/1 4 * * *" , zone = "UTC")
//    public void cronTest() {
//        log.info("cron Test");
//    }
}
