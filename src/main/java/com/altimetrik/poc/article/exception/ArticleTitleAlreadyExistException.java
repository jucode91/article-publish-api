package com.altimetrik.poc.article.exception;

public class ArticleTitleAlreadyExistException extends ArticleException {
    public ArticleTitleAlreadyExistException(String message) {
        super(message);
    }
}
