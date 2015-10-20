package ee.ttu.http.handlers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.http.service.ParamsHelper;
import ee.ttu.p2net.util.Constants;
import ee.ttu.util.Log;

//Ressursipäringu saatmine ja edasisaatmine
public class ResourceHandler extends GetHandler {
	
	ParamsHelper paramsHelper = new ParamsHelper();
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		Log.debug("URI: " + httpExchange.getRequestURI().getQuery().toString());
		Map<String, List<String>> parsedParameters = paramsHelper.parse(httpExchange.getRequestURI().getQuery().toString());
		
		//example: http://11.22.33.44:2345/resource?sendip=55.66.77.88&sendport=6788&ttl=5&id=wqeqwe23&noask=11.22.33.44_345&noask=111.222.333.444_223
		//example2 http://localhost:1215/resource?sendip=localhost&sendport=1216&ttl=5&id=wqeqwe23&noask=localhost_1216&noask=localhost_1217
		
		for (String key: Constants.REQUIRED_RESOURCE_PARAMETERS) {
			//Checks if correct parameters
			List<String> value = parsedParameters.get(key);
			if ( value == null || value.isEmpty() ) {
				sendEmptyResponse(400, httpExchange);
				Log.error("Missing request parameter -> " + key);
			}
		}
		try {
			//Gives parameter values to our variables
			String sendip = parsedParameters.get("sendip").get(0);
			String sendport = parsedParameters.get("sendport").get(0);
			Integer ttl = (Integer.parseInt( parsedParameters.get("ttl").get(0) )-1 ); //ttl - 1
			Integer resource = 100;
			
			// Optional
			String id = null;
			List<String> noask = null;
			try {
				id = parsedParameters.get("id").get(0);
				noask = parsedParameters.get("noask");
			} catch (Exception ex) {
				Log.warn("Missing optional parameter, excpetion -> " + ex.getMessage());
			}
			
			Log.info("SendIP: " + sendip + "; sendPort: " + sendport + "; ttl: " + ttl + "; id: " + id + "; noask: " + noask);
			
			if ( ttl > 1 ) { //"Seejuures ei saadeta päringut edasi, kui sissetulnud ttl oli 1 või vähem"
				if ( noask == null )
					noask = new ArrayList<String>();
				noask.add( NetworkCache.getServerIP() + "_" + NetworkCache.getServerPort() );
				Map<String, String> requestParams = paramsHelper.createResourceParams(sendip, sendport, id, ttl, noask);
				//Create new parameters
				String machineToNoask;
				for ( String machine: NetworkCache.getAllMachines() ) {
					machineToNoask = machine.replaceAll(":", "_");
					
					if ( noask != null && !noask.isEmpty() ) {
						if ( noask.contains(machineToNoask) )
							//Doesn't send if a machine in our list is on noask list
							continue;
					}
					
					if ( (sendip + ":" +  sendport).equals(machine) ) //Dont send it back to the original (should be on noask though)
						continue;
					
					sendGET( requestParams, machine + "/resource" );
				}
			}
			
			if (resource > 0){
				//Send resourcereply to the original computer
				String requestbody;
				if (id != null){
					//Bruteforce much
					requestbody = "{\"ip\":\""  + NetworkCache.getServerIP() + "\",\"port\":\"" + NetworkCache.getServerPort()
					+ "\",\"id\":\"" + id + "\",\"resource\":" + resource + "}";
				} else {
					requestbody = "{\"ip\":\""  + NetworkCache.getServerIP() + "\",\"port\":\"" + NetworkCache.getServerPort()
					+	"\",\"resource\":" + resource + "}";
				}
				
				Log.info("RequestBody: " + requestbody);
				
				Map<String, String> requestheader = new HashMap<String, String>();
				requestheader.put("Content-Type", "application/json");
				sendPOST(requestbody, requestheader, sendip + ":" + sendport + "/resourcereply");
			}
			
			sendResponse("0", httpExchange); // "GET-päringu vastuseks on õnnestunud päringu korral number 0"

			
		} catch (Exception ex) {
			Log.error("Error parsing parameters ->" + ex.getMessage());
			sendEmptyResponse(500, httpExchange);
		}
		
		//For testing purposes
		StringBuffer response = new StringBuffer();
		response.append("<html><body> Current URI:" + parsedParameters.toString() + "</body></html>");
		sendResponse( response.toString(), httpExchange ); 
		
		/*
			resource: ütleb, et tegu ressursipäringuga
			sendip: esialgse saatja ip
			sendport: esialgse saatja port
			ttl: kui mitu otsingu-hopi veel teha (iga edasiküsimine vähendab ühe võrra)
			id: optsionaalne päringu identifikaator
			noask: (optsionaalne ja võib korduda) list ip_port kombinatsioonidest, kellele pole mõtet edastada (eraldajaks alakriips)
		 */
	}
}
