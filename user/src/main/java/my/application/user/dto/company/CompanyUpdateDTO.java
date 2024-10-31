package my.application.user.dto.company;

import my.application.user.entities.mysql.company.BusinessWeekTimeTable;
import my.application.user.entities.mysql.company.CompanyServiceProduct;
import my.application.user.entities.mysql.company.ExceptionBusinessTimeTable;
import my.application.user.entities.mysql.company.ExceptionDayOffTimeTable;

import java.util.List;

public record CompanyUpdateDTO(
        Long id,
        String name,
        String simpleExp,
        Boolean holidayWork,
        String taxId,
        List<BusinessWeekTimeTable> businessWeekTimeTable,
        List<ExceptionDayOffTimeTable> exceptionDayOffTimeTable,
        List<ExceptionBusinessTimeTable> exceptionBusinessTimeTable,
        List<CompanyServiceProduct> services
) {
}
