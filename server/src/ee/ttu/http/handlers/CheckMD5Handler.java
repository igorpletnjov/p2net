package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.PostHandler;
import ee.ttu.http.service.JsonParser2;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.http.service.ResourceHolder;
import ee.ttu.md5.MD5Cracker;
import ee.ttu.util.Log;

//Rehkendusp�ringu saatmine
public class CheckMD5Handler extends PostHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		//super.handle(httpExchange);
		
		String postedData = getRequestBody(httpExchange); //This should work, right?
		Log.info("Posted Data: " + postedData);
		
		JSONObject postedDataJSON = new JSONObject(postedData); //Converts postedData string to jsonobject
		
		Map<String,Object> dataMap = JsonParser2.jsonToMap(postedDataJSON); //JsonParser2 parses the jsonobject to map
		
		Log.info("dataMap: " + dataMap.toString());
		//Log.info("mapTest: " + dataMap.get("ip"));
		
		String originalIP = (String) dataMap.get("ip");
		String originalPort = (String) dataMap.get("port");
		String id = (String) dataMap.get("id");
		String md5 = (String) dataMap.get("md5");
		
		/* TODO - Sten
		 * Selle klassi k�ivitab �hte t�kki tegev arvuti
		 * Seega ta peaks lugema sisse endale antud t�ki - seda teeb ta �leval,
		 * 				CrackHandleris saadetud POST'ist v�ljalugedes
		 * 				String range= (String) dataMap.get("range"); vms
		 * 
		 * Hetkel kutsub v�lja ta String answer = MD5Cracker.calculator(md5);
		 * 				ehk ta teeb terve hash'iga praegu, mis on vale
		 * 				Ta peaks andma t�en�oliselt nii md5, kui ka siis oma selle range'i, 
		 * 				seega pead MD5Crackerit sedasi muutma, et v�taks 2 sisendit
		 * 
		 * Vastuse saamise puhul peaks ta saatma vastuse tagasi, mida ta teeb all pool
		 * V�ib-olla peaks ta kuidagi ka aega v�tma, sest Tanelil sedasi kirjas - "result: mis sai (leidsin stringi: 0, ei leidnud stringi: 1, ei j�udnud rehkendada: 2)"
		 */
		
		Log.info("Original ip: " + originalIP + ", Original port: " + originalPort + ", id: " + id + ", md5: " + md5);
		
		int result = 1;
		String answer = MD5Cracker.calculator(md5); //See on vale
		if (answer != null){
			result = 0;
		}
		
		JSONObject answerObject = new JSONObject();
		answerObject.put("ip", NetworkCache.getServerIP());
		answerObject.put("port", String.valueOf( NetworkCache.getServerPort() ));
		answerObject.put("id", id);
		answerObject.put("md5", md5);
		answerObject.put("result", String.valueOf( result ));
		answerObject.put("resultstring", answer);
		
		String requestbody = answerObject.toString();
		Log.info("RequestBody: " + requestbody);
		
		ResourceHolder.setResultString(answer);
	
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
  			   
			ip: rehkendusp�ringu saatja ip
			port: rehkendusp�ringu saatja port
			id: rehkendusp�ringu id
			md5: hash stringina
			ranges: list stringidest, kus v�ivad olla wildcardid ?
			wildcard: optsionaalne: kui olemas, siis s�mbol, mida wildcardina ? asemel kasutatakse
			symbolrange: optsionaalne: list baidivahemike paaridest, mida wildcardi asemel katsetada.
		 */
		
	}
}
