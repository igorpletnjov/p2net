package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.ServerMain;
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
		//example2 http://localhost:1215/crack?md5=4a8a08f09d37b73795649038408b5f33
		
		String uri = httpExchange.getRequestURI().getQuery().toString();
		
		Log.debug("URI: " + uri);
		
		String[] split =  uri.split("=");
		String hashToCrack = split[1];
		
		Log.debug("Hash: " + hashToCrack);
		
		// TODO sends this hashToCrack out by pieces to the network
		String result = MD5Cracker.calculator(hashToCrack); 

		Map<String, String> parameters = new HashMap();
		parameters.put("sendip", NetworkCache.getServerIP());
		parameters.put("sendport", String.valueOf(NetworkCache.getServerPort()));
		parameters.put("ttl", "5");
		parameters.put("id", "laksfwe34");
		parameters.put("noask", NetworkCache.getServerIP() + "_" + String.valueOf(NetworkCache.getServerPort()));
		
		//http://11.22.33.44:2345/resource?sendip=55.66.77.88&sendport=6788&ttl=5&id=wqeqwe23&noask=11.22.33.44_345&noask=111.222.333.444_223
		for (String machine : NetworkCache.getAllMachines()){
			sendGET(parameters, machine + "/resource");
		}

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
