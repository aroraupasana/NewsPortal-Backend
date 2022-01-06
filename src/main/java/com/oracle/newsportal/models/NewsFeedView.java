package com.oracle.newsportal.models;

import java.util.Date;

import javax.validation.constraints.NotBlank;

public class NewsFeedView {
	
	private Long newsId;
	
	private String broadcasterName;
	
	private String categoryName;

	private String newsHeading;
	
	private String newsContent;
	
	private Long views;	
	
	private Date date;

	public NewsFeedView() {
		super();
	}

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public String getBroadcasterName() {
		return broadcasterName;
	}

	public void setBroadcasterName(String broadcasterName) {
		this.broadcasterName = broadcasterName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
