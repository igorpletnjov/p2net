package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ee.ttu.http.model.IntegerPair;
import ee.ttu.http.model.JsonObject;
import ee.ttu.util.Log;

public class JsonParser {
	
	// TODO doesnt check if json is valid btw, oh well
	
	/*näiteks:
	 * 
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
	
	// I don't think these three will 'overlap' in any way
	// this distinction is pretty important btw
	public static final String REGEX_STRING_PATTERN = "\"(.+)\"[ ]*:[ ]*\"(.+)\""; // for almost everything
	public static final String REGEX_ARRAY_PATTERN = "\"(.+)\"[ ]*:[ ]*(\\[.+\\])"; // for ranges and symbolrange
	public static final String REGEX_INTEGER_PATTERN = "\"(.+)\"[ ]*:[ ]*([0-9]+)"; // for numbers without ""
	
	public JsonObject parseJson ( String json ) {
		// Turn JSON into java object here -> better idea: turn it into a Map first and then decide what object to turn it into
		// Use regex or String.split() -> I think regex is simpler here
		
		Matcher stringMatcher = Pattern.compile(REGEX_STRING_PATTERN).matcher( json );
		Matcher arrayMatcher = Pattern.compile(REGEX_ARRAY_PATTERN).matcher( json );
		Matcher integerMatcher = Pattern.compile(REGEX_INTEGER_PATTERN).matcher( json );
		
		Map<String, Object> parsedJsonMap = new HashMap<String, Object>();
		
		while ( stringMatcher.find() ) {
			try {
				parsedJsonMap.put( stringMatcher.group(1) , stringMatcher.group(2) );
			} catch ( Exception ex ) {
				Log.error("Error parsing json string object, " + ex.getMessage());
				continue;
			}
		}
		
		while ( integerMatcher.find() ) {
			try {
				parsedJsonMap.put( integerMatcher.group(1) , Integer.parseInt( integerMatcher.group(2) ) );
			} catch ( Exception ex ) {
				Log.error("Error parsing json integer object, " + ex.getMessage());
				continue;
			}
		}
		
		while ( arrayMatcher.find() ) {
			try {
				parsedJsonMap.put( arrayMatcher.group(1) , parseJsonArray( arrayMatcher.group(2) ) );
			} catch ( Exception ex ) {
				Log.error("Error parsing json array object, " + ex.getMessage());
				continue;
			}
		}
		
		// TODO Do something with parsedJsonMap now
		
		//JsonObject jsonObject = new JsonObject();
		
		// ...
		//IntegerPair integerPair = new IntegerPair();
		//jsonObject.getSymbolRange().add( integerPair );
		
		// Eventually...
		//Log.debug( "Parsed Json Object : " + jsonObject.toString() );
		
		//return jsonObject;
		return null;
	}
	
	// TODO use .split to get array
	List<String> parseJsonArray( String json ) {
		List<String> array = new ArrayList<String>();
		
		return array;
	}
	
	// TODO parse symbolrange using .split() after using parseJsonArray()
	IntegerPair getIntegerPair( List<String> symbolRange ) {
		return null;
	}
	
	JsonObject parseResource( Map<String, Object> jsonMap ) {
		// TODO
		// use p2net Constants here to check if everything is there
		// then unwrap the json map and turn it into an object
		return null;
	}

}
