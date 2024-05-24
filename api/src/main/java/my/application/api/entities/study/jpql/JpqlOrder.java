package my.application.api.entities.study.jpql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JpqlOrder {
    @Id @GeneratedValue
    private Long id;
    private Integer orderAmount;

    private JpqlAddress address;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private JpqlMember member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private JpqlProduct jpqlProduct;
}
