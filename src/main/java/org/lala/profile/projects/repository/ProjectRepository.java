package org.lala.profile.projects.repository;

import org.lala.profile.accounts.vo.Account;
import org.lala.profile.projects.vo.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<List<Project>> findByOwner(Account owner);
}
