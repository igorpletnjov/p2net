package ee.ttu.http.service;

import java.util.HashMap;
import java.util.Map;

public class ParamsParser {
	
	// Will parse URI parameters and map them
	
	public Map<String, String> parse(String uri) {
		Map<String, String> params = new HashMap<>();
		
		// TODO not safe yet
		String[] splitParams = uri.split("?")[1].split("&");
		
		for (String param: splitParams) {
			String[] nameValue = param.split("=");
			if (nameValue.length == 1) {
				System.err.println("WARN: Invalid URI parameter, " + nameValue);
				continue;
			}
			
			params.put(nameValue[0], nameValue[1]);
		}

		System.err.println("DEBUG: Got params " + params.toString());
		return params;
	}
	

}
