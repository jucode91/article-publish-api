package com.altimetrik.poc.article.resources;

import com.altimetrik.poc.article.api.ArticlePublishServiceEndpoint;
import com.altimetrik.poc.article.model.*;
import com.altimetrik.poc.article.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class ArticleResource  implements ArticlePublishServiceEndpoint {

    private ArticleService articleService;

    /**
     * get all articles
     * @return: Article List
     */
    @Override
    public Resources<ArticleResponse> getArticles() {

        log.info("resources getArticles started");
        log.debug("resources getArticles started -- DEBUG");

        List<ArticleResponse> allCustomers = articleService.getAllArticles();

        for (ArticleResponse article : allCustomers) {
            Link selfLink = linkTo(ArticleResource.class).slash(article.getTitle()).withSelfRel();
            article.add(selfLink);
        }

        Link link = linkTo(ArticleResource.class).withSelfRel();
        Resources<ArticleResponse> result = new Resources<ArticleResponse>(allCustomers, link);

        log.info("resources getArticles completed");

        return result;
    }

    /**
     * @param articleAddRequest: add article model
     * @return:  added Article
     */
    @Override
    public Resource<ArticleResponse> addArticle(ArticleAddRequest articleAddRequest) {
        log.info("resources addArticles started");
        ArticleResponse article = articleService.addArticle(articleAddRequest);
        Link selfLink = linkTo(ArticleResource.class).slash(article.getTitle()).withSelfRel();
        article.add(selfLink);
        Resource<ArticleResponse> result = new Resource<ArticleResponse>(article, selfLink);
        log.info("resources addArticles completed");
        return result;
    }

    /**
     * @param articleTitle
     * @return
     */
    @Override
    public  Resources<ArticleResponse> getArticlesByTitle(String articleTitle) {
        log.info("resources getArticlesByTitle started");
        List<ArticleResponse> articlesByTitle = articleService.getArticlesByTitle(articleTitle);

        Link link = linkTo(methodOn(ArticleResource.class)
                .getArticles()).withRel("allArticles");
        Resources<ArticleResponse> result = new Resources<ArticleResponse>(articlesByTitle, link);
        log.info("resources getArticlesByTitle completed");
        return result;
    }

    /**
     * @param articleTitle
     * @param articleUpdateRequest
     * @return
     */
    @Override
    public Resource<ArticleResponse> updateArticle(String articleTitle, ArticleUpdateRequest articleUpdateRequest) {
        log.info("resources updateArticle started");
        ArticleResponse updateArticle = articleService.updateArticle(articleTitle, articleUpdateRequest);

        Link link = linkTo(methodOn(ArticleResource.class)
                .getArticlesByTitle(updateArticle.getTitle())).withRel("article");
        Resource<ArticleResponse> result = new Resource<ArticleResponse>(updateArticle, link);
        log.info("resources updateArticle completed");
        return result;
    }

    /**
     * @param articleTitle
     * @param voteRequest
     */
    @Override
    public void vote(String articleTitle, VoteRequest voteRequest) {
        log.info("resources vote started");
        articleService.vote(articleTitle, voteRequest);
        log.info("resources vote completed");
    }

    /**
     * @param articleTitle
     */
    @Override
    public void deleteArticle(String articleTitle) {
        log.info("resources deleteArticle started");
        articleService.deleteArticle(articleTitle);
        log.info("resources deleteArticle completed");
    }

    /**
     * @param articleId
     * @return
     */
    @Override
    public Resource<ArticleResponse> activateAnArticle(@Valid Long articleId) {
        log.info("resources activateAnArticle started");
        ArticleResponse activatedArticle = articleService.activateAnArticle(articleId);
        Link link = linkTo(methodOn(ArticleResource.class)
                .getArticlesByTitle(activatedArticle.getTitle())).withRel("article");
        Resource<ArticleResponse> result = new Resource<ArticleResponse>(activatedArticle, link);
        log.info("resources activateAnArticle completed");
        return result;
    }

    /**
     * @return
     */
    @Override
    public ArticleStatistics getArticleStatistics() {
        log.info("resources getArticleStatistics started");
        ArticleStatistics articleStatistics = articleService.getArticleStatistics();
        log.info("resources getArticleStatistics completed");
        return articleStatistics;

    }
}
