package com.altimetrik.poc.article.exception;

import lombok.Data;

@Data
public class Error {
    private String errorMessage;
    private String errorDescription;
    private int status;
}

