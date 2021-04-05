package com.ipdive.springboottemplate.dao.passwordResetToken;

import com.ipdive.springboottemplate.models.PasswordResetToken;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, String> {



}
