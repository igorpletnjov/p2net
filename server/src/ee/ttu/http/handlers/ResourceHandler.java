package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.ParamsHelper;
import ee.ttu.util.Log;

//Ressursipäringu saatmine ja edasisaatmine
public class ResourceHandler extends GetHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		Log.debug("URI: " + httpExchange.getRequestURI().getQuery().toString());
		
		ParamsHelper parameters = new ParamsHelper();
		Map<String, String> parsedParameters = parameters.parse(httpExchange.getRequestURI().getQuery().toString());
		
		//example: http://11.22.33.44:2345/resource?sendip=55.66.77.88&sendport=6788&ttl=5&id=wqeqwe23&noask=11.22.33.44_345&noask=111.222.333.444_223
		
		/*
		for (String key: REQUIRED_RESOURCE_PARAMETERS) {
			String value = parameters.get(Key);
			if ( value == null) senderrorresponse();
			else
			//put it into some local variable or whatever
			}
		*/
		
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
