package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpContext;

public class ResourceHolder {
	
	// TODO maybe hold some sort of md5/p2net data in the future
	
	static List<HttpContext> contextList = new ArrayList<HttpContext>();
	
	static String id;
	static String hashToCrack;
	
	public static String getHashToCrack() {
		return hashToCrack;
	}

	public static void setHashToCrack(String hashToCrack) {
		ResourceHolder.hashToCrack = hashToCrack;
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		ResourceHolder.id = id;
	}

	public static List<HttpContext> getContextList() {
		return contextList;
	}

}
