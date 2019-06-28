package com.altimetrik.poc.article.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="ARTICLE")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long articleId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String author;

    @NotNull
    private String status;

    @NotNull
    private Integer voteCount;

    @NotNull
    private Integer version;

}
