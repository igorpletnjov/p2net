package ee.ttu.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.PostHandler;
import ee.ttu.http.service.JsonParser2;

// This is mostly a test class

// Accepts POSTed JSON data, parses into object and returns status in JSON
public class JsonHandler extends PostHandler {
	
	JsonParser2 jsonParser = new JsonParser2();
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		// Request must be in JSON
		if ( !checkHeader("Content-Type", "application/json", httpExchange ) ) {
			sendEmptyResponse( 400, httpExchange ); // send 400 Bad Request
		}
		
		sendJsonResponse( "{ \"status\":\"success\"}", httpExchange );
	}

}
