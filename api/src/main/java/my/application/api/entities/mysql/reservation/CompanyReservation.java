package my.application.api.entities.mysql.reservation;

import java.util.List;

public class CompanyReservation {
    private Long id;
    private Long companyId;
    private Company companyInfo;
    private List<CompanyReservationMainService> mainServices;
}
