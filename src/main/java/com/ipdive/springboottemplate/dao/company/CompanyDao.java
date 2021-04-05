package com.ipdive.springboottemplate.dao.company;

import com.ipdive.springboottemplate.exceptions.CompanyNotFoundException;
import com.ipdive.springboottemplate.models.MyCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDao {

    MyCompany getCompanyByTicker(String ticker) throws CompanyNotFoundException;

    List<MyCompany> getAllCompanies();

    void save(MyCompany company);

    void delete(MyCompany company);

}
