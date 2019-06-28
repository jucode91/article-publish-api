package com.altimetrik.poc.article;

import com.altimetrik.poc.article.exception.ArticleNotFoundException;
import com.altimetrik.poc.article.exception.ArticleTitleAlreadyExistException;
import com.altimetrik.poc.article.model.*;
import com.altimetrik.poc.article.resources.ArticleResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticlePublishingApiApplicationTests {

	@Autowired
	private ArticleResource resource;

	private ArticleAddRequest request = new ArticleAddRequest();

	@Test
	public void testAddAnArticle() {

		request = prepareArticleAddReuest("TITLE_01", "Test Author 01", "Test Content 01");
		Resource<ArticleResponse> articleResponseResource = resource.addArticle(request);
		ArticleResponse addedArticle = articleResponseResource.getContent();

		assertNotNull(addedArticle);
		assertNotNull(addedArticle.getArticleId());
		assertEquals(addedArticle.getStatus(), Status.ACTIVE);
		assertEquals(addedArticle.getTitle(), "TITLE_01");
	}

	@Test(expected = ArticleTitleAlreadyExistException.class)
	public void testAddAnArticle_WhileArticleIsAlreadyThereWithSameTitle() {

		request = prepareArticleAddReuest("TITLE_01", "Test Author 01", "Test Content 01");
		resource.addArticle(request);
		fail();
	}

	@Test
	public void testUpdateArticle(){

		request = prepareArticleAddReuest("TITLE_02", "Test Author 02", "Test Content 02");
		Resource<ArticleResponse> articleResponseResource = resource.addArticle(request);
		ArticleResponse addedArticle = articleResponseResource.getContent();

		assertNotNull(addedArticle);
		assertNotNull(addedArticle.getArticleId());
		assertEquals(addedArticle.getStatus(), Status.ACTIVE);

		assertEquals(addedArticle.getTitle(), "TITLE_02");

		addedArticle.setAuthor("Test Author 02 UPDATED");
		ArticleUpdateRequest updateRequest = new ArticleUpdateRequest();
		updateRequest.setAuthor(addedArticle.getAuthor());
		updateRequest.setContent("UPDATED CONENT");

		Resource<ArticleResponse> updatedArticleResponse = resource.updateArticle("TITLE_02", updateRequest);
		ArticleResponse updatedArticle = updatedArticleResponse.getContent();

		assertNotNull(updatedArticle);
		assertNotNull(updatedArticle.getArticleId());
		assertEquals(updatedArticle.getStatus(), Status.ACTIVE);
	}

	@Test
	public void testGetArticlesByTitle(){
		request = prepareArticleAddReuest("TITLE_03", "Test Author 03", "Test Content 03");
		resource.addArticle(request);

		Resources<ArticleResponse> articlesResource = resource.getArticles();
		Collection<ArticleResponse> articles = articlesResource.getContent();
		assertTrue(articles.size()>0);
	}

	@Test
	public void testGetParticularArticleByTitle(){
		request = prepareArticleAddReuest("TITLE_04", "Test Author 04", "Test Content 04");
		resource.addArticle(request);


		Resources<ArticleResponse> articlesResource = resource.getArticlesByTitle("TITLE_04");
		Collection<ArticleResponse> articles = articlesResource.getContent();
		assertTrue(articles.size()>0);
		articles.forEach(articleResponse -> assertEquals(articleResponse.getTitle(),"TITLE_04"));

	}

	@Test(expected = ArticleNotFoundException.class)
	public void testGetParticularArticleByNonExistingTitle(){
		request = prepareArticleAddReuest("TITLE_05", "Test Author 05", "Test Content 05");
		resource.addArticle(request);

		resource.getArticlesByTitle("TITLE_044");

		fail();
	}

	@Test(expected = ArticleNotFoundException.class)
	public void testDeleteArticleByTitle(){

		request = prepareArticleAddReuest("TITLE_06", "Test Author 06", "Test Content 06");
		Resource<ArticleResponse> articleResponseResource = resource.addArticle(request);
		ArticleResponse addedArticle = articleResponseResource.getContent();

		assertNotNull(addedArticle);
		assertNotNull(addedArticle.getArticleId());
		assertEquals(addedArticle.getStatus(), Status.ACTIVE);

		resource.deleteArticle("TITLE_06");

		resource.getArticlesByTitle("TITLE_06");

		fail();
	}

	@Test
	public void testVote(){
		request = prepareArticleAddReuest("TITLE_07", "Test Author 07", "Test Content 07");
		Resource<ArticleResponse> articleResponseResource = resource.addArticle(request);
		ArticleResponse addedArticle = articleResponseResource.getContent();

		assertNotNull(addedArticle);
		assertNotNull(addedArticle.getArticleId());
		assertEquals(addedArticle.getStatus(), Status.ACTIVE);
		assertEquals(0, (int) addedArticle.getVoteCount());

		VoteRequest voteRequest =new VoteRequest();
		voteRequest.setVote(Vote.UP);

		resource.vote(addedArticle.getTitle(), voteRequest);

		Resources<ArticleResponse> articlesResource = resource.getArticlesByTitle("TITLE_07");
		Collection<ArticleResponse> votedArticleCollection = articlesResource.getContent();

		votedArticleCollection.forEach(votedArticle -> {
			assertNotNull(votedArticle);
			assertNotNull(votedArticle.getArticleId());
			assertEquals(votedArticle.getStatus(), Status.ACTIVE);
			assertEquals(1, (int) votedArticle.getVoteCount());
		});
	}

	@Test
	public void testGetStatistics(){
		request = prepareArticleAddReuest("TITLE_08", "Test Author 08", "Test Content 08");
		Resource<ArticleResponse> articleResponseResource08 = resource.addArticle(request);
		ArticleResponse addedArticle08 = articleResponseResource08.getContent();

		assertNotNull(addedArticle08);
		assertNotNull(addedArticle08.getArticleId());
		assertEquals(addedArticle08.getStatus(), Status.ACTIVE);
		assertEquals(addedArticle08.getTitle(), "TITLE_08");

		request = prepareArticleAddReuest("TITLE_09", "Test Author 09", "Test Content 09");
		Resource<ArticleResponse> articleResponseResource09 = resource.addArticle(request);
		ArticleResponse addedArticle09 = articleResponseResource09.getContent();

		assertNotNull(addedArticle09);
		assertNotNull(addedArticle09.getArticleId());
		assertEquals(addedArticle09.getStatus(), Status.ACTIVE);
		assertEquals(addedArticle09.getTitle(), "TITLE_09");

		request = prepareArticleAddReuest("TITLE_10", "Test Author 10", "Test Content 10");
		Resource<ArticleResponse> articleResponseResource10 = resource.addArticle(request);
		ArticleResponse addedArticle10 = articleResponseResource10.getContent();

		assertNotNull(addedArticle10);
		assertNotNull(addedArticle10.getArticleId());
		assertEquals(addedArticle10.getStatus(), Status.ACTIVE);
		assertEquals(addedArticle10.getTitle(), "TITLE_10");

		VoteRequest voteRequest =new VoteRequest();
		voteRequest.setVote(Vote.UP);

		resource.vote(addedArticle09.getTitle(), voteRequest);
		resource.vote(addedArticle09.getTitle(), voteRequest);
		resource.vote(addedArticle09.getTitle(), voteRequest);

		resource.vote(addedArticle10.getTitle(), voteRequest);
		resource.vote(addedArticle10.getTitle(), voteRequest);
		resource.vote(addedArticle10.getTitle(), voteRequest);
		resource.vote(addedArticle10.getTitle(), voteRequest);
		resource.vote(addedArticle10.getTitle(), voteRequest);

		resource.vote(addedArticle08.getTitle(), voteRequest);
		resource.vote(addedArticle08.getTitle(), voteRequest);

		ArticleStatistics articleStatistics = resource.getArticleStatistics();

		assertTrue(articleStatistics.getTotalNoOfArticles()>=3);
		assertEquals(addedArticle10.getTitle(), articleStatistics.getMostPopularArticle().getTitle());
	}

	private ArticleAddRequest prepareArticleAddReuest(String title, String author, String content){
		request.setTitle(title);
		request.setAuthor(author);
		request.setContent(content);
		return request;
	}

}
