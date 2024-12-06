package my.application.chat.entities.mysql;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class RoomEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer maxUser;
    private String type;

    @OneToMany(mappedBy = "room")
    private List<RoomJoinMemberEntity> joinMembers;

    public RoomEntity(Integer maxUser, String type) {
        this.maxUser = maxUser;
        this.type = type;
    }

    public RoomEntity() {}
}
