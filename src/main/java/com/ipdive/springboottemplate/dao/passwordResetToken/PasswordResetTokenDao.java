package com.ipdive.springboottemplate.dao.passwordResetToken;

import com.ipdive.springboottemplate.exceptions.PasswordResetTokenNotFoundException;
import com.ipdive.springboottemplate.models.PasswordResetToken;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetTokenDao {

    void save(PasswordResetToken passwordResetToken);

    PasswordResetToken getPasswordResetToken(String tokenId) throws PasswordResetTokenNotFoundException;

    void deletePasswordResetToken(PasswordResetToken passwordResetToken);

    List<PasswordResetToken> getAll();
}
