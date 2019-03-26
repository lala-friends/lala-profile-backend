package org.lala.profile.accounts.service;

import org.lala.profile.accounts.config.AccountAdapter;
import org.lala.profile.accounts.repository.AccountsRepository;
import org.lala.profile.accounts.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountsService implements UserDetailsService {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountsRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountsRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(username));
        return new AccountAdapter(account);
    }
}
