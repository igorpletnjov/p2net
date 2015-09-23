package ee.ttu.http.handlers.model;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.service.ParamsParser;

public class SendGetHandler extends GetHandler {
	// Principle: incoming GET has url parameters of some sort, this one sends them to the next address
	
	ParamsParser paramsParser = new ParamsParser();
	
	// resource?sendip=55.66.77.88&sendport=6788&ttl=5&id=wqeqwe23&noask=11.22.33.44_345&noask=111.222.333.444_223
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		// TODO simplify?
		Map<String, String> requestParams = paramsParser.parse( httpExchange.getRequestURI().toString() );
		
		
	}

}
