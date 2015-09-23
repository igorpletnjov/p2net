package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpContext;

public class ResourceHolder {
	
	// TODO maybe hold some sort of md5/p2net data in the future
	
	static List<HttpContext> contextList = new ArrayList<HttpContext>();
	
	public static List<HttpContext> getContextList() {
		return contextList;
	}

}
