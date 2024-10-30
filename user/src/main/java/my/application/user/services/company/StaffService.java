package my.application.user.services.company;

import lombok.RequiredArgsConstructor;
import my.application.user.entities.mysql.company.staff.Staff;
import my.application.user.entities.mysql.company.staff.StaffServiceInfo;
import my.application.user.repositories.mysql.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public List<StaffServiceInfo> staffServiceInfo(Long staffId) {
        Optional<Staff> byId = staffRepository.findById(staffId);
        if (byId.isPresent()) {
            Staff staff = byId.get();
            return staff.getServices();
        }
        return new ArrayList<>();
    }
}
