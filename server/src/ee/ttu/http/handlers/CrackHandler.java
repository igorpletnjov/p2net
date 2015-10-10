package ee.ttu.http.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.GetHandler;

public class CrackHandler extends GetHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		//example http://11.22.33.44:2345/crack?md5=dd97813dd40be87559aaefed642c3fbb
	}
}
