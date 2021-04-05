package com.ipdive.springboottemplate.dao.user;

import com.ipdive.springboottemplate.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoDatabase implements UserDao {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();
        Iterable<User> iterable = userRepository.findAll();
        for (User user : iterable) list.add(user); // TODO: Streamline?
        return list;
    }

    @Override
    public void enableUser(String username) {
        User user = getUserByUsername(username);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(String username) {
        User user = getUserByUsername(username);
        user.setEnabled(false);
        userRepository.save(user); // TODO: Duplication of code
    }

    @Override
    public void enablePremium(String username, long premiumUntil) {
        User user = getUserByUsername(username);
        user.setPremium(true);
        user.setPremiumExpiryDateEpoch(premiumUntil);
        userRepository.save(user);
    }

    @Override
    public void disablePremium(String username) {
        User user = getUserByUsername(username);
        user.setPremium(false);
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.orElseThrow(() -> {
            return new UsernameNotFoundException(String.format("Cannot find user with username %s", username));
        });
        return userOptional.get();
    }
}
