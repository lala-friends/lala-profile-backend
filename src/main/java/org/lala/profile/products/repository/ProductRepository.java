package org.lala.profile.products.repository;

import org.lala.profile.products.vo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
