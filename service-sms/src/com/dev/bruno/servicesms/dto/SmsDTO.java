package com.dev.bruno.servicesms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class SmsDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;

    @ApiModelProperty(value="ID do SMS", example="1")
	private Long id;

    @ApiModelProperty(value="Remetente do SMS, no seguinte formato: ^\\+\\d{13}$", example="+5521999112222")
	private String from;

    @ApiModelProperty(value="Destinatário do SMS, no seguinte formato: ^\\+\\d{13}$", example="+5521999112223")
	private String to;

    @ApiModelProperty(value="Corpo do SMS, até 160 caracteres", example="teste")
	private String body;

    @ApiModelProperty(value="Data limite de envio do SMS", example="2016-01-01T00:00:00")
	private Date validDate;

    @ApiModelProperty(value="Data de envio do SMS", example="2016-01-01T00:00:00")
	private Date sentDate;

    @ApiModelProperty(value="Data de invalidação do SMS, caso não tenha sido enviado antes da validDate", example="2016-01-01T00:00:00")
	private Date invalidationDate;

    @ApiModelProperty(value="Data de falha de envio do SMS", example="2016-01-01T00:00:00")	
	private Date failureDate;
	
    @ApiModelProperty(value="Mensagem de falha de envio do SMS", example="teste")
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