package my.application.gateway.entities.mysql.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class MemberAuthorityEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private MemberAuthority memberAuthority;
}
