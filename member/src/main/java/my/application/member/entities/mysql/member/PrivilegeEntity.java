package my.application.member.entities.mysql.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class PrivilegeEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private MemberPrivilege privilege;
}
