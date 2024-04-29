package my.application.api.entities.study.jpql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
public class JpqlMember {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private Integer age;

    @BatchSize(size = 100) // call이 되면 한번에 몇개의 로우를 가져와서 찾을건지
    @ManyToOne(fetch = FetchType.LAZY)
    private JpqlTeam team;
}
