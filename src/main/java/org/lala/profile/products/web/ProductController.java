package org.lala.profile.products.web;

import org.lala.profile.accounts.config.CurrentUser;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.accounts.vo.AccountRole;
import org.lala.profile.products.repository.ProductRepository;
import org.lala.profile.products.vo.Product;
import org.lala.profile.products.vo.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/products", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ProductController {

    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    private ProductValidator productValidator;

    public ProductController(ProductRepository productRepository, ModelMapper modelMapper, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.productValidator = productValidator;
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody @Valid ProductDto productDto, Errors errors
            , @CurrentUser Account currentUser) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        productValidator.validate(productDto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Product product = modelMapper.map(productDto, Product.class);
        product.setOwner(currentUser);
        Product newProduct = this.productRepository.save(product);

        URI createUri = linkTo(ProductController.class).slash(newProduct.getId()).toUri();
        return ResponseEntity.created(createUri).body(newProduct);
    }

    @GetMapping
    public ResponseEntity getAllProduct() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity modifyProduct(@PathVariable Integer id, @RequestBody @Valid ProductDto productDto, Errors errors
            , @CurrentUser Account currentUser) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Product existsProduct = productOptional.get();
        boolean isAdmin = currentUser.getRoles().stream().filter(r -> r.equals(AccountRole.ADMIN)).count() == 1 ? true : false;
        if (!isAdmin && !existsProduct.getOwner().getEmail().equals(currentUser.getEmail())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        this.modelMapper.map(productDto, existsProduct);
        Product savedProduct = productRepository.save(existsProduct);
        return ResponseEntity.ok(savedProduct);
    }
}
