package org.lala.profile.person.vo;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String introduce;
    private String imageUrl;
    private String repColor;
    private String blog;
    private String github;
    private String facebook;
}
