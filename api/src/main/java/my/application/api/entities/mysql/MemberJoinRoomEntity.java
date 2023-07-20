package my.application.api.entities.mysql;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "member_join_room")
@Getter
@Builder
public class MemberJoinRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer autoNo;

    @ManyToOne
    @JoinColumn(name = "room_no")
    private RoomEntity roomEntity;
    @ManyToOne
    @JoinColumn(name = "mem_no")
    private MemberEntity member;

    @Builder
    public MemberJoinRoomEntity() {

    }
}
