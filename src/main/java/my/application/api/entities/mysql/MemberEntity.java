package my.application.api.entities.mysql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class MemberEntity {
    @Id
    private Integer memNo;
    private String id;
    private String name;
    private String passWord;
    private String imageName;

    @Builder
    protected MemberEntity() {

    }
}
