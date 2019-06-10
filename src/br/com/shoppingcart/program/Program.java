package br.com.shoppingcart.program;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.uri.UriTemplate;
import com.sun.jersey.server.impl.application.WebApplicationContext;

import br.com.shoppingcart.enums.OperationStatus;

public class Program {

	public static void main(String[] args) {
		
		String template = "http://example.com/carts/{clientCode}/items/1";
		String template2 = "http://example.com/carts/{clientCode}";
	    UriTemplate uriTemplate = new UriTemplate(template);
	    String uri = "http://example.com/name/Bob/age/47";
	    Map<String, String> parameters = new HashMap<>();

	    uriTemplate.match(uri, parameters);
	   
	    parameters.put("name","Arnold");
	    parameters.put("age","110");
	    
	    WebApplicationContext wac = new WebApplicationContext(null, null, null);
	    
	    UriBuilder builder = UriBuilder.fromPath(template);

	    UriBuilder output = builder.replacePath(template2);
	    System.out.println(output.build());

	}

}
