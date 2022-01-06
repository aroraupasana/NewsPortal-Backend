package com.oracle.newsportal.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="newsfeed")
public class NewsFeed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long newsId;
	
	private Long broadcasterId;
	
	private Long categoryId;

	@NotBlank(message = "News Heading can not be blank")
	private String newsHeading;
	
	@NotBlank(message = "News Content can not be blank")
	private String newsContent;
	
	private Long views;	
	
	private Date date;
	
	public NewsFeed() {
		super();
	}

	public Long getNewsId() {
		return newsId;
	}
	
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public Long getBroadcasterId() {
		return broadcasterId;
	}

	public void setBroadcasterId(Long broadcasterId) {
		this.broadcasterId = broadcasterId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getNewsHeading() {
		return newsHeading;
	}
	
	public void setNewsHeading(String newsHeading) {
		this.newsHeading = newsHeading;
	}
	
	public String getNewsContent() {
		return newsContent;
	}
	
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	
	public Long getViews() {
		return views;
	}
	
	public void setViews(Long views) {
		this.views = views;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
