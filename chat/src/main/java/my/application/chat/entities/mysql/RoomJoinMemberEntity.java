package my.application.chat.entities.mysql;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RoomJoinMemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private RoomEntity room;

    private Long memberNumber;

    public RoomJoinMemberEntity() {}
    public RoomJoinMemberEntity(RoomEntity room, Long memberNumber) {
        this.room = room;
        this.memberNumber = memberNumber;
    }
}
