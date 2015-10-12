package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.ttu.util.Log;

public class ParamsHelper {
	
	// Will parse URI parameters and map them
	
	public Map<String, List<String>> parse(String uri) {
		Map<String, List<String>> params = new HashMap<>();
		
		// debug: Should be safe
		String[] splitParams = uri.split("&");
		
		for (String param: splitParams) {
			String[] nameValue = param.split("=");
			if (nameValue.length == 1) {
				Log.warn("Invalid URI parameter, " + nameValue);
				continue;
			}
			if ( params.containsKey( nameValue[0]) ) {
				params.get(nameValue[0]).add(nameValue[1]);
			} else {
				List<String> valueList = new ArrayList<>();
				valueList.add(nameValue[1]);
				params.put(nameValue[0], valueList);
			}
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
