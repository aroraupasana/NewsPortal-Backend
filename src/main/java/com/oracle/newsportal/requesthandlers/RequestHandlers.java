package com.oracle.newsportal.requesthandlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.newsportal.exceptions.BadRequestException;
import com.oracle.newsportal.models.Broadcaster;
import com.oracle.newsportal.models.Category;
import com.oracle.newsportal.models.CreateUser;
import com.oracle.newsportal.models.NewsFeed;
import com.oracle.newsportal.models.NewsFeedRequest;
import com.oracle.newsportal.models.NewsFeedView;
import com.oracle.newsportal.models.User;
import com.oracle.newsportal.services.NewsService;
import com.oracle.newsportal.services.UserService;

@RestController
@RequestMapping(path = "api/v1/")
@CrossOrigin
public class RequestHandlers {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private NewsService newsService;
	
	
	@PostMapping(path ="/saveuser")
	public ResponseEntity<?> addUser(@Validated @RequestBody CreateUser user){
		 return ResponseEntity.ok().body(userService.addUser(user));
	}
	
	@GetMapping(path ="/verifyuser")
	public ResponseEntity<Object> verifyUser(@RequestParam String userName, String userPassword){
		
		if(userName=="" || userPassword=="")
			throw new BadRequestException("Invalid Credentials");
		
		return ResponseEntity.ok().body(userService.verifyUser(userName,userPassword));
	}
	
	@GetMapping(path ="/getusername")
	public ResponseEntity<String> getUserName(@RequestBody User user){
		if(user.getUserId() == null)
			return ResponseEntity.badRequest().build();
		
		return ResponseEntity.ok().body(userService.getUserName(user));
	}
	
	
	@PostMapping(path = "/savecategory")
	public ResponseEntity<?> saveCategory(@Validated @RequestBody Category category)
	{
		  newsService.addCategory(category);
		  return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@PostMapping(path = "/savebroadcaster")
	public ResponseEntity<?> saveBroadcaster(@Validated @RequestBody Broadcaster broadcaster)
	{
		  newsService.addBroadcaster(broadcaster);
		  return ResponseEntity.ok().build();	
	}
	
	// ************* news request handlers **************
	
	@PostMapping(path = "/savenews")
	public ResponseEntity<?> saveNews(@Validated @RequestBody NewsFeedRequest newsFeed)
	{
		  newsService.addNews(newsFeed);
		  return ResponseEntity.ok().build();	
	}
	
	@PutMapping(path = "/updatenews")
	public ResponseEntity<?> updateNews(@RequestParam Long id, @Validated @RequestBody NewsFeedRequest newsFeed)
	{
		  newsService.updateNews(newsFeed,id);
		  return ResponseEntity.ok().build();	
	}
	
	@GetMapping(path ="/getallnews")
	public ResponseEntity<List<NewsFeedView>> getAllNews(){
		return ResponseEntity.ok().body(newsService.getAllNews());
	}
	
	@GetMapping(path ="/getallcategory")
	public List<Category> getAllCategory(){
		return newsService.getAllCategory();
	}
	
	@GetMapping(path ="/getallbroadcaster")
	public List<Broadcaster> getAllBroadcaster(){
		return newsService.getAllBroadcaster();
	}
	
	@GetMapping(path ="/gettrendingnews")
	public List<NewsFeedView> getTop5TrendendingNews(){
		return newsService.getTop5TrendendingNews();
	}
	
	@GetMapping(path ="/getnewsbycategory")
	public List<NewsFeedView> getNewsByCategory(@RequestParam Long categoryId){
		return newsService.getNewsByCategory(categoryId);
	}
	
	@GetMapping(path ="/getnewsbyid")
	public ResponseEntity<NewsFeedView> getNewsById(@RequestParam Long id){
		return ResponseEntity.ok().body(newsService.getNewsById(id));
		//return newsService.getNewsById(id);
	}
	

	@DeleteMapping(path ="/deletecategory")
	public ResponseEntity<?> deletCategoryById(@RequestParam Long id){
		newsService.deleteCategoryById(id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(path ="/deletebroadcaster")
	public ResponseEntity<?> deletBroadcasterById(@RequestParam Long id){
		newsService.deleteBroadcasterById(id);
		return ResponseEntity.ok().build();
	}
	
}
