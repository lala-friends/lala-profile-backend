package org.lala.profile.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.service.AccountsService;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.accounts.vo.AccountRole;
import org.lala.profile.common.AbstractCommonTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AccountsServiceTest extends AbstractCommonTest {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        accountsService.saveAccount(account);

        // When
        UserDetailsService userDetailsService = accountsService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();

    }

    @Test
    @DisplayName("Username 찾기 실패")
    void findByUsernameFail() {
        String noUser = "nouser@naver.com";

        try {
            accountsService.loadUserByUsername(noUser);
            fail("supposed to be fail");
        } catch (UsernameNotFoundException e) {
            assertThat(e instanceof UsernameNotFoundException).isTrue();
            assertThat(e.getMessage()).containsSequence(noUser);
        }
    }

}