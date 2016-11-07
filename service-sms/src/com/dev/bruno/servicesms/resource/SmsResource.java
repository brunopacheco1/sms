package com.dev.bruno.servicesms.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.dev.bruno.servicesms.dto.ResultDTO;
import com.dev.bruno.servicesms.dto.SmsDTO;
import com.dev.bruno.servicesms.response.GenericResponse;
import com.dev.bruno.servicesms.service.SmsService;

@Produces(MediaType.APPLICATION_JSON)
public class SmsResource {

	private SmsService service;
	
	@GET
	public ResultDTO list(@QueryParam("query") String queryStr, @QueryParam("start") Integer start, @QueryParam("limit") Integer limit, @QueryParam("order") String order, @QueryParam("dir") String dir) throws Exception {
		return service.list(queryStr, start, limit, order, dir);
	}
	
	@GET
	@Path("/{id}") 
	public SmsDTO get(@PathParam("id") Long id) throws Exception {
		return service.get(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericResponse add(SmsDTO dto) throws Exception {
		service.add(dto);
		
		return new GenericResponse(true);
	}
}