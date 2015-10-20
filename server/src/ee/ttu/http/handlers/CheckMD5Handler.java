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

//Rehkenduspäringu saatmine
public class CheckMD5Handler extends PostHandler{
	public static String answer;
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
		//MD5Cracker cracker = new MD5Cracker();
		//String md5 = (String) dataMap.get(cracker.generate("", 0, 0, "b764be88d07a84126fd57a9c4071fed8"));
 		
		/* TODO - Sten
		 * Selle klassi käivitab ühte tükki tegev arvuti
		 * Seega ta peaks lugema sisse endale antud tüki - seda teeb ta üleval,
		 * 				CrackHandleris saadetud POST'ist väljalugedes
		 * 				String range= (String) dataMap.get("range"); vms
		 * 
		 * Hetkel kutsub välja ta String answer = MD5Cracker.calculator(md5);
		 * 				ehk ta teeb terve hash'iga praegu, mis on vale
		 * 				Ta peaks andma tõenäoliselt nii md5, kui ka siis oma selle range'i, 
		 * 				seega pead MD5Crackerit sedasi muutma, et võtaks 2 sisendit
		 * 
		 * Vastuse saamise puhul peaks ta saatma vastuse tagasi, mida ta teeb all pool
		 * Võib-olla peaks ta kuidagi ka aega võtma, sest Tanelil sedasi kirjas - "result: mis sai (leidsin stringi: 0, ei leidnud stringi: 1, ei jõudnud rehkendada: 2)"
		 */
		
		Log.info("Original ip: " + originalIP + ", Original port: " + originalPort + ", id: " + id + ", md5: " + md5);
		
		int result = 1;
		//MD5Cracker cracker = new MD5Cracker();
		
		/*while(MD5Cracker.range > 0) {
			MD5Cracker.j = 62 - MD5Cracker.range;
			cracker.generate("", 0, 0, "b764be88d07a84126fd57a9c4071fed8");
			answer = MD5Cracker.calculator(md5);
			if(MD5Cracker.range - MD5Cracker.tempRange > 0 && MD5Cracker.range - (2 * MD5Cracker.tempRange) > 0 ) {
				MD5Cracker.tempRange = MD5Cracker.range;
			}
		}*/
		/*if(MD5Cracker.range > 0) {
			MD5Cracker.j = 62 - MD5Cracker.range;
			MD5Cracker cracker = new MD5Cracker();
			cracker.generate("", 0, 0, "dd97813dd40be87559aaefed642c3fbb");
			MD5Cracker.range = MD5Cracker.range - MD5Cracker.tempRange;
			answer = MD5Cracker.code;
		}*/
		/*while(MD5Cracker.range > 0) {
			MD5Cracker cracker = new MD5Cracker();
			cracker.generate("", 0, 0, "b764be88d07a84126fd57a9c4071fed8");
			
			MD5Cracker.j = 62 - MD5Cracker.range;
			answer = MD5Cracker.code;
		}*/
		//String answer = MD5Cracker.code;
		//MD5Cracker cracker = new MD5Cracker();
		//cracker.generate("", 0, 0, "dd97813dd40be87559aaefed642c3fbb");
		//String answer = MD5Cracker.calculator(md5); //See on vale
		//while(MD5Cracker.range > 0) {
			//cracker.generate("", 0, 0, "b764be88d07a84126fd57a9c4071fed8");
			//MD5Cracker.calculator(toCrack)
			
			//MD5Cracker.j = 62 - MD5Cracker.range;
			//if(MD5Cracker.range - MD5Cracker.tempRange > 0 && MD5Cracker.range - (2 * MD5Cracker.tempRange) > 0 ) {
			//	MD5Cracker.tempRange = MD5Cracker.range;
			//}
			//cracker.generate("", 0, 0, "dd97813dd40be87559aaefed642c3fbb");
			//MD5Cracker.range = MD5Cracker.range - MD5Cracker.tempRange
		//}
		//MD5Cracker.range();
		answer = MD5Cracker.code;
		
		if (answer.equals(MD5Cracker.calculator(md5))){
			result = 0;
		}
		int gg = MD5Cracker.tempRange;
		String tempLen = MD5Cracker.tempSet.charAt(MD5Cracker.j) + "-" + MD5Cracker.tempSet.charAt(MD5Cracker.tempRange-1 + MD5Cracker.j);
		JSONObject answerObject = new JSONObject(); 
		answerObject.put("ip", NetworkCache.getServerIP());
		answerObject.put("port", String.valueOf( NetworkCache.getServerPort() ));
		answerObject.put("id", id);
		answerObject.put("md5", md5);
		answerObject.put("result", String.valueOf( result ));
		answerObject.put("resultstring", answer);
		answerObject.put("range", tempLen);
		
		
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
