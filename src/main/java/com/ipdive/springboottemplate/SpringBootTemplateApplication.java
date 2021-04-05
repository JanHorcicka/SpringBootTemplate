package com.ipdive.springboottemplate;

import com.ipdive.springboottemplate.configuration.DynamoDBConfig;
import com.ipdive.springboottemplate.dao.company.CompanyRepository;
import com.ipdive.springboottemplate.dao.passwordResetToken.PasswordResetTokenRepository;
import com.ipdive.springboottemplate.dao.user.UserDao;
import com.ipdive.springboottemplate.dao.user.UserDaoDatabase;
import com.ipdive.springboottemplate.dao.user.UserRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, // No JPA
        DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableDynamoDBRepositories(basePackageClasses = {UserRepository.class, CompanyRepository.class, PasswordResetTokenRepository.class})
@Configuration
@Import(DynamoDBConfig.class)
@EnableScheduling
public class SpringBootTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTemplateApplication.class, args);
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoDatabase();
    }

}
