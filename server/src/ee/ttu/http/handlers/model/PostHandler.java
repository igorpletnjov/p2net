package ee.ttu.http.handlers.model;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ee.ttu.util.Log;

public abstract class PostHandler extends BaseHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		if ( !"POST".equals( httpExchange.getRequestMethod() ) ) {
			Log.error("Request " + getRequestInfo(httpExchange) + " is not a POST request");
		    sendEmptyResponse(405, httpExchange ); // send 405 Method not Allowed
		    return;
		}
	}

}
