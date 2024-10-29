package my.application.api.services.reservation;

import lombok.RequiredArgsConstructor;
import my.application.api.dto.reservation.ReceiptDTO;
import my.application.api.entities.mysql.reservation.ReservationReceipt;
import my.application.api.repositories.mysql.ClientReservationReceiptRepository;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ClientReservationReceiptRepository receiptRepository;
    private final InfoEndpoint infoEndpoint;

    public List<ReservationReceipt> userReceipts(Long member) {
        return receiptRepository.findAllByMember(member);
    }

    public List<ReservationReceipt> staffReceipts(List<Long> serviceInfos) {
        return serviceInfos.stream()
                .map(receiptRepository::findAllByStaffServiceInfo)
                .flatMap(List::stream)
                .toList();
    }

    public Object createReceipt(Long member,  ReceiptDTO dto) {
        return receiptRepository.save(new ReservationReceipt(dto, member));
    }
}
