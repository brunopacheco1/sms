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

import com.dev.bruno.servicesms.dto.ResponseDTO;
import com.dev.bruno.servicesms.dto.ResultDTO;
import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.dto.SmsDTO;
import com.dev.bruno.servicesms.service.SmsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Produces(MediaType.APPLICATION_JSON)
@Stateless
@Path("/sms")
@Api("servicos")
public class SmsResource {

	@Inject
	private SmsService service;
	
	@GET
	@ApiOperation(value = "Serviço de listagem de SMS enviados.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Resposta com resultado da busca.", response = ResultDTO.class),
	    @ApiResponse(code = 500, message = "Erro não esperado.", response = ResponseDTO.class)
	})
	public ResultDTO list(
	   @ApiParam(required=false, example="teste", value="Campo de pesquisa textual nos campos: to, from, body e failureMsg.") @QueryParam("query") String queryStr, 
	   @ApiParam(required=false, example="0", defaultValue="0", value="Ponto inicial do resultado da consulta.") @QueryParam("start") Integer start,
	   @ApiParam(required=false, example="100", defaultValue="100", value="Número limite de linhas da consulta.") @QueryParam("limit") Integer limit,
	   @ApiParam(required=false, example="id", defaultValue="id", allowableValues="id,validDate,sentDate,invalidationDate,failureDate", value="Campo que ordenará a consulta.") @QueryParam("order") String order,
	   @ApiParam(required=false, example="asc", defaultValue="asc", allowableValues="dir,asc", value="Direção da ordenação da consulta.") @QueryParam("dir") String dir) throws Exception {
		return service.list(queryStr, start, limit, order, dir);
	}
	
	@GET
	@Path("/{id}")
	@ApiOperation(value = "Serviço de busca de SMS único.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "SMS encontrado.", response = SmsDTO.class),
	    @ApiResponse(code = 500, message = "Erro não esperado.", response = ResponseDTO.class)
	})
	public SmsDTO get(@ApiParam(required=true, example="1", value="ID do SMS.") @PathParam("id") Long id) throws Exception {
		return service.get(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Serviço de envio de SMS.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "SMS enviado.", response = ResponseDTO.class),
	    @ApiResponse(code = 409, message = "SMS não passou na validação de campos.", response = ResponseDTO.class),
	    @ApiResponse(code = 500, message = "Erro não esperado.", response = ResponseDTO.class)
	})
	public ResponseDTO send(@ApiParam(required=true, value="SMS que será enviado.") SentSmsDTO dto) throws Exception {
		service.queue(dto);
		
		return new ResponseDTO(true);
	}
}