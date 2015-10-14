package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.PostHandler;
import ee.ttu.http.service.JsonParser2;
import ee.ttu.http.service.ResourceHolder;
import ee.ttu.util.Log;

//Rehkenduse tulemuse tagastamine algsele päringu saatjale
public class AnswerMD5Handler extends PostHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		//super.handle(httpExchange);

		String postedData = getRequestBody(httpExchange); //This should work, right?
		Log.info("Posted Data: " + postedData);
		
		JSONObject postedDataJSON = new JSONObject(postedData); //Converts postedData string to jsonobject
		
		Map<String,Object> dataMap = JsonParser2.jsonToMap(postedDataJSON); //JsonParser2 parses the jsonobject to map
		Log.info("dataMap: " + dataMap.toString());
		
		String result = (String) dataMap.get("result");
		
		if ( result != null ) {
			switch (result) {
			case "0":
				Log.info(" SUCCESS : Got cracked result from " + dataMap.get("ip") + ":" + dataMap.get("port") + " -> " + dataMap.get("resultstring"));
				break;
			case "1":
				Log.info(" FAILURE : Did not get cracked result from  " + dataMap.get("ip") + ":" + dataMap.get("port"));
				break;
			case "2":
				Log.info(" TIMEOUT : Did not get cracked result from " + dataMap.get("ip") + ":" + dataMap.get("port"));
				break;
			}
		} else {
			Log.error("Result status from " + dataMap.get("ip") + ":" + dataMap.get("port") + " was null. Resultstring -> " + dataMap.get("resultstring"));
			sendEmptyResponse(400, httpExchange);
		}
		
		ResourceHolder.setResultString((String)dataMap.get("resultstring"));
		
		sendResponse("0", httpExchange);
		/*
			{"ip": "55.66.77.88", 
				"port": "6788",
				"id": "asasasas",
				"md5": "siinonmd5string",
				"result": 0,
				"resultstring": "sssasasc"
			}
			
			ip: tulemuse saatnud masina ip
			port: tulemuse saatnud masina port
			id: esialgse rehkenduspäringu id
			md5: bruteforcetav hash stringina
			result: mis sai (leidsin stringi: 0, ei leidnud stringi: 1, ei jõudnud rehkendada: 2)
			resultstring: leitud string (kui on), mille md5 hash ongi bruteforcetav hash					
		 */
	}
}
