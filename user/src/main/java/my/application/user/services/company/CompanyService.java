package my.application.user.services.company;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.application.user.dto.company.CompanyDeleteDTO;
import my.application.user.dto.company.CompanyUpdateDTO;
import my.application.user.entities.mysql.company.*;
import my.application.user.repositories.mysql.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Company createCompany(CompanyUpdateDTO companyUpdateDTO) {
        return companyRepository.save(new Company(companyUpdateDTO));
    }

    @Transactional
    public Company updateCompany(CompanyUpdateDTO companyUpdateDTO, CompanyDeleteDTO deleteDTO) {
        Company company = companyRepository.findById(companyUpdateDTO.id()).orElseThrow(() -> new RuntimeException("Company not found"));
        // update
        company.setName(companyUpdateDTO.name());
        company.setHolidayWork(companyUpdateDTO.holidayWork());
        company.setSimpleExp(companyUpdateDTO.simpleExp());
        // insert update
        List<Long> bizTime = companyUpdateDTO.exceptionBusinessTimeTable().stream().map(ExceptionBusinessTimeTable::getId).toList();
        company.getExceptionBusinessTimeTable().removeIf(table -> bizTime.contains(table.getId()));
        company.getExceptionBusinessTimeTable().addAll(companyUpdateDTO.exceptionBusinessTimeTable());

        List<Long> dayOffTime = companyUpdateDTO.exceptionDayOffTimeTable().stream().map(ExceptionDayOffTimeTable::getId).toList();
        company.getExceptionDayOffTimeTable().removeIf(table -> dayOffTime.contains(table.getId()));
        company.getExceptionDayOffTimeTable().addAll(companyUpdateDTO.exceptionDayOffTimeTable());

        List<Long> weekTime = companyUpdateDTO.businessWeekTimeTable().stream().map(BusinessWeekTimeTable::getId).toList();
        company.getBusinessWeekTimeTable().removeIf(table -> weekTime.contains(table.getId()));
        company.getBusinessWeekTimeTable().addAll(companyUpdateDTO.businessWeekTimeTable());
        // insert update delete
        List<Long> deleteServices = deleteDTO.services().stream().map(CompanyServiceProduct::getId).toList();
        List<Long> updateServices = companyUpdateDTO.services().stream().map(CompanyServiceProduct::getId).toList();
        company.getServices().removeIf(product -> deleteServices.contains(product.getId()));
        company.getServices().removeIf(product -> updateServices.contains(product.getId()));
        company.getServices().addAll(companyUpdateDTO.services());
        return company;
    }
}
