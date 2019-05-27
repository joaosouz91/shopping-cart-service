package br.com.shoppingcart.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/")
public class ShoppingCartController {
	
	@GET
	@Path("/status")
	@Produces("application/json")
	public Response getHelloWorld() {
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("msg", "helloworld");
		
		return Response.ok().status(200).entity(new Gson().toJson(jsonMap)).build();
	}


}
