package com.ipdive.springboottemplate.dao.company;

import com.ipdive.springboottemplate.models.MyCompany;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface CompanyRepository extends CrudRepository<MyCompany, String> {

    @EnableScan
    List<MyCompany> findAll();

    Optional<MyCompany> findByNameIs(String name);

    @EnableScan
    List<MyCompany> findByName(String name);

    void deleteByTicker(String ticker);

}
