package org.lala.profile.products;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ProductValidator {

    public void validate(ProductDto productDto, Errors errors) {

//        if (productDto.getProductDetailDto() == null || productDto.getProductDetailDto() == null) {
//            errors.rejectValue("productDetail", "emptyEntity", "ProductDetail is essential");
//            return;
//        }

        UrlValidator urlValidator = new UrlValidator();

        if (!productDto.getImageUrl().isEmpty() &&
                !urlValidator.isValid(productDto.getImageUrl())) {
            errors.rejectValue("imageUrl", "wrongValue", "Image URL is not URL Pattern");
            return;
        }

//        if (productDto.getProductDetailDto() != null &&
//                !productDto.getProductDetailDto().getImageUrl().isEmpty() &&
//                !urlValidator.isValid(productDto.getProductDetailDto().getImageUrl())) {
//            errors.rejectValue("productDetail.imageUrl", "wrongValue", "Image URL is not URL Pattern");
//             return;
//        }
    }
}
