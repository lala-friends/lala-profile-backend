package org.lala.profile.accounts.repository;

import org.lala.profile.accounts.vo.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String username);
}
