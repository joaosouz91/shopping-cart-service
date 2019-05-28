package br.com.shoppingcart.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.omg.CORBA.portable.ApplicationException;

import com.google.gson.Gson;

import br.com.shoppingcart.dto.CartDTO;
import br.com.shoppingcart.dto.CartListDTO;
import br.com.shoppingcart.service.ShoppingCartService;

@Path("/")
public class ShoppingCartController {

	private ShoppingCartService cartService = new ShoppingCartService();

	@GET
	@Path("/status")
	@Produces("application/json")
	public Response getHelloWorld() {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("msg", "helloworld");
		return Response.ok().status(200).entity(new Gson().toJson(jsonMap)).build();
	}

	@GET
	@Path("/carts")
	@Produces("application/json")
	public Response listAllCarts() {

		CartListDTO dto = cartService.getAllCarts();
		if (dto != null) {
			return Response.status(200).entity(new Gson().toJson(dto)).build();
		}
		return Response.status(HttpServletResponse.SC_NO_CONTENT).build();
	}

	@POST
	@Path("/carts")
	@Consumes("application/json")
	public Response createCart(@Context UriInfo uriInfo, String json) throws ApplicationException, Exception {

		CartDTO dto = cartService.create(json);
		if (dto != null) {
			if (dto.getStatusCode() == 409) {

				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put("error", "Já existe um carrinho ativo para este cliente.");
				return Response.status(409).entity(new Gson().toJson(jsonMap)).build();
			}
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			uriBuilder.path(dto.getCart().getClientCode());
			return Response.ok(uriBuilder.build()).status(dto.getStatusCode()).build();
		}
		return Response.status(HttpServletResponse.SC_BAD_REQUEST).build();
	}

}
