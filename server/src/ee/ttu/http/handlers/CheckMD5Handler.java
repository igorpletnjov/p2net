package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.PostHandler;
import ee.ttu.http.service.JsonParser2;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.md5.MD5Cracker;
import ee.ttu.util.Log;

//Rehkenduspäringu saatmine
public class CheckMD5Handler extends PostHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		//super.handle(httpExchange);
		
		String postedData = getRequestBody(httpExchange); //This should work, right?
		Log.info("Posted Data: " + postedData);
		
		JSONObject postedDataJSON = new JSONObject(postedData); //Converts postedData string to jsonobject
		
		JsonParser2 parser= new JsonParser2();
		Map<String,Object> dataMap = new HashMap();
		dataMap = parser.jsonToMap(postedDataJSON); //JsonParser2 parses the jsonobject to map
		
		Log.info("dataMap: " + dataMap.toString());
		//Log.info("mapTest: " + dataMap.get("ip"));
		
		String originalIP = (String) dataMap.get("ip");
		String originalPort = (String) dataMap.get("port");
		String id = (String) dataMap.get("id");
		String md5 = (String) dataMap.get("md5");
		
		Log.info("Original ip: " + originalIP + ", Original port: " + originalPort + ", id: " + id + ", md5: " + md5);
		
		int result = 1;
		String answer = MD5Cracker.calculator(md5);
		if (answer != null){
			result = 0;
		}
		
		String requestbody = "{\"ip\":\""  + NetworkCache.getServerIP() + "\",\"port\":\"" + NetworkCache.getServerPort()
		+	"\",\"id\":\"" + id + "\",\"md5\":\"" + md5 + "\",\"result\":" + result + ",\"resultstring\":" + answer +  "}";
		Log.info("RequestBody: " + requestbody);
	
		Map<String, String> requestheader = new HashMap<String, String>();
		requestheader.put("Content-Type", "application/json");
		sendPOST(requestbody, requestheader, originalIP + ":" + originalPort + "/answermd5");

		sendResponse("0", httpExchange);
		
		
		/* 
		POST-itatud data:
			{"ip": "55.66.77.88", 
				"port": "6788",
   				"id": "siinonid",
     			 "md5": "siinonmd5string",
      			"ranges": ["ax?o?ssss","aa","ab","ac","ad"],
      			"wildcard": "?",
     			"symbolrange": [[3,10],[100,150]]
			}
  			   
			ip: rehkenduspäringu saatja ip
			port: rehkenduspäringu saatja port
			id: rehkenduspäringu id
			md5: hash stringina
			ranges: list stringidest, kus võivad olla wildcardid ?
			wildcard: optsionaalne: kui olemas, siis sümbol, mida wildcardina ? asemel kasutatakse
			symbolrange: optsionaalne: list baidivahemike paaridest, mida wildcardi asemel katsetada.
		 */
		
	}
}
