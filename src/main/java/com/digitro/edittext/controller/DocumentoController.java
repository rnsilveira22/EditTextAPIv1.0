package com.digitro.edittext.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.digitro.edittext.model.Documento;

@Path("/documento")
public class DocumentoController {
	/**
	 * Esse método lista todos documentos cadastrados na base
	 * */
	@GET
	@PathParam("/{titulo},{corpo}")
	@Produces("application/json ;charset=UTF-8" )
	public List<Documento> getDocumentos() {
		//DocumentoService documentoServico = new DocumentoService();
		//ist<Documento> documentos = documentoServico.listar(null, null);		 
	
		return null;
	
	
	}
	
	
	
 
	
	@GET
	@PathParam("/{id}")
	@Produces("application/json")
	public void getDocumento() {
		
	}
	
	@PUT
	@PathParam("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public String updateDocumento() {
		return "";
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String postDocumento() {
		return "";
	}
	
	@DELETE
	@PathParam("/{id}")
	public void deleteDocumento() {
		
	}
}
