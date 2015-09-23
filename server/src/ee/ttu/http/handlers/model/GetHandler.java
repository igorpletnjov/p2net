package ee.ttu.http.handlers.model;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class GetHandler extends BaseHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		if ( !"GET".equals( httpExchange.getRequestMethod() ) ) {
			System.out.println("ERROR: Request " + getRequestInfo(httpExchange) + " is not a GET request");
			sendEmptyResponse(405, httpExchange);
		    return;
		}
	}

}
