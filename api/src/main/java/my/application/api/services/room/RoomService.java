package my.application.api.services.room;

import lombok.RequiredArgsConstructor;
import my.application.api.dto.member.MemberDTO;
import my.application.api.entities.mysql.MemberJoinRoomEntity;
import my.application.api.entities.mysql.RoomEntity;
import my.application.api.repositories.mysql.member.MemberJoinRoomRepository;
import my.application.api.repositories.mysql.member.RoomRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberJoinRoomRepository memberJoinRoomRepository;
    public RoomEntity createRoom() {
        return roomRepository.save(new RoomEntity());
    }

    public MemberJoinRoomEntity saveMemberJoinRoom(RoomEntity room, MemberDTO member) {
        return memberJoinRoomRepository.save(MemberJoinRoomEntity.builder().roomEntity(room).memNo(member.memNo()).build());
    }

    public MemberJoinRoomEntity findRoomById(MemberDTO member, Integer roomId) {
        Optional<MemberJoinRoomEntity> memNo = memberJoinRoomRepository.findBy(Example.of(MemberJoinRoomEntity.builder().memNo(member.memNo()).build())
                , (query) -> query.project("mem_no").first());
        return memNo.orElse(null);
    }
}
