package com.dev.bruno.servicesms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SMS")
public class Sms implements Serializable {

	private static final long serialVersionUID = 6001113379795063656L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="_ID", nullable=false)
	private Long id;
	
	@Column(name="_FROM", nullable=false, length=20)
	private String from;
	
	@Column(name="_TO", nullable=false, length=20)
	private String to;
	
	@Column(name="_BODY", nullable=false, length=160)
	private String body;
	
	@Column(name="_VALID_DATE")
	private Date validDate;
	
	@Column(name="_SENT_DATE")
	private Date sentDate;
	
	@Column(name="_INVALIDATION_DATE")
	private Date invalidationDate;

	@Column(name="_FAILURE_DATE")
	private Date failureDate;
	
	@Column(name="_FAILURE_MSG", length=1000)
	private String failureMsg;
	
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
	
	public Date getFailureDate() {
	    return failureDate;
	}
	
	public void setFailureDate(Date failureDate) {
        this.failureDate = failureDate;
    }
    
    public String getFailureMsg() {
        return failureMsg;
    }
    
    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }
}