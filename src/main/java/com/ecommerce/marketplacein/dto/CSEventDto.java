package com.ecommerce.marketplacein.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CSEventDto {

	@JsonProperty("channel")
	private String channel;

	@JsonProperty("message")
	private String message;

	public CSEventDto() {
		// Empty
	}

	public CSEventDto(String channel, String message) {
		this.channel = channel;
		this.message = message;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
