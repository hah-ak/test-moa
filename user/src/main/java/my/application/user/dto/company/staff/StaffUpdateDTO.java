package my.application.user.dto.company.staff;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StaffUpdateDTO(
        Long staff,
        @NotNull Long company,
        @NotNull String name,
        @NotNull String rank,
        @NotNull String introduce,
        @NotNull List<StaffServiceInfoDTO> staffServiceInfos
) {
}
