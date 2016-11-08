package com.dev.bruno.servicesms.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class SmsDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;

	private Long id;
	
	private String from;
	
	private String to;
	
	private String body;
	
	private Date validDate;
	
	private Date sentDate;
	
	private Date invalidationDate;

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

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getInvalidationDate() {
		return invalidationDate;
	}

	public void setInvalidationDate(Date invalidationDate) {
		this.invalidationDate = invalidationDate;
	}
}