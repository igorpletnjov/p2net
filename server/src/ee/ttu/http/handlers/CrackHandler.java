package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.http.service.TextReader;
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
		String hashToCrack = split[1];
		
		Log.debug("Hash: " + hashToCrack);
		
		String result = MD5Cracker.calculator(hashToCrack); 
		
		// TODO sends this hashToCrack out by pieces to the network

		
		//sendGET(null, "http://www.example.com");
		
		//For testing purposes
		StringBuffer response = new StringBuffer();
		response.append("<html><body> Hash: " + hashToCrack + " <br> Answer: " + result + "<br><br> Machines: ");
		for (int i = 0; i < NetworkCache.getAllMachines().size(); i++){
			response.append("<br>" + NetworkCache.getAllMachines().get(i));
		}
		response.append("</body></html>");
		sendResponse( response.toString(), httpExchange ); 
	}
}
