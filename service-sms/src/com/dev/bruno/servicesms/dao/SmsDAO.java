package com.dev.bruno.servicesms.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import com.dev.bruno.servicesms.model.Sms;

@Stateless
public class SmsDAO {

	@PersistenceContext
	protected EntityManager manager;
	
	private Set<String> orderOptions;
	
	private Set<String> queryOptions;
	
	private Set<String> dirOptions;
	
	@PostConstruct
	private void init() {
		dirOptions = new HashSet<>();
		
		dirOptions.add("asc");
		dirOptions.add("desc");
		
        List<Field> fields = new ArrayList<Field>();
        
		fields.addAll(Arrays.asList(Sms.class.getDeclaredFields()));
		
		orderOptions = new HashSet<>();
		queryOptions = new HashSet<>();
		
		for(Field field : fields) {
			if(field.getType().equals(Integer.class) || field.getType().equals(Long.class) || field.getType().equals(Date.class)) {
				orderOptions.add(field.getName());
			} else if(field.getType().equals(String.class)) {
				orderOptions.add(field.getName());
				queryOptions.add(field.getName());
			}
		}
	}
	
	public Sms get(Long id) throws Exception {
		if(!exists(id)) {
			throw new Exception("Sms não encontrado");
		}
		
		Sms result = manager.find(Sms.class, id);
		
		return result;
	}

	public void add(Sms entity) throws Exception {
		if(entity == null) {
			throw new Exception("Sms não encontrado");
		}
		
		manager.persist(entity);
	}

	public List<Sms> list(String queryStr, Integer start, Integer limit, String order, String dir) throws Exception {
		if(start == null || limit == null || order == null || dir == null) {
			throw new Exception("start, limit, order e dir são obrigatórios");
		}
		
		if(!orderOptions.contains(order) || !dirOptions.contains(dir)) {
			throw new Exception(String.format("Possíveis valores para order[%s] e dir[%s]", StringUtils.join(orderOptions, ", "), StringUtils.join(dirOptions, ", ")));
		}
		
		StringBuilder hql = new StringBuilder("select s from Sms s where 1=1");
		
		
		if(queryStr != null && !queryStr.isEmpty()) {
			hql.append(" and (");
			
			boolean first = true;
			
			for(String queryOption : queryOptions) {
				if(!first) {
					hql.append(" or ");
				}
				
				hql.append("upper(s.").append(queryOption).append(") like upper(:").append(queryOption).append(")");
				
				first = false;
			}
			
			hql.append(")");
		}
		
		
		hql.append(" order by s." + order + " " + dir);
		
		TypedQuery<Sms> query = manager.createQuery(hql.toString(), Sms.class);
		
		if(queryStr != null && !queryStr.isEmpty()) {
			for(String queryOption : queryOptions) {
				query.setParameter(queryOption, "%" + queryStr + "%");
			}
		}
		
		return query.setFirstResult(start).setMaxResults(limit).getResultList();
	}
	
	public List<Sms> list() {
		return manager.createQuery("select s from Sms s order by s.id", Sms.class).getResultList();
	}

	public Long count(String queryStr) {
		
		StringBuilder hql = new StringBuilder("select count(s) from Sms s where 1=1");
		
		if(queryStr != null && !queryStr.isEmpty()) {
			hql.append(" and (");
			
			boolean first = true;
			
			for(String queryOption : queryOptions) {
				if(!first) {
					hql.append(" or ");
				}
				
				hql.append("upper(s.").append(queryOption).append(") like upper(:").append(queryOption).append(")");
				
				first = false;
			}
			
			hql.append(")");
		}
		
		TypedQuery<Long> query = manager.createQuery(hql.toString(), Long.class);
		
		if(queryStr != null && !queryStr.isEmpty()) {
			for(String queryOption : queryOptions) {
				query.setParameter(queryOption, "%" + queryStr + "%");
			}
		}
		
		return query.getSingleResult();
	}
	
	public Boolean exists(Long id) throws Exception {
		if(id == null) {
			throw new Exception("id é obrigatório");
		}
		
		Long result = manager.createQuery("select count(s) from Sms s where s.id = :id", Long.class).setParameter("id", id).getSingleResult();
		
		return result > 0;
	}
}