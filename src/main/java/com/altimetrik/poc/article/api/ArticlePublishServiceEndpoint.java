package com.altimetrik.poc.article.api;

import com.altimetrik.poc.article.model.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.net.HttpURLConnection.*;

@RequestMapping(value = ArticlePublishEndpoint.ARTICLE_PUBLISH_SERVICE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public interface ArticlePublishServiceEndpoint extends ArticlePublishEndpoint {

    @GetMapping()
    @ApiOperation(value = "Get all Articles", notes = "Endpoint provide capability to get all Articles")
    @ApiResponse(code = HTTP_OK, message = "Success", response = ArticleRequest.class)
    Resources<ArticleResponse> getArticles();

    @PostMapping()
    @ApiOperation(value = "Add Articles", notes = "Endpoint provide capability get data for an Article")
    @ApiResponse(code = HTTP_CREATED, message = "Success", response = ArticleRequest.class)
    Resource<ArticleResponse> addArticle(@ApiParam(value = "ArticleAddRequest, contain needed fields to create an Article(Request)")
                       @Valid @RequestBody ArticleAddRequest articleAddRequest);

    @GetMapping("/{articleTitle}")
    @ApiOperation(value = "Get Articles Based on Title", notes = "Endpoint provide capability to get details about an Article")
    @ApiResponse(code = HTTP_OK, message = "Success", response = ArticleRequest.class)
    Resources<ArticleResponse> getArticlesByTitle(@ApiParam(value = "ArticleRequest Title", name = "articleTitle", example = "java", required = true)
              @Valid @PathVariable String articleTitle);

    @PutMapping("/{articleTitle}")
    @ApiOperation(value = "Update an Article", notes = "Endpoint provide capability to update an Article")
    @ApiResponse(code = HTTP_OK, message = "Success", response = ArticleRequest.class)
    Resource<ArticleResponse> updateArticle(@ApiParam(value = "ArticleRequest Title", name = "articleTitle", example = "java", required = true)
                       @Valid @PathVariable String articleTitle,
                       @ApiParam(value = "ArticleRequest update request, contain needed fields to update an ArticleRequest")
                       @Valid @RequestBody ArticleUpdateRequest articleUpdateRequest);

    @PostMapping("/{articleTitle}")
    @ApiOperation(value = "Vote for an Article", notes = "Endpoint provide use to vote(UP/DOWN) for an Article")
    @ApiResponse(code = HTTP_OK, message = "Success", response = ArticleRequest.class)
    void vote(@ApiParam(value = "ArticleRequest Title", name = "String articleTitle", example = "java", required = true)
              @Valid @PathVariable String articleTitle,
              @ApiParam(value = "ArticleRequest vote request, contain needed fields to vote for an Article Request")
              @Valid @RequestBody VoteRequest voteRequest);

    @DeleteMapping("/{articleTitle}")
    @ApiOperation(value = "Delete Article(s) based on Title", notes = "Endpoint provide capability to get all article based on article name")
    @ApiResponse(code = HTTP_NO_CONTENT, message = "Success", response = ArticleRequest.class)
    void deleteArticle(@ApiParam(value = "ArticleRequest Title", name = "String articleTitle", example = "java", required = true)
                       @Valid @PathVariable String articleTitle);

    @PatchMapping("/{articleId}/activate")
    @ApiOperation(value = "Activate an Article", notes = "Endpoint provide capability to get all Articles")
    @ApiResponse(code = HTTP_OK, message = "Success", response = ArticleRequest.class)
    Resource<ArticleResponse> activateAnArticle(@ApiParam(value = "ArticleRequest Title", name = "Long articleId", example = "1", required = true)
                                                 @Valid @PathVariable Long articleId);

    @GetMapping("/statistics")
    @ApiOperation(value = "Get Article Statistics", notes = "Endpoint provide capability to get all Articles")
    @ApiResponse(code = HTTP_OK, message = "Success", response = ArticleResponse.class)
    ArticleStatistics getArticleStatistics();
}
