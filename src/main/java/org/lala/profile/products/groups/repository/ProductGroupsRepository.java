package org.lala.profile.products.groups.repository;

import org.lala.profile.products.groups.vo.ProductGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGroupsRepository extends JpaRepository<ProductGroups, Integer> {
}
