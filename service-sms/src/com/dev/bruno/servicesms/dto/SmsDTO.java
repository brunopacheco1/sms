package com.dev.bruno.servicesms.dto;

import java.io.Serializable;

public class SmsDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;

	private Long id;
	
	private String from;
	
	private String to;
	
	private String body;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}