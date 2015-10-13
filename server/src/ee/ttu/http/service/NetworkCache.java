package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class NetworkCache {
	public static HashSet<String> readyMachines = new HashSet<>(); //Public?
	static List<String> allMachines = new ArrayList<>(); 
	static String serverIP;
	static int serverPort;
	
	public static String getServerIP() {
		return serverIP;
	}
	
	public static int getServerPort() {
		return serverPort;
	}
	
	public static void setServerIP(String newServerIP) {
		serverIP = newServerIP;
	}
	
	public static void setServerPort(int newServerPort) {
		serverPort = newServerPort;
	}

	public static HashSet<String> getReadyMachines() {
		return readyMachines;
	}
	public static List<String> getAllMachines() {
		return allMachines;
	}
	
	Boolean originalSender = false;
	// This is true only for the machine that started the entire process, and should be enabled only then
}
