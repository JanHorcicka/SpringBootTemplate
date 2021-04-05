package com.ipdive.springboottemplate.dao.company;

import com.ipdive.springboottemplate.exceptions.CompanyNotFoundException;
import com.ipdive.springboottemplate.models.MyCompany;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Repository
public class CompanyInMemoryDao implements CompanyDao {

    @Autowired
    CompanyRepository companyRepository;

    private List<MyCompany> companiesList;
    private Map<String, MyCompany> companiesMap;

    public CompanyInMemoryDao() {

    }

    @Override
    public MyCompany getCompanyByTicker(String ticker) throws CompanyNotFoundException {
        fillListIfEmpty();
        MyCompany company = companiesMap.get(ticker);
        if (company == null) throw new CompanyNotFoundException(ticker);
        return company;
    }

    @Override
    public List<MyCompany> getAllCompanies() {
        fillListIfEmpty();
        return companiesList;
    }

    @Override
    public void save(MyCompany company) {
        fillListIfEmpty();
        companiesList.add(company);
        companiesMap.put(company.getTicker(), company);
    }

    @Override
    public void delete(MyCompany company) {
        fillListIfEmpty();
        companiesList.remove(company);
        companiesMap.remove(company.getTicker());
    }

    private void fillListIfEmpty() {
        if (companiesList == null) {
            companiesList = companyRepository.findAll();
            companiesMap = getCompaniesMapFromList(companiesList);
        }
    }

    private Map<String, MyCompany> getCompaniesMapFromList(List<MyCompany> companiesList) {
        Map<String, MyCompany> companyMap = new HashMap<>();
        for (MyCompany company : companiesList) {
            companyMap.put(company.getTicker(), company);
        }
        return companyMap;
    }
}
