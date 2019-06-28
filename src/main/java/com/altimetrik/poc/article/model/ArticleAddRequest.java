package com.altimetrik.poc.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAddRequest extends ArticleRequest {

    private static final long serialVersionUID = -5349670237653238331L;

    @NotNull
    private String title;
}
