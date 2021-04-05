package com.ipdive.springboottemplate.dao.passwordResetToken;

import com.ipdive.springboottemplate.exceptions.PasswordResetTokenNotFoundException;
import com.ipdive.springboottemplate.models.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PasswordResetTokenDbDao implements PasswordResetTokenDao {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void save(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String tokenId) throws PasswordResetTokenNotFoundException {
        Optional<PasswordResetToken> token = passwordResetTokenRepository.findById(tokenId);
        token.orElseThrow(() -> new PasswordResetTokenNotFoundException(tokenId));
        return token.get();
    }

    @Override
    public void deletePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    public List<PasswordResetToken> getAll() {
        List<PasswordResetToken> list = new ArrayList<>();
        Iterable<PasswordResetToken> iterable = passwordResetTokenRepository.findAll();
        for (PasswordResetToken token : iterable) list.add(token); // TODO: Streamline?
        return list;
    }
}
