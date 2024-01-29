package my.application.api.entities.study;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class StudyShopOrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private StudyShopOrder order;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private StudyShopItem item;
    private int price;
    private int count;

}
