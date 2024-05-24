package my.application.api.entities.study;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class StudyShopDelivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery")
    private StudyShopOrder order;

    private String city;
    private String street;
    private String zipCode;
    private DeliveryStatus status;
}
