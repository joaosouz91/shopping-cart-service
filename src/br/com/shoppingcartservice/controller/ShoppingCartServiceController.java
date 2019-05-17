package br.com.shoppingcartservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/")
public class ShoppingCartServiceController {
	
	@GET
	@Path("/helloworld")
	public Response getHelloWorld() {
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("msg", "helloworld");
		
		return Response.ok().status(200).entity(new Gson().toJson(jsonMap)).build();
	}


}
