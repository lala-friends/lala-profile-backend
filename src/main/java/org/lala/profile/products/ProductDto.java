package org.lala.profile.products;

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

    @NotEmpty
    private String introduce;

    @NotEmpty
    private String[] tech;

    private String imageUrl;
    private String color;
}
