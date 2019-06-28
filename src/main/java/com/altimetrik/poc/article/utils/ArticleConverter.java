package com.altimetrik.poc.article.utils;

import com.altimetrik.poc.article.entity.ArticleEntity;
import com.altimetrik.poc.article.model.ArticleAddRequest;
import com.altimetrik.poc.article.model.ArticleRequest;
import com.altimetrik.poc.article.model.ArticleResponse;
import com.altimetrik.poc.article.model.Status;

import java.util.ArrayList;
import java.util.List;

public class ArticleConverter {

    public static ArticleResponse convertArticleEntityToPojo(ArticleEntity articleEntity){
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setArticleId(articleEntity.getArticleId());
        articleResponse.setAuthor(articleEntity.getAuthor());
        articleResponse.setTitle(articleEntity.getTitle());
        articleResponse.setContent(articleEntity.getContent());
        articleResponse.setVersion(articleEntity.getVersion());
        articleResponse.setVoteCount(articleEntity.getVoteCount());
        articleResponse.setStatus(Status.valueOf(articleEntity.getStatus()));
        return articleResponse;
    }

    public static List<ArticleResponse> convertArticleEntityListToPojoList(List<ArticleEntity> articleEntityList){
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        articleEntityList.forEach((articleEntity)-> articleResponseList.add(convertArticleEntityToPojo(articleEntity)));
        return articleResponseList;
    }

    public static ArticleEntity convertPojoToArticleEntity(ArticleRequest articleRequest){
        ArticleEntity articleEntity = new ArticleEntity();
        if(articleRequest instanceof ArticleAddRequest){
            articleEntity.setTitle(((ArticleAddRequest)articleRequest).getTitle());
        }
        articleEntity.setContent(articleRequest.getContent());
        articleEntity.setAuthor(articleRequest.getAuthor());
        articleEntity.setStatus(Status.ACTIVE.toString());
        articleEntity.setVersion(0);
        articleEntity.setVoteCount(0);
        return articleEntity;
    }
}
