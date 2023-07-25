package my.application.api.services.room;

import lombok.RequiredArgsConstructor;
import my.application.api.entities.mysql.MemberEntity;
import my.application.api.entities.mysql.MemberJoinRoomEntity;
import my.application.api.entities.mysql.RoomEntity;
import my.application.api.repositories.mysql.MemberRepository;
import my.application.api.repositories.mysql.MemberJoinRoomRepository;
import my.application.api.repositories.mysql.RoomRepository;
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
