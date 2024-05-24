package my.application.api.entities.mysql;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "member_join_room")
@Getter
public class MemberJoinRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer autoNo;

    @ManyToOne
    @JoinColumn(name = "room_no")
    private RoomEntity roomEntity;

    @JoinColumn(table = "member", name = "mem_no")
    private Integer member;

    public MemberJoinRoomEntity() {};

    @Builder
    public MemberJoinRoomEntity(RoomEntity roomEntity,Integer memNo) {
    }
}
