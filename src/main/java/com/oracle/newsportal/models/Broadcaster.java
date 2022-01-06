package com.oracle.newsportal.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Broadcaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long broadcasterId;

	@NotBlank(message = "Broadcaster Name can not be blank")
	@NotNull(message = "Broadcaster Name can not be blank")
	private String broadcasterName;
	
	private Integer broadcasterType; 

	public Broadcaster() {
		super();
	}

	public Broadcaster(String broadcasterName) {
		this.broadcasterName = broadcasterName;
	}

	public Long getBroadcasterId() {
		return broadcasterId;
	}

	public void setBroadcasterId(Long broadcasterId) {
		this.broadcasterId = broadcasterId;
	}

	public String getBroadcasterName() {
		return broadcasterName;
	}

	public void setBroadcasterName(String broadcasterName) {
		this.broadcasterName = broadcasterName;
	}

	public Integer getBroadcasterType() {
		return broadcasterType;
	}

	public void setBroadcasterType(Integer broadcasterType) {
		this.broadcasterType = broadcasterType;
	}

}
