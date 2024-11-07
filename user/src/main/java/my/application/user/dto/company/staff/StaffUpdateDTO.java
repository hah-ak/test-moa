package my.application.user.dto.company.staff;

import java.util.List;

public record StaffUpdateDTO(
        Long company,
        String name,
        String rank,
        String introduce,
        List<StaffServiceInfoDTO> staffServiceInfos
) {
}
