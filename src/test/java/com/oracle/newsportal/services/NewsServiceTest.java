package com.oracle.newsportal.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.oracle.newsportal.exceptions.BadRequestException;
import com.oracle.newsportal.exceptions.ResourceNotFoundException;
import com.oracle.newsportal.models.Broadcaster;
import com.oracle.newsportal.models.Category;
import com.oracle.newsportal.models.FieldType;
import com.oracle.newsportal.models.NewsFeed;
import com.oracle.newsportal.models.NewsFeedRequest;
import com.oracle.newsportal.models.NewsFeedView;
import com.oracle.newsportal.repository.BroadcasterRepository;
import com.oracle.newsportal.repository.CategoryRepository;
import com.oracle.newsportal.repository.NewsRepository;

@SpringBootTest
public class NewsServiceTest {
	
	@InjectMocks
	private NewsService newsService;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private BroadcasterRepository broadcasterRepository;
	
	@Mock
	private NewsRepository newsRepository;
	
	
	@Test
	@DisplayName("test add new category")
	void testAddCategory() {
		when(categoryRepository.findByCategoryName(Mockito.anyString())).thenReturn(Optional.empty());
		when(categoryRepository.save(Mockito.any())).thenReturn(getCategoryObj());
		
		Category category = getCategoryObj();
		Category newCategory = newsService.addCategory(category);
		
		assertEquals(newCategory.getCategoryName(), category.getCategoryName());
		
	}
	
	@Test
	@DisplayName("test add new category: BadRequest(category already exist)")
	void testAddCategoryBadRequest() {
		when(categoryRepository.findByCategoryName(Mockito.anyString())).thenReturn(Optional.of(getCategoryObj()));
		when(categoryRepository.save(Mockito.any())).thenReturn(getCategoryObj());
		
		Category category = getCategoryObj();
		
		assertThrows(BadRequestException.class,() -> newsService.addCategory(category));
		
	}
	

	@Test
	@DisplayName("test add new broadcaster")
	void testAddBroadcaster() {
		when(broadcasterRepository.findByBroadcasterName(Mockito.anyString())).thenReturn(Optional.empty());
		when(broadcasterRepository.save(Mockito.any())).thenReturn(getBroadcasterObj());
		
		Broadcaster broadcaster = getBroadcasterObj();
		Broadcaster newBroadcaster = newsService.addBroadcaster(broadcaster);
		
		assertEquals(newBroadcaster.getBroadcasterName(), broadcaster.getBroadcasterName());
		
	}
	
	@Test
	@DisplayName("test add new category: BadRequest(category already exist)")
	void testAddBroadcasterBadRequest() {
		when(broadcasterRepository.findByBroadcasterName(Mockito.anyString())).thenReturn(Optional.of(getBroadcasterObj()));
		when(broadcasterRepository.save(Mockito.any())).thenReturn(getBroadcasterObj());
		
		Broadcaster broadcaster = getBroadcasterObj();
		
		assertThrows(BadRequestException.class,() -> newsService.addBroadcaster(broadcaster));
		
	}
	
	@Test
	@DisplayName("test add new news")
	void testAddNews() {
		when(broadcasterRepository.findById(Mockito.any())).thenReturn(Optional.of(getBroadcasterObj()));
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(getCategoryObj()));
		when(newsRepository.save(Mockito.any())).thenReturn(getNewsFeedObj());
		
		NewsFeedRequest newsFeedRequest = getNewsFeedRequestObj();
		
		NewsFeedView newNews = newsService.addNews(newsFeedRequest);
		
		assertEquals(newNews.getNewsHeading(), newsFeedRequest.getNewsHeading());
		assertEquals(newNews.getNewsContent(), newsFeedRequest.getNewsContent());
	}
	
	
	@Test
	@DisplayName("test add new news: BadRequest(invalid category)")
	void testAddNewsInvalidCategory() {
		when(broadcasterRepository.findById(Mockito.any())).thenReturn(Optional.of(getBroadcasterObj()));
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		when(newsRepository.save(Mockito.any())).thenReturn(getNewsFeedObj());
		
		NewsFeedRequest newsFeedRequest = getNewsFeedRequestObj();
		
		assertThrows(BadRequestException.class,() -> newsService.addNews(newsFeedRequest));
	}

	
	@Test
	@DisplayName("test add new news: BadRequest(invalid broadcaster)")
	void testAddNewsInvalidBroadcaster() {
		when(broadcasterRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(getCategoryObj()));
		when(newsRepository.save(Mockito.any())).thenReturn(getNewsFeedObj());
		
		NewsFeedRequest newsFeedRequest = getNewsFeedRequestObj();
		
		assertThrows(BadRequestException.class,() -> newsService.addNews(newsFeedRequest));
	}
	
	@Test
	@DisplayName("test update news")
	void testUpdateNews() {
		when(broadcasterRepository.findById(Mockito.any())).thenReturn(Optional.of(getBroadcasterObj()));
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(getCategoryObj()));
		when(newsRepository.save(Mockito.any())).thenReturn(getNewsFeedObj());
		
		NewsFeedRequest newsFeedRequest = getNewsFeedRequestObj();
		
		NewsFeedView newNews = newsService.updateNews(newsFeedRequest,1L);
		
		assertEquals(newNews.getNewsHeading(), newsFeedRequest.getNewsHeading());
		assertEquals(newNews.getNewsContent(), newsFeedRequest.getNewsContent());
	}
	
	
	@Test
	@DisplayName("test update news: BadRequest(invalid category)")
	void testUpdateNewsInvalidCategory() {
		when(broadcasterRepository.findById(Mockito.any())).thenReturn(Optional.of(getBroadcasterObj()));
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		when(newsRepository.save(Mockito.any())).thenReturn(getNewsFeedObj());
		
		NewsFeedRequest newsFeedRequest = getNewsFeedRequestObj();
		
		assertThrows(BadRequestException.class,() -> newsService.updateNews(newsFeedRequest,1L));
	}

	
	@Test
	@DisplayName("test update news: BadRequest(invalid broadcaster)")
	void testUpdateNewsInvalidBroadcaster() {
		when(broadcasterRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(getCategoryObj()));
		when(newsRepository.save(Mockito.any())).thenReturn(getNewsFeedObj());
		
		NewsFeedRequest newsFeedRequest = getNewsFeedRequestObj();
		
		assertThrows(BadRequestException.class,() -> newsService.updateNews(newsFeedRequest,1L));
	}

	
	@Test
	@DisplayName("test to get all categories")
	void testToGetAllCategories() {
		when(categoryRepository.findAllByOrderByCategoryIdDesc()).thenReturn(getAllCategories());
				
		List<Category> newCategories = newsService.getAllCategory();
		
		assertEquals(newCategories.size(), 2);
	}
	
	
	@Test
	@DisplayName("test to get all broadcasters")
	void testToGetAllBroadcasters() {
		when(broadcasterRepository.findAllByOrderByBroadcasterIdDesc()).thenReturn(getAllBroadcasters());
				
		List<Broadcaster> newBroadcasters = newsService.getAllBroadcaster();
		
		assertEquals(newBroadcasters.size(), 2);
	}
	
	@Test
	@DisplayName("test to get all news")
	void testToGetAllNews() {
		when(newsRepository.findAllByOrderByDateDesc()).thenReturn(getNewsFeedList());
		when(categoryRepository.findAllById(Mockito.anyList())).thenReturn(getAllCategories());
		when(broadcasterRepository.findAllById(Mockito.anyList())).thenReturn(getAllBroadcasters());
				
		List<NewsFeedView> news = newsService.getAllNews();
		
		assertEquals(news.size(), 1);
	}
	
	
	@Test
	@DisplayName("test to get trending news")
	void testToGetTrendingNews() {
		when(newsRepository.findTop5ByDateBetweenOrderByViewsDesc(Mockito.any(),Mockito.any())).thenReturn(getNewsFeedList());
		when(categoryRepository.findAllById(Mockito.anyList())).thenReturn(getAllCategories());
		when(broadcasterRepository.findAllById(Mockito.anyList())).thenReturn(getAllBroadcasters());
				
		List<NewsFeedView> news = newsService.getTop5TrendendingNews();
		
		assertEquals(news.size(), 1);
	}
	
	@Test
	@DisplayName("test to get news by category")
	void testToGetNewsByCategory() {
		when(newsRepository.findByCategoryIdOrderByDateDesc(Mockito.anyLong())).thenReturn(getNewsFeedList());
		when(categoryRepository.findAllById(Mockito.anyList())).thenReturn(getAllCategories());
		when(broadcasterRepository.findAllById(Mockito.anyList())).thenReturn(getAllBroadcasters());
				
		List<NewsFeedView> news = newsService.getNewsByCategory(1L);
		
		assertEquals(news.size(), 1);
	}
	
	@Test
	@DisplayName("test to get news by id")
	void testToGetNewsById() {
		when(newsRepository.findById(Mockito.any())).thenReturn(Optional.of(getNewsFeedObj()));
		when(newsRepository.save(Mockito.any())).thenReturn(getNewsFeedObj());
		when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getCategoryObj()));
		when(broadcasterRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getBroadcasterObj()));
		
		NewsFeed newsFeed = getNewsFeedObj();
				
		NewsFeedView news = newsService.getNewsById(1L);
		
		assertEquals(news.getNewsId(), newsFeed.getNewsId());
	}
	
	@Test
	@DisplayName("test to get news by id: ResourceNotFoundException(news with given id not found)")
	void testToGetNewsByIdResourceNotFound() {
		when(newsRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class,() -> newsService.getNewsById(1L));
	}
/*
	@Test
	@DisplayName("test to delete category by id")
	void testToDeleteCategoryById() {
		when(newsRepository.findAllByCategoryId(Mockito.any())).thenReturn(getNewsFeedList());
		when(newsRepository.saveAll(Mockito.anyList())).thenReturn(getNewsFeedList());
		
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(getCategoryObj()));
		when(categoryRepository.getCategoryIdByCategoryType(Mockito.anyInt())).thenReturn(3L);
		Mockito.doNothing().when(categoryRepository).deleteById(Mockito.anyLong());
		
		Boolean res = newsService.deleteCategoryById(1L);
		
		assertTrue(res);
	}*/
	
	@Test
	@DisplayName("test to delete category by id: ResourceNotFoundException(category with given id not found)")
	void testToDeleteCategoryByIdResourceException() {
		when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class,() -> newsService.deleteCategoryById(1L));
	}
	
	@Test
	@DisplayName("test to delete category by id: BadException(invalid request)")
	void testToDeleteCategoryByIdBadException() {
		Category category = new Category();
		category.setCategoryId(1L);
		category.setCategoryType(FieldType.DEFAULT);
		
		when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
		
		assertThrows(BadRequestException.class,() -> newsService.deleteCategoryById(1L));
	}
	/*
	@Test
	@DisplayName("test to delete broadcaster by id")
	void testToDeleteBroadcasterById() {
		when(newsRepository.findAllByCategoryId(Mockito.any())).thenReturn(getNewsFeedList());
		when(newsRepository.saveAll(Mockito.anyList())).thenReturn(getNewsFeedList());
		
		when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(getCategoryObj()));
		when(categoryRepository.getCategoryIdByCategoryType(Mockito.anyInt())).thenReturn(3L);
		Mockito.doNothing().when(categoryRepository).deleteById(Mockito.anyLong());
		
		Boolean res = newsService.deleteBroadcasterById(1L);
		
		assertTrue(res);
	}*/

	
	@Test
	@DisplayName("test to delete broadcaster by id: ResourceNotFoundException(broadcaster with given id not found)")
	void testToDeleteBroadcasterByIdResourceException() {
		when(broadcasterRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class,() -> newsService.deleteBroadcasterById(1L));
	}
	
	@Test
	@DisplayName("test to delete broadcaster by id: BadException(invalid request)")
	void testToDeleteBroadcasterByIdBadException() {
		Broadcaster broadcaster = new Broadcaster();
		broadcaster.setBroadcasterId(1L);
		broadcaster.setBroadcasterType(FieldType.DEFAULT);
		
		when(broadcasterRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(broadcaster));
		
		assertThrows(BadRequestException.class,() -> newsService.deleteBroadcasterById(1L));
	}
	
	// mock data
	private List<NewsFeed> getNewsFeedList() {
		List<NewsFeed> news = new ArrayList<NewsFeed>();
		
		news.add(getNewsFeedObj());
		
		return news;
	}
	
	private List<Broadcaster> getAllBroadcasters() {
    	List<Broadcaster> broadcasters = new ArrayList<Broadcaster>();
		
    	Broadcaster broadcaster1 = new Broadcaster("broadcaster1");
    	Broadcaster broadcaster2 = new Broadcaster("broadcaster2");
		
		
    	broadcasters.add(broadcaster1);
    	broadcasters.add(broadcaster2);
		
		return broadcasters;
	}
	
	
	private List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		
		Category category1 = new Category("category1");
		category1.setCategoryId(1L);
		
		Category category2 = new Category("category2");
		category2.setCategoryId(2L);
		
		categories.add(category1);
		categories.add(category2);
		
		return categories;
	}
	
	private NewsFeed getNewsFeedObj() {
		
    	NewsFeed newsFeed = new NewsFeed();
		
		newsFeed.setNewsId(1L);
		newsFeed.setBroadcasterId(1L);
		newsFeed.setCategoryId(1L);
		newsFeed.setNewsHeading("i am heading");
		newsFeed.setNewsContent("its content");
		newsFeed.setViews(1L);
		newsFeed.setDate(new Date());
		
		return newsFeed;
	}


	private NewsFeedRequest getNewsFeedRequestObj() {

		NewsFeedRequest newsFeed = new NewsFeedRequest();
		
		newsFeed.setBroadcasterId(1L);
		newsFeed.setCategoryId(1L);
		newsFeed.setNewsHeading("i am heading");
		newsFeed.setNewsContent("its content");
		
		return newsFeed;
	}

	private Broadcaster getBroadcasterObj() {
		Broadcaster broadcaster = new Broadcaster("broadcaster1");
		
		return broadcaster;
	}
	
	private Category getCategoryObj() {
		Category category = new Category("category1");
		
		return category;
	}

}
