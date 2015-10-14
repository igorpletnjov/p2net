package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpContext;

public class ResourceHolder {
		
	static List<HttpContext> contextList = new ArrayList<HttpContext>();
	
	static String id;
	static String hashToCrack;
	static String resultString;
	
	public static String getResultString() {
		return resultString;
	}

	public static void setResultString(String resultString) {
		ResourceHolder.resultString = resultString;
	}

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
