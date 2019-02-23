package org.lala.profile.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
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
}
