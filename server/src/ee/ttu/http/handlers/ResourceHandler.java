package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.ParamsHelper;
import ee.ttu.util.Log;

public class ResourceHandler extends GetHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		Log.debug("URI: " + httpExchange.getRequestURI().getQuery().toString());
		
		ParamsHelper parameters = new ParamsHelper();
		Map<String, String> parsedParameters = parameters.parse(httpExchange.getRequestURI().getQuery().toString());
		
		/*
		for (String key: REQUIRED_RESOURCE_PARAMETERS) {
			String value = parameters.get(Key);
			if ( value == null) senderrorresponse();
			else
			//put it into some local variable or whatever
			}
		*/
		
		//For testing purposes
		StringBuffer response = new StringBuffer();
		response.append("<html><body> Current URI:" + parsedParameters.toString() + "</body></html>");
		sendResponse( response.toString(), httpExchange ); 
		
	}
}
