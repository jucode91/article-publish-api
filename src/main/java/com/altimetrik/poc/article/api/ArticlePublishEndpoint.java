package com.altimetrik.poc.article.api;

import com.altimetrik.poc.article.model.ArticleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static java.net.HttpURLConnection.*;

@Api(tags = "Article Publish API", description = "Altimetrik API for Article Publish")
@ApiResponses({
        @ApiResponse(code = HTTP_OK, message = "Success", response = ArticleResponse.class),
        @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid Input", response = Error.class),
        @ApiResponse(code = HTTP_NOT_FOUND, message = "Requested ID not found", response = Error.class),
        @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Internal Server Error", response = Error.class)})


public interface ArticlePublishEndpoint {
        String ARTICLE_PUBLISH_SERVICE_ENDPOINT="/articles";
}
