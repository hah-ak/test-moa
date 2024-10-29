package my.application.api.repositories.mysql;

import my.application.api.entities.mysql.reservation.ReservationReceipt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientReservationReceiptRepository extends CrudRepository<ReservationReceipt, Long> {
    List<ReservationReceipt> findAllByMember(Long memberId);
    List<ReservationReceipt> findAllByStaffServiceInfo(Long serviceId);
}
