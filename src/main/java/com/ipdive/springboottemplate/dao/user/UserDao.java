package com.ipdive.springboottemplate.dao.user;

import com.ipdive.springboottemplate.models.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@EnableScan
public interface UserDao {

    User save(User user);

    void delete(User user);

    List<User> getAllUsers();

    void enableUser(String username);

    void disableUser(String username);

    void enablePremium(String username, long premiumUntil);

    void disablePremium(String username);

    User getUserByUsername(String username) throws UsernameNotFoundException;

}
