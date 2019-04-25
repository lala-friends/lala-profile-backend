package org.lala.profile.products.groups.repository;

import org.lala.profile.person.vo.Person;
import org.lala.profile.products.groups.vo.ProductGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductGroupsRepository extends JpaRepository<ProductGroups, Integer> {

    Optional<List<ProductGroups>> findByPerson(Person person);
}
