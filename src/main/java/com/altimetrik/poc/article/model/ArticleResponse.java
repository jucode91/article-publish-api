package com.altimetrik.poc.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = -785684425586726743L;

    private Long articleId;

    private String title;

    private String content;

    private String author;

    private Status status;

    private Integer voteCount;

    private Integer version;

}
