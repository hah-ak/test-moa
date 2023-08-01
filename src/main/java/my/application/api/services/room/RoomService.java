package my.application.api.services.room;

import lombok.RequiredArgsConstructor;
import my.domain.mysql.entities.MemberEntity;
import my.domain.mysql.entities.MemberJoinRoomEntity;
import my.domain.mysql.entities.RoomEntity;
import my.domain.mysql.repositories.member.MemberJoinRoomRepository;
import my.domain.mysql.repositories.member.MemberRepository;
import my.domain.mysql.repositories.member.RoomRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberJoinRoomRepository memberJoinRoomRepository;
    private final MemberRepository memberRepository;
    public RoomEntity createRoom() {
        return roomRepository.save(new RoomEntity());
    }

    public MemberJoinRoomEntity saveMemberJoinRoom(RoomEntity room, MemberEntity member) {
        return memberJoinRoomRepository.save(MemberJoinRoomEntity.builder().roomEntity(room).member(member).build());
    }

    public MemberJoinRoomEntity findRoomById(String id) {
        MemberEntity member = memberRepository.findById(id);
        Optional<MemberJoinRoomEntity> memNo = memberJoinRoomRepository.findBy(Example.of(MemberJoinRoomEntity.builder().member(member).build())
                , (query) -> query.project("mem_no").first());
        return memNo.orElse(null);
    }
}
