package my.application.api.entities.mysql;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "member_join_room")
@Getter
public class MemberJoinRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer autoNo;

    @ManyToOne
    @JoinColumn(name = "room_no")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "mem_no")
    private MemberEntity member;
}
