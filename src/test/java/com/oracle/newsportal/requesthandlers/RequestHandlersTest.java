package com.oracle.newsportal.requesthandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.newsportal.models.Broadcaster;
import com.oracle.newsportal.models.Category;
import com.oracle.newsportal.models.CreateUser;
import com.oracle.newsportal.models.NewsFeedRequest;
import com.oracle.newsportal.models.NewsFeedView;
import com.oracle.newsportal.models.User;
import com.oracle.newsportal.services.NewsService;
import com.oracle.newsportal.services.UserService;


@WebMvcTest
public class RequestHandlersTest {

	@MockBean
    private UserService userService;
	
	@MockBean
	private NewsService newsService;
 
    @Autowired
    private MockMvc mockMvc;
 
    @Test
    @DisplayName("Test to add new user")
    public void testToAddNewUser() throws Exception {
 
        Mockito.when(userService.addUser(Mockito.any())).thenReturn(getUser());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .post("/api/v1/saveuser")
        	      .content(asJsonString(new CreateUser("john","email@gmail.com","pwd")))
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("Test to verify user")
    public void testToVerifyUser() throws Exception {
 
        Mockito.when(userService.verifyUser(Mockito.anyString(),Mockito.anyString())).thenReturn(getUser());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/verifyuser")
        	      .param("userName", "john")
                  .param("userPassword", "pwd")
                  .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test to get username")
    public void testToGetUserName() throws Exception {
 
        Mockito.when(userService.getUserName(Mockito.any())).thenReturn("john");
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/getusername")
        	      .content(asJsonString(getUser()))
                  .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test to add new category")
    public void testToAddNewCategory() throws Exception {
 
        Mockito.when(newsService.addCategory(Mockito.any())).thenReturn(new Category("mycategory"));
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .post("/api/v1/savecategory")
        	      .content(asJsonString(new Category("mycategory")))
                  .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("Test to add new broadcaster")
    public void testToAddNewBroadcaster() throws Exception {
 
        Mockito.when(newsService.addBroadcaster(Mockito.any())).thenReturn(new Broadcaster("myBroadcaster"));
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .post("/api/v1/savebroadcaster")
        	      .content(asJsonString(new Broadcaster("myBroadcaster")))
                  .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test to add new news")
    public void testToSaveNews() throws Exception {
 
        Mockito.when(newsService.addNews(Mockito.any())).thenReturn(getNewsFeedViewObj());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .post("/api/v1/savenews")
        	      .content(asJsonString(getNewsFeedRequestObj()))
                  .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test to update news")
    public void testToUpdateNews() throws Exception {
 
        Mockito.when(newsService.updateNews(Mockito.any(),Mockito.any())).thenReturn(getNewsFeedViewObj());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .put("/api/v1/updatenews")
        	      .content(asJsonString(getNewsFeedRequestObj()))
        	      .param("id", "1")
                  .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test to get all news")
    public void testToGetAllNews() throws Exception {
 
        Mockito.when(newsService.getAllNews()).thenReturn(getNewsFeedList());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/getallnews")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test to get all category")
    public void testToGetAllCategory() throws Exception {
 
        Mockito.when(newsService.getAllCategory()).thenReturn(getAllCategories());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/getallcategory")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("Test to get all Broadcasters")
    public void testToGetAllBroadcasters() throws Exception {
 
        Mockito.when(newsService.getAllBroadcaster()).thenReturn(getAllBroadcasters());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/getallbroadcaster")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("Test to get Trending News")
    public void testToGetTrendingNews() throws Exception {
 
        Mockito.when(newsService.getTop5TrendendingNews()).thenReturn(getNewsFeedList());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/gettrendingnews")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("Test to get News by category")
    public void testToGetNewsByCategory() throws Exception {
 
        Mockito.when(newsService.getNewsByCategory(Mockito.anyLong())).thenReturn(getNewsFeedList());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/getnewsbycategory")
        	      .param("categoryId", "1")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test to get News by id")
    public void testToGetNewsById() throws Exception {
 
        Mockito.when(newsService.getNewsById(Mockito.any())).thenReturn(getNewsFeedViewObj());
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .get("/api/v1/getnewsbyid")
        	      .param("id", "1")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }
    
    

    @Test
    @DisplayName("Test to delete category by id")
    public void testToDeleteCategoryById() throws Exception {
 
        Mockito.when(newsService.deleteCategoryById(Mockito.anyLong())).thenReturn(true);
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .delete("/api/v1/deletecategory")
        	      .param("id", "1")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Test to delete broadcaster by id")
    public void testToDeleteBroadcasterById() throws Exception {
 
        Mockito.when(newsService.deleteBroadcasterById(Mockito.anyLong())).thenReturn(true);
 
        this.mockMvc.perform( MockMvcRequestBuilders
        	      .delete("/api/v1/deletebroadcaster")
        	      .param("id", "1")
        	      .contentType(MediaType.APPLICATION_JSON)
        	      .accept(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk());
    }

    
    
	// mock data 
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
		Category category2 = new Category("category2");
		
		
		categories.add(category1);
		categories.add(category2);
		
		return categories;
	}


	private List<NewsFeedView> getNewsFeedList() {
		List<NewsFeedView> news = new ArrayList<NewsFeedView>();
		
		news.add(getNewsFeedViewObj());
		
		return news;
	}


	private NewsFeedView getNewsFeedViewObj() {
		
    	NewsFeedView newsFeed = new NewsFeedView();
		
		newsFeed.setNewsId(1L);
		newsFeed.setBroadcasterName("news18");
		newsFeed.setCategoryName("abc");
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


    private User getUser() {
		User user = new User(1,"john","abc@gmail.com","pwd");
		user.setUserId(1L);
		
		return user;
	}
    

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
