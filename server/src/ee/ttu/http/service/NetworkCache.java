package ee.ttu.http.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NetworkCache {
	public static HashSet<String> getReadyMachines() {
		return readyMachines;
	}
	public static List<String> getAllMachines() {
		return allMachines;
	}
	static HashSet<String> readyMachines = new HashSet<>();
	static List<String> allMachines = new ArrayList<>(); 
}
