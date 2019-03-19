package org.lala.profile.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.common.AbstractCommonTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountsServiceTest extends AbstractCommonTest {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AccountsRepository accountsRepository;

    @Test
    @DisplayName("Accounts Service 에서 Username 을 찾는다.")
    void findByUsername() {
        // Given
        String email = "ryan@naver.com";
        String password = "ryan";
        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        accountsRepository.save(account);

        // When
        UserDetailsService userDetailsService = accountsService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }

}