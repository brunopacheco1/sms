package com.dev.bruno.servicesms.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;

import com.dev.bruno.servicesms.dao.SmsDAO;
import com.dev.bruno.servicesms.dto.ResultDTO;
import com.dev.bruno.servicesms.dto.SmsDTO;
import com.dev.bruno.servicesms.model.Sms;

@Stateless
public class SmsService {

	@Inject
	private SmsDAO dao;
	
	public ResultDTO list(String queryStr, Integer start, Integer limit, String order, String dir) throws Exception {
		if(start == null) {
			start = 0;
		}
		
		if(limit == null) {
			limit = 100;
		}
		
		if(order == null) {
			order = "id";
		}
		
		if(dir == null) {
			dir = "asc";
		}
		
		List<SmsDTO> dtos = new ArrayList<>();
		
		for(Sms sms : dao.list(queryStr, start, limit, order, dir)) {
			dtos.add(entityToDTO(sms));
		}
		
		ResultDTO result = new ResultDTO();
		
		result.setResultSize((long) dtos.size());
		result.setTotalSize(dao.count(queryStr));
		result.setResult(dtos);
		result.setDir(dir);
		result.setLimit(limit);
		result.setOrder(order);
		result.setStart(start);
		
		return result;
	}
	
	
	public SmsDTO get(Long id) throws Exception {
		return entityToDTO(dao.get(id));
	}
	
	public SmsDTO add(SmsDTO dto) throws Exception {
		validate(null, dto);
		
		Sms entity = dtoToEntity(null, null, dto);
		
		dao.add(entity);
		
		return entityToDTO(entity);
	}
	
	private void validate(Long id, SmsDTO dto) throws Exception {
		if(id != null && !dao.exists(id)) {
			throw new Exception("Sms[" + id + "] não encontrado.");
		}
		
		dto.setId(id);
	}
	
	private SmsDTO entityToDTO(Sms sms) throws Exception {
		SmsDTO dto = new SmsDTO();
		
		if(sms == null) {
			return dto;
		}
		
		PropertyUtils.copyProperties(dto, sms);
		
		return dto;
	}
	
	private Sms dtoToEntity(Long id, Sms sms, SmsDTO dto) throws Exception {
		if(sms == null) {
			sms = new Sms();
		}
		
		if(dto == null) {
			return sms;
		}
		
		PropertyUtils.copyProperties(sms, dto);
		
		sms.setId(id);
		
		return sms;
	}
}