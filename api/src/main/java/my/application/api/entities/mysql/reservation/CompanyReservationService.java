package my.application.api.entities.mysql.reservation;

import java.util.List;

public class CompanyReservationService {
    private Long id;
    private Long companyId;
    private Company companyInfo;
    private List<CompanyReservationMainService> mainServices;
}
