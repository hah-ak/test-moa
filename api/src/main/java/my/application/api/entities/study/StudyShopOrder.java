package my.application.api.entities.study;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class StudyShopOrder {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private StudyShopMember member;

    @OneToMany(mappedBy = "order")
    @JoinColumn(name = "order_item_id") // 없으면 조인테이블이 생성되어버림.
    private List<StudyShopOrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private StudyShopDelivery delivery;


    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
