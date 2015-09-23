package ee.ttu.http.service;

import ee.ttu.http.model.IntegerPair;
import ee.ttu.http.model.JsonObject;

public class JsonParser {
	
	/*
	 * {
			"ip": "55.66.77.88", 
	   		"port": "6788",
	   		"id": "siinonid",
	   		"md5": "siinonmd5string",
	   		"ranges": ["ax?o?ssss","aa","ab","ac","ad"],
	   		"wildcard": "?",
	   		"symbolrange": [[3,10],[100,150]]
  		}
	 */
	
	public static final String SYMBOL_RANGE_PATTERN = "";
	
	public JsonObject parseJson ( String json ) {
		// Turn JSON into java object here
		// Use regex or String.split()
		JsonObject jsonObject = new JsonObject();
		
		// ...
		IntegerPair integerPair = new IntegerPair();
		jsonObject.getSymbolRange().add( integerPair );
		
		System.out.println("DEBUG: Parsed Json Object : " + jsonObject.toString());
		
		return jsonObject;
	}

}
