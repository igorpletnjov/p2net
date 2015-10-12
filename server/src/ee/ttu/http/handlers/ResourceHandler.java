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
	
	ParamsHelper parameters = new ParamsHelper();
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		Log.debug("URI: " + httpExchange.getRequestURI().getQuery().toString());
		Map<String, List<String>> parsedParameters = parameters.parse(httpExchange.getRequestURI().getQuery().toString());
		
		//example: http://11.22.33.44:2345/resource?sendip=55.66.77.88&sendport=6788&ttl=5&id=wqeqwe23&noask=11.22.33.44_345&noask=111.222.333.444_223
		
		
		for (String key: Constants.REQUIRED_RESOURCE_PARAMETERS) {
			List<String> value = parsedParameters.get(key);
			if ( value == null || value.isEmpty() ) {
				sendEmptyResponse(400, httpExchange);
				Log.error("Mising request parameter -> " + key);
			}
		}
		
		String sendip = parsedParameters.get("sendip").get(0);
		String sendport = parsedParameters.get("sendport").get(0);
		Integer ttl = 0;
		try {
			ttl = Integer.parseInt( parsedParameters.get("ttl").get(0) );
		} catch (Exception ex) {
			Log.warn("Error parsing ttl -> " + ttl);
			ttl = 0;
		}
		
		// Optional
		String id = parsedParameters.get("id").get(0);
		List<String> noask = parsedParameters.get("noask");
		
		if ( ttl > 0 ) {
			for ( String machine: NetworkCache.getAllMachines() ) {
				if ( noask != null && !noask.isEmpty() ) {
					if ( noask.contains(machine) )
						continue;
				}
				
				if ( sendip.equals(machine) ) // obv dont fuckin send it back lmao
					continue;
				
				// TODO put our own IP into the noask array?
				// TODO decrement ttl by 1
				sendGET( null, machine ); // TODO put actual parameters instead of null
			}
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
