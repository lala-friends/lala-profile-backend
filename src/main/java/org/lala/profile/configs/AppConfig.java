package org.lala.profile.configs;

import org.apache.commons.validator.routines.UrlValidator;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.accounts.vo.AccountRole;
import org.lala.profile.accounts.service.AccountsService;
import org.lala.profile.commons.AppProperties;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            AccountsService accountsService;

            @Autowired
            AppProperties appProperties;

            @Autowired
            PersonRepository personRepository;

            @Override
            public void run(ApplicationArguments args) throws Exception {

                Account admin = Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .build();
                accountsService.saveAccount(admin);

                Person adminPerson = Person.builder()
                        .email(appProperties.getAdminUsername())
                        .build();
                personRepository.save(adminPerson);

                Account user = Account.builder()
                        .email(appProperties.getUserUsername())
                        .password(appProperties.getUserPassword())
//                        .roles(Set.of(AccountRole.USER))
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .build();
                accountsService.saveAccount(user);

                Person userPerson = Person.builder()
                        .email(appProperties.getUserUsername())
                        .build();
                personRepository.save(userPerson);

            }
        };
    }
}
