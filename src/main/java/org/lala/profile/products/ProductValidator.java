package org.lala.profile.products;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ProductValidator {

    public void validate(ProductDto productDto, Errors errors) {
        UrlValidator urlValidator = new UrlValidator();

        if (!productDto.getImageUrl().isEmpty() &&
                !urlValidator.isValid(productDto.getImageUrl())) {
            errors.rejectValue("imageUrl", "wrongValue", "Image URL is not URL Pattern");
        }
    }
}
