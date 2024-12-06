package my.application.user.controller;

import lombok.RequiredArgsConstructor;
import my.application.user.dto.company.CompanyUpdateDTO;
import my.application.user.dto.company.staff.StaffUpdateDTO;
import my.application.user.entities.mysql.company.Company;
import my.application.user.entities.mysql.company.staff.Staff;
import my.application.user.services.company.CompanyService;
import my.application.user.services.company.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final StaffService staffService;

    @GetMapping
    public Company find(@RequestParam("companyId") Long companyId) {
        return companyService.findById(companyId);
    }

    @PostMapping
    public Company create(@RequestBody CompanyUpdateDTO company) {
        return companyService.createCompany(company);
    }

    @GetMapping("/staff")
    public Staff staff(@RequestParam("staffId") Long staffId) {
        return staffService.findStaff(staffId);
    }

    @PostMapping("/staff")
    public Staff create(@RequestBody StaffUpdateDTO staff) {
        return staffService.createStaff(staff);
    }

    @PatchMapping("/staff")
    public Staff update(@RequestBody StaffUpdateDTO staff) {
        return staffService.updateStaff(staff);
    }

    @DeleteMapping("/staff")
    public Boolean delete(@RequestBody StaffUpdateDTO staff) {
        return staffService.deleteStaff(staff.staff());
    }

}
