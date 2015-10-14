package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.PostHandler;
import ee.ttu.http.service.JsonParser2;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.util.Log;

//Ressursipäringu tulemuse tagastamine algsele pärijale
public class ResourceReplyHandler extends PostHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		//super.handle(httpExchange);

		String postedData = getRequestBody(httpExchange); //This should work, right?
		Log.info("Posted Data: " + postedData);
		
		JSONObject postedDataJSON = new JSONObject(postedData); //Converts postedData string to jsonobject
		
		Map<String,Object> dataMap = new HashMap<>();
		dataMap = JsonParser2.jsonToMap(postedDataJSON); //JsonParser2 parses the jsonobject to map
		
		Log.info("dataMap: " + dataMap.toString());
		//Log.info("mapTest: " + dataMap.get("ip"));
		
		if (dataMap.get("ip")  != null && dataMap.get("port") != null ){
			//TODO Võib-olla peaks kontrollima et dataMap.get("id") == ResourceHolder.getId() ???
			NetworkCache.readyMachines.add(dataMap.get("ip") + ":" + dataMap.get("port"));
			Log.info("Ready machines: " + NetworkCache.getReadyMachines().toString());
		}

		sendResponse("0", httpExchange);
		
		/*
		POST'itatud data:
			{"ip": "55.66.77.88", 
   				"port": "6788",
   				"id": "asasasas",
   				"resource": 100 
   			}
   				
   			ip: tulemuse saatnud masina ip
			port: tulemuse saatnud masina port
			id: esialgse päringu id (kui oli algselt antud)
			resource: minu rehkendusvõimsus (keskmine masin on 100) seda ei pea täpsemalt hindama, võib alati öelda 100
		 */
		
	}
}
