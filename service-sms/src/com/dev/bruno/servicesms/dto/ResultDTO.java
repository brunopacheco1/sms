package com.dev.bruno.servicesms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultDTO implements Serializable {

	private static final long serialVersionUID = -4216175342452534456L;

	private List<SmsDTO> result = new ArrayList<>();

	private Long resultSize = 0l;
	
	private Long totalSize = 0l;
	
	private Integer start;
	
	private Integer limit;
	
	private String order;
	
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