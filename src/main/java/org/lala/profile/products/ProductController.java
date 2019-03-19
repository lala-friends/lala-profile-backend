package org.lala.profile.products;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/products", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
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
    public ResponseEntity createProduct(@RequestBody @Valid ProductDto productDto, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        productValidator.validate(productDto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Product product = modelMapper.map(productDto, Product.class);
        Product newProduct = this.productRepository.save(product);

        URI createUri = linkTo(ProductController.class).slash(newProduct.getId()).toUri();
        product.setId(10);
        return ResponseEntity.created(createUri).body(product);
    }

    @GetMapping
    public ResponseEntity getAllProduct() {
        return ResponseEntity.ok(productRepository.findAll());
    }
}
