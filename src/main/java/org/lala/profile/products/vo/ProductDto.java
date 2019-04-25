package org.lala.profile.products.vo;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotEmpty
    private String name;

    private String repImageUrl;

    @NotEmpty
    private String introduce;

    private String[] techs;

    private String[] imageUrls;

    private String description;

    private String color;
}
