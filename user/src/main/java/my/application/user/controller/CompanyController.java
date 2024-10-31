package my.application.user.controller;

import lombok.RequiredArgsConstructor;
import my.application.user.dto.company.CompanyUpdateDTO;
import my.application.user.entities.mysql.company.Company;
import my.application.user.services.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public Company create(@RequestBody CompanyUpdateDTO company) {

        return companyService.createCompany(company);
    }

}
