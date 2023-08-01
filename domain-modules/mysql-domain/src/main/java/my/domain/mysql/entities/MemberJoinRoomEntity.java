package my.domain.mysql.entities;

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
    @ManyToOne
    @JoinColumn(name = "mem_no")
    private MemberEntity member;

    public MemberJoinRoomEntity() {};

    @Builder
    public MemberJoinRoomEntity(RoomEntity roomEntity,MemberEntity member) {
    }
}
