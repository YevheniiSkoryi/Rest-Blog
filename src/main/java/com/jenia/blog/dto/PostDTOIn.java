package com.jenia.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostDTOIn {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

}
