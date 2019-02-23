package org.lala.profile.products;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/products", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product product) {
        Product newProduct = this.productRepository.save(product);

        URI createUri = linkTo(ProductController.class).slash(newProduct.getId()).toUri();
        product.setId(10);
        return ResponseEntity.created(createUri).body(product);
    }
}
