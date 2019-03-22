package org.lala.profile.configs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.Account;
import org.lala.profile.accounts.AccountRole;
import org.lala.profile.accounts.AccountsService;
import org.lala.profile.common.AbstractCommonTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends AbstractCommonTest {

    @Autowired
    AccountsService accountsService;

    @Test
    @DisplayName("인증 토큰을 발급받는 테스트")
    void getAuthToken() throws Exception {
        String username = "whuk@naver.com";
        String password = "fkfkvmfpswm";

//        Account account = Account.builder()
//                .email(username)
//                .password(password)
//                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//                .build();
//        accountsService.saveAccount(account);

        String clientId = "lala-profile-client";
        String clientSecret = "lala-profile-secret";

        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
        ;
    }

}