package com.oracle.newsportal.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class NewsService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BroadcasterRepository broadcasterRepository;
	
	@Autowired
	private NewsRepository newsRepository;

	
	public Category addCategory(Category category) {
		
		// verify if already same category exist
		Optional<Category> optionalCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		
		if(optionalCategory.isPresent()) {
			throw new BadRequestException("Category already exist");
		}
		
		category.setCategoryType(FieldType.CUSTOM);
		return categoryRepository.save(category);
	}
	

	public Broadcaster addBroadcaster(Broadcaster broadcaster) {	
		
		// verify if already same broadcaster exist
		Optional<Broadcaster> optionalBroadCast = broadcasterRepository.findByBroadcasterName(broadcaster.getBroadcasterName());
		
		if(optionalBroadCast.isPresent()) {
			throw new BadRequestException("Broadcaster already exist");
		}
		
		broadcaster.setBroadcasterType(FieldType.CUSTOM);
		return broadcasterRepository.save(broadcaster);
	}
	
	

	public NewsFeedView addNews(NewsFeedRequest newsFeedRequest) {
		
		// validation for categories and broadcasters
		Optional<Category> optionalCategory = categoryRepository.findById(newsFeedRequest.getCategoryId());
		
		if(!optionalCategory.isPresent()) {
			throw new BadRequestException("Invalid Category");
		}
		
		Optional<Broadcaster> optionalBroadcast = broadcasterRepository.findById(newsFeedRequest.getBroadcasterId());
		
		if(!optionalBroadcast.isPresent()) {
			throw new BadRequestException("Invalid Broadcaster");
		}
		
		NewsFeed newsFeed = new NewsFeed();
		newsFeed.setBroadcasterId(newsFeedRequest.getBroadcasterId());
		newsFeed.setCategoryId(newsFeedRequest.getCategoryId());
		newsFeed.setNewsHeading(newsFeedRequest.getNewsHeading());
		newsFeed.setNewsContent(newsFeedRequest.getNewsContent());
		newsFeed.setDate(new Date());
		newsFeed.setViews(0L);
		
		NewsFeed news = newsRepository.save(newsFeed);
		
		return getNewsFeedViewObj(news);
	}
	
	
	
	public NewsFeedView updateNews(NewsFeedRequest newsFeedRequest, Long id) {
		
		// validation for categories and broadcasters
		Optional<Category> optionalCategory = categoryRepository.findById(newsFeedRequest.getCategoryId());
		
		if(!optionalCategory.isPresent()) {
			throw new BadRequestException("Invalid Category");
		}
		
		Optional<Broadcaster> optionalBroadcast = broadcasterRepository.findById(newsFeedRequest.getBroadcasterId());
		
		if(!optionalBroadcast.isPresent()) {
			throw new BadRequestException("Invalid Broadcaster");
		}
		
		NewsFeed newsFeed = new NewsFeed();
		newsFeed.setNewsId(id);
		newsFeed.setBroadcasterId(newsFeedRequest.getBroadcasterId());
		newsFeed.setCategoryId(newsFeedRequest.getCategoryId());
		newsFeed.setNewsHeading(newsFeedRequest.getNewsHeading());
		newsFeed.setNewsContent(newsFeedRequest.getNewsContent());
		newsFeed.setDate(new Date());
		newsFeed.setViews(0L);
		
		NewsFeed news = newsRepository.save(newsFeed);
		
		return getNewsFeedViewObj(news);
	}

	
	public List<Category> getAllCategory() {
		return categoryRepository.findAllByOrderByCategoryIdDesc();
	}

	
	public List<Broadcaster> getAllBroadcaster() {
		return broadcasterRepository.findAllByOrderByBroadcasterIdDesc();
	}

	
	public List<NewsFeedView> getAllNews() {
		List<NewsFeed> newsFeed = newsRepository.findAllByOrderByDateDesc();
		
		return getNewsFeedViewList(newsFeed);
	}
	
	
	/** get top 5 trending news of last 24 hours order based on views */
	public List<NewsFeedView> getTop5TrendendingNews() {
		Date currentDate = new Date();
		
		// convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        // manipulate date
        c.add(Calendar.DATE, -1);
        
        // convert calendar to date
        Date pastDate = c.getTime();
		List<NewsFeed> news = newsRepository.findTop5ByDateBetweenOrderByViewsDesc(pastDate, currentDate);
		
		return getNewsFeedViewList(news);
	}
	
	
	public List<NewsFeedView> getNewsByCategory(Long categoryId){
		List<NewsFeed> news = newsRepository.findByCategoryIdOrderByDateDesc(categoryId);
		
		return getNewsFeedViewList(news);
	}
	
	
	public NewsFeedView getNewsById(Long id) {
		Optional<NewsFeed> optionalNews = newsRepository.findById(id);
		
		if(optionalNews.isPresent()) {
			
			// update views then return value
			NewsFeed newsFeed = optionalNews.get();
			newsFeed.setViews(newsFeed.getViews() + 1);
			
			NewsFeed news = newsRepository.save(newsFeed);
			
			return getNewsFeedViewObj(news);
		}
		else
			throw new ResourceNotFoundException("News with given id not found");
	}

	
	public Boolean deleteCategoryById(Long id){
	
		Optional<Category> optionalCategory = categoryRepository.findById(id);
	
		if(!optionalCategory.isPresent())
			throw new ResourceNotFoundException("Category with given id not found");
	
		else{
			Category category = optionalCategory.get();
			
			if(category.getCategoryType() == FieldType.CUSTOM){
				
				List<NewsFeed> newsFeed = updateCategoryIdInNews(id);
				
				newsRepository.saveAll(newsFeed);
				categoryRepository.deleteById(id);
				
				return true;
			}
			else
				throw new BadRequestException("Invalid request");
		}
	}


	private List<NewsFeed> updateCategoryIdInNews(Long id) {
		
		Long defaultCategoryId = categoryRepository.findByCategoryType(FieldType.DEFAULT).getCategoryId();
		
		List<NewsFeed> newsFeed = newsRepository.findAllByCategoryId(id);
		
		newsFeed.forEach(news -> news.setCategoryId(defaultCategoryId));

		return newsFeed;
	}
	

	public Boolean deleteBroadcasterById(Long id){
	
		Optional<Broadcaster> optionalBroadcaster = broadcasterRepository.findById(id);
	
		if(!optionalBroadcaster.isPresent())
			throw new ResourceNotFoundException("Broadcaster with given id not found");
	
		else{
			Broadcaster broadcaster = optionalBroadcaster.get();
			
			if(broadcaster.getBroadcasterType() == FieldType.CUSTOM){
				
				List<NewsFeed> newsFeed = updateBroadcasterIdInNews(id);
				
				newsRepository.saveAll(newsFeed);
				broadcasterRepository.deleteById(id);
				
				return true;
			}
			else
				throw new BadRequestException("Invalid request");
		}
	}
	
	
	private List<NewsFeed> updateBroadcasterIdInNews(Long id) {
		
		Long defaultBroadcasterId = broadcasterRepository.findByBroadcasterType(FieldType.DEFAULT).getBroadcasterId();
		
		List<NewsFeed> newsFeed = newsRepository.findAllByBroadcasterId(id);
		
		newsFeed.forEach(news -> news.setBroadcasterId(defaultBroadcasterId));

		return newsFeed;
	}



	private List<NewsFeedView> getNewsFeedViewList(List<NewsFeed> newsFeed) {
	
		List<Long> categoryIds = new ArrayList<Long>();
		List<Long> broadcasterIds = new ArrayList<Long>();
		
		// collect category and broadcaster ids used in all news
		newsFeed.forEach(news -> {
			categoryIds.add(news.getCategoryId());
			broadcasterIds.add(news.getBroadcasterId());
		});
		
		List<Category> categories = categoryRepository.findAllById(categoryIds);
		List<Broadcaster> broadcasters = broadcasterRepository.findAllById(broadcasterIds);
		
		Map<Long,String> categoryMap = new HashMap<Long,String>();
		Map<Long,String> broadcasterMap = new HashMap<Long,String>();
		
		// prepare hashmap with respect to categoryId and cateoryName
		categories.forEach(category -> categoryMap.put(category.getCategoryId(), category.getCategoryName()));
		broadcasters.forEach(broadcaster -> broadcasterMap.put(broadcaster.getBroadcasterId(), broadcaster.getBroadcasterName()));
		
		
		// prepare newsFeedView list
		List<NewsFeedView> newsFeedView = new ArrayList<NewsFeedView>();
		
		newsFeed.forEach(news -> {
			NewsFeedView newsView = new NewsFeedView();
			
			newsView.setNewsId(news.getNewsId());
			newsView.setBroadcasterName(getBroadcasterName(broadcasterMap, news.getBroadcasterId()));
			newsView.setCategoryName(getCategoryName(categoryMap, news.getCategoryId()));
			newsView.setNewsHeading(news.getNewsHeading());
			newsView.setNewsContent(news.getNewsContent());
			newsView.setViews(news.getViews());
			newsView.setDate(news.getDate());
			
			newsFeedView.add(newsView);
		});
		
		return newsFeedView;
	}


	private NewsFeedView getNewsFeedViewObj(NewsFeed news) {
		
		Category category = categoryRepository.findById(news.getCategoryId()).get();
		Broadcaster broadcaster = broadcasterRepository.findById(news.getBroadcasterId()).get();
		
		NewsFeedView newsView = new NewsFeedView();
		
		newsView.setNewsId(news.getNewsId());
		newsView.setBroadcasterName(broadcaster.getBroadcasterName());
		newsView.setCategoryName(category.getCategoryName());
		newsView.setNewsHeading(news.getNewsHeading());
		newsView.setNewsContent(news.getNewsContent());
		newsView.setViews(news.getViews());
		newsView.setDate(news.getDate());
		
		return newsView;
	}


	private String getCategoryName(Map<Long, String> categoryMap, Long id) {
		return categoryMap.containsKey(id) ? categoryMap.get(id) : "-";
	}


	private String getBroadcasterName(Map<Long, String> broadcasterMap, Long id) {
		return broadcasterMap.containsKey(id) ? broadcasterMap.get(id) : "-";
	}


}
