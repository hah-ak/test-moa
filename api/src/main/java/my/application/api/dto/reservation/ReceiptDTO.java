package my.application.api.dto.reservation;

import java.sql.Time;
import java.time.LocalDate;

public record ReceiptDTO (
        String message,
        LocalDate date,
        Time time,
        Long staff,
        Long serviceNumber
){
}
