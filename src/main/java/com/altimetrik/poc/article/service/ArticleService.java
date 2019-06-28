package com.altimetrik.poc.article.service;

import com.altimetrik.poc.article.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {

    List<ArticleResponse> getAllArticles();

    ArticleResponse addArticle(ArticleAddRequest articleAddRequest);

    List<ArticleResponse> getArticlesByTitle(String articleTitle);

    @Transactional
    ArticleResponse updateArticle(String articleTitle, ArticleUpdateRequest articleUpdateRequest);

    void vote(String articleTitle, VoteRequest voteRequest);

    @Transactional
    void deleteArticle(String articleTitle);

    ArticleResponse activateAnArticle(Long articleId);

    ArticleStatistics getArticleStatistics();
}
