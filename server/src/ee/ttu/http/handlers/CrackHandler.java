package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.ServerMain;
import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.http.service.ResourceHolder;
import ee.ttu.http.service.TextReader;
import ee.ttu.http.service.letterCombinations;
import ee.ttu.md5.MD5Cracker;
import ee.ttu.util.Log;

//This class starts everything
public class CrackHandler extends GetHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		//example http://11.22.33.44:2345/crack?md5=dd97813dd40be87559aaefed642c3fbb
		//example2 http://localhost:1215/crack?md5=4a8a08f09d37b73795649038408b5f33
		
		String uri = httpExchange.getRequestURI().getQuery().toString();
		
		Log.debug("URI: " + uri);
		
		String[] split =  uri.split("=");
		ResourceHolder.setHashToCrack(split[1]);
		
		Log.debug("Hash: " + ResourceHolder.getHashToCrack());
		
		//Old way: arvutame kohe. Correct way: jagame tükkideks laiali, allpool
		//String result = MD5Cracker.calculator(hashToCrack); 

		ResourceHolder.setId("laksfwe34");
		
		Map<String, String> parameters = new HashMap();
		parameters.put("sendip", NetworkCache.getServerIP());
		parameters.put("sendport", String.valueOf(NetworkCache.getServerPort()));
		parameters.put("ttl", "5");
		parameters.put("id", ResourceHolder.getId());
		parameters.put("noask", NetworkCache.getServerIP() + "_" + String.valueOf(NetworkCache.getServerPort()));
		
		//http://11.22.33.44:2345/resource?sendip=55.66.77.88&sendport=6788&ttl=5&id=wqeqwe23&noask=11.22.33.44_345&noask=111.222.333.444_223
		for (String machine : NetworkCache.getAllMachines()){
			sendGET(parameters, machine + "/resource");
		}
		//Pro-tip: remove machines from machines.txt to test
		
		/* "N sekundit peale ressursipäringu tegemist (N sea algul näiteks paari sekundi peale) 
		 * jaotab ressursipäringu tegija ülesande talle OK vastanud masinate vahel ära. 
		 * Edaspidiseid OK teateid ta ignoreerib (elegantsem oleks vastata, et ei kasuta).
		 * Seejärel saadab ta kõigile OK vastanud masinatele ülesandesõnumi, 
		 * mis sisaldab md5 hash stringi ja vahemikku katsetatavast sõnalistist."
		 */

		//Waiting
		try {
		    Thread.sleep(2000); //1000 = 1 sec. Testimise jaoks lühike aeg. Reaalsuses oleks pikem aeg.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//Send resourcereply to the original computer
		String requestbody;
		ResourceHolder.setId("fsafasrwe");
	
		
		//TODO MAKE PROPER REQUEST
		//Total combinations (5 char): 931151402 - gotta divide it by the nr of computers
		//Siia tuleb 931151402 jagatis nt 931151402  : 3 = 232787852 IMPORTANT, komakohad tuleb õigseti panna
		//TODO Ümardamine korrektselt
		//esimene arvuti 0-310383800, teine 310383800 - (310383800*2) ja kolmas (310383800*2) - (310383800*3)
		//kasutame letterCombinatsionsit leidmiseks
		
		List<String> list = new ArrayList<>();
        for (char c: "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray()) {
        	list.add(Character.toString(c));
        }
		
        letterCombinations test = new letterCombinations();
        letterCombinations test2 = new letterCombinations();
		System.out.println("WORD IS " + test.generateCombinations(4, list, 1));
		
		System.out.println("WORD IS " + test2.generateCombinations(4, list, 5000));
		//TODO hetkel leiame ise kõik vahemikud generateCombinationiga. Meie peaksime andma pigem numbrid ja iga arvuti kasutab
		//				ise seda letterCombinatsionsit, et leida enda vahemik. Seejärel vaatab, kas lahendus kuulub vahemikku - nt hetkel a - arN (1-5000)
		
		requestbody = "{\"ip\":\""  + NetworkCache.getServerIP() + "\",\"port\":\"" + NetworkCache.getServerPort()
			+ "\",\"id\":\"" + ResourceHolder.getId() + "\",\"md5\":" +ResourceHolder.getHashToCrack() + "\"m\"ranges\":" +  "}";
		
		Log.info("RequestBody for checkmd5: " + requestbody);
		
		Map<String, String> requestheader = new HashMap<String, String>();
		requestheader.put("Content-Type", "application/json"); 
		for (String aadress : NetworkCache.getReadyMachines()){
			sendPOST(requestbody, requestheader, aadress + "/checkmd5");
		}
		

		//For testing purposes
		StringBuffer response = new StringBuffer();
		response.append("<html><body> Hash: " + ResourceHolder.getHashToCrack() + " <br> Answer: " + "SIIA TULEB TULEMUS" + "<br><br> Machines: ");
		for (int i = 0; i < NetworkCache.getAllMachines().size(); i++){
			response.append("<br>" + NetworkCache.getAllMachines().get(i));
		}
		response.append("</body></html>");
		sendResponse( response.toString(), httpExchange ); 
	}
}
