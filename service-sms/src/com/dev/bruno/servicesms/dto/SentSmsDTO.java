package com.dev.bruno.servicesms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class SentSmsDTO implements Serializable {

	private static final long serialVersionUID = 6435974476117737767L;

    @ApiModelProperty(value="Remetente do SMS, no seguinte formato: ^\\+\\d{13}$", example="+5521999112222")
	private String from;

    @ApiModelProperty(value="Destinatário do SMS, no seguinte formato: ^\\+\\d{13}$", example="+5521999112223")
	private String to;

    @ApiModelProperty(value="Corpo do SMS", example="teste")
	private String body;

    @ApiModelProperty(value="Data limite de envio do SMS", example="2016-01-01T00:00:00")
	private Date validDate;

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
}