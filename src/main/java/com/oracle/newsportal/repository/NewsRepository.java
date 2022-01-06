package com.oracle.newsportal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.newsportal.models.NewsFeed;

@Repository
public interface NewsRepository extends JpaRepository<NewsFeed, Long>{
	
	List<NewsFeed> findTop5ByDateBetweenOrderByViewsDesc(Date from, Date to);

	List<NewsFeed> findByCategoryIdOrderByDateDesc(Long categoryId);

	List<NewsFeed> findAllByOrderByDateDesc();

	List<NewsFeed> findAllByCategoryId(Long id);

	List<NewsFeed> findAllByBroadcasterId(Long id);

}


