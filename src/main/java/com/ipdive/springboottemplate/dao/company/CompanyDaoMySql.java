package com.ipdive.springboottemplate.dao.company;

import com.ipdive.springboottemplate.exceptions.CompanyNotFoundException;
import com.ipdive.springboottemplate.models.MyCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class CompanyDaoMySql implements CompanyDao {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public MyCompany getCompanyByTicker(String ticker) throws CompanyNotFoundException {
        Optional<MyCompany> company = companyRepository.findById(ticker);
        company.orElseThrow(() -> new CompanyNotFoundException(ticker));
        return company.get();
    }

    @Override
    public List<MyCompany> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void save(MyCompany company) { companyRepository.save(company);
    }

    @Override
    public void delete(MyCompany company) {
        companyRepository.deleteByTicker(company.getTicker());
    }
}
