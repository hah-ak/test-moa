package my.application.user.dto.company;

import my.application.user.entities.mysql.company.CompanyServiceProduct;

import java.util.List;

public record CompanyDeleteDTO(
        List<CompanyServiceProduct> services
        ) {

}
