package com.oliynyk.play.web.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAddRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
