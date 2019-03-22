package org.lala.profile.configs;

import org.apache.commons.validator.routines.UrlValidator;
import org.lala.profile.accounts.Account;
import org.lala.profile.accounts.AccountRole;
import org.lala.profile.accounts.AccountsService;
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

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                        .email("whuk@naver.com")
                        .password("fkfkvmfpswm")
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .build();
                accountsService.saveAccount(account);

            }
        };
    }
}
