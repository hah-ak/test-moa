package my.application.api.services.reservation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.application.api.dto.reservation.ReceiptDTO;
import my.application.api.entities.mysql.reservation.ReservationReceipt;
import my.application.api.repositories.mysql.ClientReservationReceiptRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import web.core.CommonProperty;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ClientReservationReceiptRepository receiptRepository;
    private final CommonProperty commonProperty;

    public List<ReservationReceipt> userReceipts(Long member) {
        return receiptRepository.findAllByMember(member);
    }

    public Mono<List<Long>> staffServiceInfos(Long staffNumber) {
        WebClient build = WebClient.builder().baseUrl(commonProperty.getUSER_SERVER_DOMAIN()).build();
        return build.method(HttpMethod.POST).uri("/company/staff/service-info")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{staffNumber:" + staffNumber + "}")
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(new ParameterizedTypeReference<List<Long>>() {}))
                .doOnError(throwable -> {throw new RuntimeException(throwable.getMessage());});
    }

    public Mono<List<ReservationReceipt>> staffReceipts(Long staffNumber) {
        return staffServiceInfos(staffNumber)
                .map(serviceInfos -> serviceInfos.stream()
                        .map(receiptRepository::findAllByStaffServiceInfo)
                        .flatMap(List::stream)
                        .toList()
                );
    }

    @Transactional
    public Object createReceipt(Long member,  ReceiptDTO dto) {
        return receiptRepository.save(new ReservationReceipt(dto, member));
    }
}
