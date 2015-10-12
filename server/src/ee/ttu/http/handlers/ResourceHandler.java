package ee.ttu.http.handlers;

import java.io.IOException;
import java.net.URL;
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
		
		
		for (String key: Constants.REQUIRED_RESOURCE_PARAMETERS) {
			List<String> value = parsedParameters.get(key);
			if ( value == null || value.isEmpty() ) {
				sendEmptyResponse(400, httpExchange);
				Log.error("Mising request parameter -> " + key);
			}
		}
		try {
			String sendip = parsedParameters.get("sendip").get(0);
			String sendport = parsedParameters.get("sendport").get(0);
			Integer ttl = Integer.parseInt( parsedParameters.get("ttl").get(0) );
			
			// Optional
			String id = null;
			List<String> noask = null;
			try {
				id = parsedParameters.get("id").get(0);
				noask = parsedParameters.get("noask");
			} catch (Exception ex) {
				Log.warn("Missing optional parameter, excpetion -> " + ex.getMessage());
			}
			
			if ( ttl > 0 ) {
				
				Map<String, String> requestParams = paramsHelper.createResourceParams(sendip, sendport, id, ttl, noask);
				for ( String machine: NetworkCache.getAllMachines() ) {
					if ( noask != null && !noask.isEmpty() ) {
						if ( noask.contains(machine) )
							continue;
					}
					
					if ( (sendip + sendport).equals(machine) ) // obv dont fuckin send it back lmao
						continue;
					
					sendGET( requestParams, machine + "/resource" );
				}
			}
			
			sendPOST(null, null, sendip + ":" + sendport + "/resourcereply"); // TODO JSON as request body
			
			
		} catch (Exception ex) {
			Log.error("Error parsing parameters ->" + ex.getMessage());
			sendEmptyResponse(500, httpExchange);
		}
		
		// TODO sendResponse("0", httpExchange); <- tannu tahab nii
		
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
