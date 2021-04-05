package com.ipdive.springboottemplate.dao.user;

import com.ipdive.springboottemplate.models.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {

    @EnableScan
    Optional<User> findByUsername(String username);

}
