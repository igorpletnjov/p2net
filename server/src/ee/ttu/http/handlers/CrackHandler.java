package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.ParamsHelper;
import ee.ttu.md5.MD5Cracker;
import ee.ttu.util.Log;

public class CrackHandler extends GetHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		//example http://11.22.33.44:2345/crack?md5=dd97813dd40be87559aaefed642c3fbb
		
		String uri = httpExchange.getRequestURI().getQuery().toString();
		
		Log.debug("URI: " + uri);
		
		String[] split =  uri.split("=");
		
		Log.debug("Hash: " + split[1]);
		
		MD5Cracker cracker = new MD5Cracker();
		String result = cracker.calculator("dd97813dd40be87559aaefed642c3fbb"); // "igor" :^)
		
		// TODO sends this hash out by pieces to the network
		
		//For testing purposes
		StringBuffer response = new StringBuffer();
		response.append("<html><body> Hash: " + split[1] + " <br> Answer: " + result + "</body></html>");
		sendResponse( response.toString(), httpExchange ); 
	}
}
