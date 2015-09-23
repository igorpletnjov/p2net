package ee.ttu.http.handlers.model;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class SendPostHandler extends PostHandler {
	// Principle: incoming POST has some request body that we will parse and send on to the next address

	// see: jsonparser
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
	}
}
