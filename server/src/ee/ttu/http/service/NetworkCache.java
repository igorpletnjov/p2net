package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class NetworkCache {
	static HashSet<String> readyMachines = new HashSet<>();
	static List<String> allMachines = new ArrayList<>(); 
	
	static List< Map<String, String> > pendingResourceReplies = new ArrayList<>();
	
	public static List< Map<String, String>> getPendingResourceReplies() {
		return pendingResourceReplies;
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
