package ee.ttu.http.service;

import java.util.HashMap;
import java.util.Map;

import ee.ttu.util.Log;

public class ParamsHelper {
	
	// Will parse URI parameters and map them
	
	public Map<String, String> parse(String uri) {
		Map<String, String> params = new HashMap<>();
		
		// debug: Should be safe
		String[] splitParams = uri.split("&");
		
		for (String param: splitParams) {
			String[] nameValue = param.split("=");
			if (nameValue.length == 1) {
				Log.warn("Invalid URI parameter, " + nameValue);
				continue;
			}
			params.put(nameValue[0], nameValue[1]);
		}

		Log.debug("Got params " + params.toString());
		return params;
	}
	
	public String create( Map<String, String> params ) {
		StringBuffer paramString = new StringBuffer("/?");
		for ( String name: params.keySet() ) {
			paramString.append("&" + name +  "=" + params.get(name) );
		}
		
		return paramString.toString();
	}
	

}
