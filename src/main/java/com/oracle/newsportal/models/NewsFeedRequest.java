package com.oracle.newsportal.models;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewsFeedRequest {
	
	@NotNull(message = "Broadcaster Id is required")
	private Long broadcasterId;
	
	@NotNull(message = "Category Id is required")
	private Long categoryId;

	@NotNull(message = "News Heading cannot be blank")
	@NotBlank(message = "News Heading cannot be blank")
	private String newsHeading;
	
	@Lob
	@Column(name="newscontent",columnDefinition="LONGTEXT")
	@NotBlank(message = "News Content cannot be blank")
	@NotNull(message = "News Content cannot be blank")
	private String newsContent;

	public NewsFeedRequest() {
		super();
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

}
