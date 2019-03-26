package org.lala.profile.products.web;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.lala.profile.products.vo.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ProductValidator {

    @Autowired
    private UrlValidator urlValidator;

    public void validate(ProductDto productDto, Errors errors) {

        // image url 의 유효성 체크
        if(!ArrayUtils.isEmpty(productDto.getImageUrls())) {
            for (String url: productDto.getImageUrls()) {
                if(StringUtils.isEmpty(url) || !urlValidator.isValid(url)) {
                    errors.rejectValue("imageUrls", "wrongValue", "Image URL is not URL Pattern");
                    return;
                }
            }
        }
    }
}
