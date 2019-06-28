package com.altimetrik.poc.article.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ArticleRequest implements Serializable {

    private static final long serialVersionUID = 1223488220362273007L;

    @NotNull
    private String content;

    @NotNull
    private String author;

}
