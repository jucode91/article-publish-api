package com.altimetrik.poc.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleStatistics {

    private Long totalNoOfArticles;
    private ArticleResponse mostPopularArticle;
    private Long totalNoOfVersionForMostPopArticles;
    private ArticleResponse leastPopularArticle;
    private Long totalNoOfVersionForLeastPopArticles;
}
