package com.altimetrik.poc.article.service;

import com.altimetrik.poc.article.entity.ArticleEntity;
import com.altimetrik.poc.article.exception.ArticleNotActiveException;
import com.altimetrik.poc.article.exception.ArticleNotFoundException;
import com.altimetrik.poc.article.exception.ArticleTitleAlreadyExistException;
import com.altimetrik.poc.article.model.*;
import com.altimetrik.poc.article.repository.ArticleRepository;
import com.altimetrik.poc.article.utils.ArticleConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository repository;

    //Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Override
    public List<ArticleResponse> getAllArticles() {

        return ArticleConverter.convertArticleEntityListToPojoList(repository.findAll());
    }

    /**
     * @param articleAddRequest
     * @return
     */
    @Override
    public ArticleResponse addArticle(ArticleAddRequest articleAddRequest) {

        if (!CollectionUtils.isEmpty(repository.findByTitle(articleAddRequest.getTitle()))) {
            log.debug("Article with Title : " + articleAddRequest.getTitle() + " already exist");
            throw new ArticleTitleAlreadyExistException("Article with Title : " + articleAddRequest.getTitle() + " already exist");
        }
        ArticleEntity entity = ArticleConverter.convertPojoToArticleEntity(articleAddRequest);

        ArticleEntity articleEntity = repository.save(entity);

        return ArticleConverter.convertArticleEntityToPojo(articleEntity);
    }

    /**
     * @param articleTitle
     * @return
     */
    @Override
    public List<ArticleResponse> getArticlesByTitle(String articleTitle) {
        List<ArticleEntity> entities = repository.findByTitle(articleTitle);
        if(CollectionUtils.isEmpty(entities)) {
            log.debug("Article not found with Title : " + articleTitle);
            throw new ArticleNotFoundException("Article not found with Title : " + articleTitle);
        }
        return ArticleConverter.convertArticleEntityListToPojoList(entities);
    }


    /**
     * @param articleTitle
     * @param articleUpdateRequest
     * @return
     */
    @Override
    public synchronized ArticleResponse updateArticle(String articleTitle, ArticleUpdateRequest articleUpdateRequest) {

        ArticleEntity entity = validateArticleTitleAndGetActiveArticle(articleTitle);

        if(null == entity) {
            log.debug("Article : "+articleTitle +" is not in Active status hence cannot update, Kindly create an Article first");
            throw new ArticleNotActiveException("Article : "+articleTitle +" is not in Active status hence cannot update, Kindly create an Article first");
        }

        entity.setStatus(Status.INACTIVE.toString());
        repository.save(entity);

        ArticleEntity articleEntity = ArticleConverter.convertPojoToArticleEntity(articleUpdateRequest);
        articleEntity.setTitle(articleTitle);
        ArticleEntity updatedArticle = repository.save(articleEntity);
        return ArticleConverter.convertArticleEntityToPojo(updatedArticle);
    }

    /**
     * @param articleTitle
     * @param voteRequest
     */
    @Override
    public void vote(String articleTitle, VoteRequest voteRequest) {
        ArticleEntity entity = validateArticleTitleAndGetActiveArticle(articleTitle);

        if(null == entity) {
            log.debug("Article : "+articleTitle +" is not in Active status hence cannot vote");
            throw new ArticleNotActiveException("Article : "+articleTitle +" is not in Active status hence cannot vote");
        }

        if (voteRequest.getVote().equals(Vote.UP)) {
            entity.setVoteCount(entity.getVoteCount() + 1);
        } else {
            entity.setVoteCount(entity.getVoteCount() - 1);
        }

        repository.save(entity);
    }

    /**
     * @param articleTitle
     */
    @Override
    public void deleteArticle(String articleTitle) {

        validaArticleByTitle(articleTitle);

        repository.deleteByTitle(articleTitle);
    }

    /**
     * @param articleId
     * @return
     */
    @Override
    public synchronized ArticleResponse activateAnArticle(Long articleId) {
        ArticleEntity activateArticle = repository.getOne(articleId);

        List<ArticleEntity> entities = repository.findByTitle(activateArticle.getTitle()).stream()
                .filter(articleEntity -> articleEntity.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());

        if(null != entities){
             ArticleEntity entity = entities .get(0);
             entity.setStatus(Status.INACTIVE.toString());
            repository.save(entity);
        }
         activateArticle.setStatus(Status.ACTIVE.toString());

        return ArticleConverter.convertArticleEntityToPojo(repository.save(activateArticle));
    }

    /**
     * @return
     */
    @Override
    public ArticleStatistics getArticleStatistics() {
        ArticleStatistics articleStatistics =new ArticleStatistics();

        List<ArticleEntity> articleEntityList = repository.findAll();

        if(null != articleEntityList) {
            ArticleEntity mostPopularArticle = articleEntityList.get(0);
            ArticleEntity leastPopularArticle = articleEntityList.get(0);

            for (ArticleEntity articleEntity : articleEntityList) {
                if (articleEntity.getVoteCount() > mostPopularArticle.getVoteCount()) {
                    mostPopularArticle = articleEntity;
                }

                if (articleEntity.getVoteCount() < leastPopularArticle.getVoteCount()) {
                    leastPopularArticle = articleEntity;
                }
            }

            articleStatistics.setTotalNoOfArticles((long) articleEntityList.size());
            articleStatistics.setMostPopularArticle(ArticleConverter.convertArticleEntityToPojo(mostPopularArticle));
            articleStatistics.setTotalNoOfVersionForMostPopArticles(mostPopularArticle.getVoteCount()+1L);
            articleStatistics.setLeastPopularArticle(ArticleConverter.convertArticleEntityToPojo(leastPopularArticle));
            articleStatistics.setTotalNoOfVersionForLeastPopArticles(leastPopularArticle.getVoteCount()+1L);

        }
        return articleStatistics;
    }

    private ArticleEntity validateArticleTitleAndGetActiveArticle(String articleTitle) {

        return validaArticleByTitle(articleTitle)
                .stream()
                .filter(articleEntity -> articleEntity.getStatus().equals(Status.ACTIVE.toString()))
                .collect(Collectors.toList()).get(0);
    }

    private  List<ArticleEntity> validaArticleByTitle(String articleTitle){
        List<ArticleEntity> articleEntities = repository.findByTitle(articleTitle);
        if (CollectionUtils.isEmpty(articleEntities)) {
            log.debug("Article not found with Article name : " + articleTitle);
            throw new ArticleNotFoundException("Article not found with Article name : " + articleTitle);
        }
        return articleEntities;
    }

}
