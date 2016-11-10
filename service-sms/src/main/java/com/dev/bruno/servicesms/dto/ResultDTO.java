package com.dev.bruno.servicesms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel
public class ResultDTO implements Serializable {

	private static final long serialVersionUID = -4216175342452534456L;

    @ApiModelProperty(required=true, value="Lista de SMS resultante de acordo com start, limit, order e dir definidos")
	private List<SmsDTO> result = new ArrayList<>();

    @ApiModelProperty(required=true, value="Número de SMS de acordo com start, limit, order e dir definidos", example="1")
	private Long resultSize = 0l;

    @ApiModelProperty(required=true, value="Número de SMS total resultante da consulta", example="1")	
	private Long totalSize = 0l;

    @ApiModelProperty(required=true, value="start definido inicialmente na consulta", example="0")	
	private Integer start;

    @ApiModelProperty(required=true, value="limit definido inicialmente na consulta", example="100")		
	private Integer limit;
	
    @ApiModelProperty(required=true, value="order definido inicialmente na consulta", example="id")	
	private String order;

    @ApiModelProperty(required=true, value="dir definido inicialmente na consulta", example="asc")		
	private String dir;
	
	public void remove(SmsDTO dto) {
		result.remove(dto);
		
		if(resultSize > 0) {
			resultSize--;
		}
		
		if(totalSize > 0) {
			totalSize--;
		}
	}
	
	public void add(SmsDTO dto) {
		result.add(dto);
		
		resultSize++;
		
		totalSize++;
	}

	public List<SmsDTO> getResult() {
		return result;
	}

	public void setResult(List<SmsDTO> result) {
		this.result = result;
	}

	public Long getResultSize() {
		return resultSize;
	}

	public void setResultSize(Long resultSize) {
		this.resultSize = resultSize;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}