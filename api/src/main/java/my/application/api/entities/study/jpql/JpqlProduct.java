package my.application.api.entities.study.jpql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JpqlProduct {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private Integer price;
    private Integer stockAmount;

}
