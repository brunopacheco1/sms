package com.dev.bruno.servicesms.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
import com.dev.bruno.servicesms.queue.SmsQueue;
import com.dev.bruno.servicesms.response.Response;
import com.dev.bruno.servicesms.service.SmsService;

@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class SmsResource {

	@Inject
	private SmsService service;
	
	@Inject
	private SmsQueue queueService;
	
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
	public Response add(SmsDTO dto) throws Exception {
		queueService.add(dto);
		
		return new Response(true);
	}
}