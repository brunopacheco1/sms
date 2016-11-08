package com.dev.bruno.servicesms.resources;

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
import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.dto.SmsDTO;
import com.dev.bruno.servicesms.queue.SmsQueue;
import com.dev.bruno.servicesms.response.Response;
import com.dev.bruno.servicesms.service.SmsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces(MediaType.APPLICATION_JSON)
@Stateless
@Path("/sms")
@Api("servicos")
public class SmsResource {

	@Inject
	private SmsService service;
	
	@Inject
	private SmsQueue queueService;
	
	@GET
	@ApiOperation(value = "Serviço de listagem de SMS enviados.")
	public ResultDTO list(@QueryParam("query") String queryStr, @QueryParam("start") Integer start, @QueryParam("limit") Integer limit, @QueryParam("order") String order, @QueryParam("dir") String dir) throws Exception {
		return service.list(queryStr, start, limit, order, dir);
	}
	
	@GET
	@Path("/{id}")
	@ApiOperation(value = "Serviço de busca de SMS único")
	public SmsDTO get(@PathParam("id") Long id) throws Exception {
		return service.get(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Serviço de envio de SMS.")
	public Response send(SentSmsDTO dto) throws Exception {
		queueService.send(dto);
		
		return new Response(true);
	}
}