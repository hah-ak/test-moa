package my.application.user.services.company;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.application.user.dto.company.staff.StaffServiceInfoDTO;
import my.application.user.dto.company.staff.StaffUpdateDTO;
import my.application.user.entities.mysql.company.Company;
import my.application.user.entities.mysql.company.CompanyServiceProduct;
import my.application.user.entities.mysql.company.staff.EachStaffServiceTimeTable;
import my.application.user.entities.mysql.company.staff.Staff;
import my.application.user.entities.mysql.company.staff.StaffServiceInfo;
import my.application.user.repositories.mysql.CompanyRepository;
import my.application.user.repositories.mysql.CompanyServiceProductRepository;
import my.application.user.repositories.mysql.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final CompanyRepository companyRepository;
    private final CompanyServiceProductRepository companyServiceProductRepository;

    @Transactional
    public Staff createStaff(StaffUpdateDTO staffUpdateDTO) {
        Company company = companyRepository.findById(staffUpdateDTO.company()).orElseThrow(EntityNotFoundException::new);
        Staff save = new Staff(company, staffUpdateDTO.name(), staffUpdateDTO.rank(), staffUpdateDTO.introduce(), new ArrayList<>());

        HashMap<Long, StaffServiceInfoDTO> productStaffDTO = new HashMap<>();
        staffUpdateDTO.staffServiceInfos().forEach(dto -> {
            productStaffDTO.put(dto.getCompanyServiceProduct(), dto);
        });

        List<CompanyServiceProduct> serviceProducts = companyServiceProductRepository.findByIdIn(productStaffDTO.keySet().stream().toList());
        serviceProducts.forEach(companyServiceProduct -> {
            StaffServiceInfoDTO dto = productStaffDTO.get(companyServiceProduct.getId());
            StaffServiceInfo staffServiceInfo = new StaffServiceInfo(save, companyServiceProduct, dto.getPrice(), dto.getCurrency(), "", new ArrayList<>());
            dto.getTimetable().forEach(timeTable -> {
                staffServiceInfo.getEachStaffServiceTimeTables().add(new EachStaffServiceTimeTable(staffServiceInfo, timeTable));
            });
            save.getServices().add(staffServiceInfo);
        });

        return staffRepository.save(save);
    }

    public List<StaffServiceInfo> staffServiceInfo(Long staffId) {
        Optional<Staff> byId = staffRepository.findById(staffId);
        if (byId.isPresent()) {
            Staff staff = byId.get();
            return staff.getServices();
        }
        return new ArrayList<>();
    }
}
