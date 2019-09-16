package br.com.shoppingcart.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public Response getStatus() {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "published");
		return Response.ok().entity(new Gson().toJson(jsonMap)).build();
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
	
	@GET
	@Path("/carts/{clientCode}")
	@Produces("application/json")
	public Response getCart(@PathParam("clientCode") String clientCode) {

		CartDTO dto = cartService.getCart(clientCode);
		if (dto != null) {
			dto.setStatusCode(null);
			return Response.ok().entity(new Gson().toJson(dto)).build();
		}
		return Response.status(HttpServletResponse.SC_NO_CONTENT).build();
	}

	@POST
	@Path("/carts")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createCart(@Context UriInfo uriInfo, String json) throws ApplicationException, Exception {

		CartDTO dto = cartService.createCart(json);
		if (dto != null) {
			if (dto.getStatusCode() == 409) {

				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put("error", "Já existe um carrinho ativo para este cliente.");
				return Response.status(409).entity(new Gson().toJson(jsonMap)).build();
			}
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			uriBuilder.path(dto.getCart().getClientCode());
			return Response.created(uriBuilder.build()).build();
		}
		return Response.status(HttpServletResponse.SC_BAD_REQUEST).build();
	}
	
	@DELETE
	@Path("/carts/{clientCode}")
	@Produces("application/json")
	public Response deleteCart(@PathParam("clientCode") String clientCode) {
		
		if(cartService.removeCart(clientCode)) {
			return Response.ok().build();
		}
		return Response.noContent().build();
	}
	
	@POST
	@Path("/carts/{clientCode}/items")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addItemCart(@PathParam("clientCode") String clientCode, @Context UriInfo uriInfo, String json) {
		
		CartDTO dto = cartService.addItemCart(clientCode, json);
		
		if(dto != null) {
			if(dto.getStatusCode() == HttpServletResponse.SC_CREATED) {
				UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
				uriBuilder.path("/carts/" + dto.getCart().getClientCode());
				return Response.created(uriBuilder.build()).build();
			
			} else if(dto.getStatusCode() == HttpServletResponse.SC_OK) {
				UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
				uriBuilder.path("/carts/" + dto.getCart().getClientCode());
				return Response.ok().build();
			}
		}
		return Response.status(HttpServletResponse.SC_BAD_REQUEST).build();
	}
	
	@DELETE
	@Path("/carts/{clientCode}/items/{itemId}")
	@Produces("application/json")
	public Response removeItemCart(@PathParam("clientCode") String clientCode, @PathParam("itemId") Long itemId, @Context UriInfo uriInfo) {

		CartDTO dto = cartService.removeItemCart(clientCode, itemId);
		
		if(dto != null) {
			if(dto.getStatusCode() == HttpServletResponse.SC_OK) {
				UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
				uriBuilder.path("/carts/" + dto.getCart().getClientCode());
				return Response.ok().build();
			}
		}
		return Response.status(HttpServletResponse.SC_NOT_FOUND).build();
	}
	
	@GET
	@Path("/carts/statistics/averageticket")
	@Produces("application/json")
	public Response getAverageTicketValue() {
		
		BigDecimal averageTicket = cartService.getTicketAverageValue();
		
		if(averageTicket != null && !averageTicket.equals(BigDecimal.ZERO)) {
			Map<String, BigDecimal> jsonMap = new HashMap<String, BigDecimal>();
			jsonMap.put("averageTicket", averageTicket);
			return Response.ok().entity(new Gson().toJson(jsonMap)).build();
		}
		return Response.noContent().build();
	}
	
	
}
