package org.lala.profile.products.vo;

import lombok.*;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.products.groups.vo.ProductGroups;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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

    @NotEmpty
    private String[] techs;

    private String[] imageUrls;

    private String description;

    private String color;

    private Account owner;
}
