package ee.ttu.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.ResourceHolder;

public class IndexHandler extends GetHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		StringBuffer response = new StringBuffer();
		
		response.append("<html><body>");
		response.append("<h1> Welcome </h1>");
		response.append("<p> List of valid pages: </p>");
		for ( HttpContext context: ResourceHolder.getContextList()) {
			response.append("<a href=\"" + context.getPath() + "\" >" + context.getPath() + "</a>" );
			response.append("<br>");
		}
		response.append("</body></html>");
		
		sendResponse( response.toString(), httpExchange ); 
		
	}
}
