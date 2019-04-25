package org.lala.profile.products.groups.service;

import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.products.groups.repository.ProductGroupsRepository;
import org.lala.profile.products.groups.vo.ProductGroups;
import org.lala.profile.products.repository.ProductRepository;
import org.lala.profile.products.vo.Product;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductGroupService {

    private ProductGroupsRepository productGroupsRepository;

    private PersonRepository personRepository;

    private ProductRepository productRepository;

    public ProductGroupService(ProductGroupsRepository productGroupsRepository, PersonRepository personRepository, ProductRepository productRepository) {
        this.productGroupsRepository = productGroupsRepository;
        this.personRepository = personRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByEmail(String email) {
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        Optional<List<ProductGroups>> byPerson = productGroupsRepository.findByPerson(person);
        if (byPerson.isPresent()) {
            List<Product> personsProductList = new ArrayList<>();
            List<ProductGroups> byPersonList = byPerson.get();
            byPersonList.stream().forEach(p -> {
                personsProductList.add(p.getProduct());
            });
            return personsProductList;
        } else {
            return null;
        }
    }
}
