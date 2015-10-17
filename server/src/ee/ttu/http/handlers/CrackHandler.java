package ee.ttu.http.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.ServerMain;
import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.http.service.ResourceHolder;
import ee.ttu.http.service.TextReader;
import ee.ttu.http.service.LetterCombination;
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

		ResourceHolder.setId("laksfwe34");
		
		Map<String, String> parameters = new HashMap<>();
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
		    Thread.sleep(10000); //1000 = 1 sec. Testimise jaoks lühike aeg. Reaalsuses oleks pikem aeg.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

        
        //TEST: Kutsub kohe ise crackeri välja
        //Generate välja kutsutud! Muutujad on, str: 88, pos: 60, length: 0, toCrack: dd97813dd40be87559aaefed642c3fbb
        //MD5Cracker cracker = new MD5Cracker();
        // cracker.generate("98", 60, 0, "dd97813dd40be87559aaefed642c3fbb");
        //System.out.println("----------------------------- ");
        
		//TEST: Otsib teatud kombinatsiooni numbri 
		/*
    	List<String> list = new ArrayList<>();
        for (char c: "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray()) {
        	list.add(Character.toString(c));
        }
        
        LetterCombination test = new LetterCombination();
        String findWord = test.findWord(2, list, "ig");
        //System.out.println("Leitud sõna on: " + findWord);
        
        //TEST: Otsib kombinatsioonid vastavalt numbritele
        LetterCombination test2 = new LetterCombination();
        int nr = 10000;
        int nr2 = 100000;
		System.out.println("TEST: At " + nr + " is " + test2.generateCombinations(4, list, nr));
		
		LetterCombination test3 = new LetterCombination();
		System.out.println("TEST: At " + nr2 + " is " + test3.generateCombinations(4, list, nr2));
		*/
		
		//TODO hetkel leiame ise kõik vahemikud generateCombinationiga. Meie peaksime andma pigem numbrid ja iga arvuti kasutab
		//				ise seda letterCombinatsionsit, et leida enda vahemik. Seejärel vaatab, kas lahendus kuulub vahemikku, nt hetkel a - arN (1-5000)
		
		/* TODO - Sten
		 * Selle klassi käivitab peaarvuti, mis saadab tükke välja teistele arvutitele
		 * Tükkide välja saatmine erinevatele arvutitele toimub siin all
		 *	for loop käib läbi kõik kõik getReadyMachines()'id ja saadab neile POSTi
		 * Arvutite arv, mida me saame kasutada on: NetworkCache.getReadyMachines().size()
		 * checkObject.put paneb requestbody'sse hetkel meil kõik info json kujul - sinul on tõenäoliselt
		 * 				vaja teha mingisugune checkObject.put("range", tükk); loopi sisse (ja siis äkki sinna lükata ka see requestbody = checkObject.toString(); ?)
		 * 				Ma ei saanud 100% su lahendusest aru, kuidas sa neid vahemikke tükeldad,
		 * 				aga kui sa saad nt enne seda for loopi mingid vahemikud kätte ja array'sse pandud, siis saab for loopi ümber teha i++ variandiks, 
		 *				kus i võtab nii masina kui ka range tüki array'st (arvestades, et need on ühe pikad)?
		 *	Teine võimalus on teha see arvutus praeguse loopi sees ja talletada ja igakord kui uuesti loop läheb käima, siis võtab kuidagi järgmise tüki ja annab uuele masinale.
		 *
		 *	Kui sa mitu serverid ei taha käivitada, siis võid lisada ise siin ajutiselt arvuteid getReadyMachine listi
		 * 	nt NetworkCache.getAllMachines().add("localhost:2000");	
		 * Kasuta logimist või syso, et näha kas saadab jupid õigesti välja, kui on palju arvuteid
		 * 
		 * Edasi mine CheckMD5Handlerisse, sest seal tuleb need tükid vastu võtta
		 */

		//Send resourcereply to the original computer
		String requestbody;
        
        System.out.println("----------------------------- ");
		
		JSONObject checkObject = new JSONObject();
		checkObject.put("ip", NetworkCache.getServerIP());
		checkObject.put("port", String.valueOf( NetworkCache.getServerPort() ));
		checkObject.put("id", "sdfgsd45");
		checkObject.put("md5", ResourceHolder.getHashToCrack());
		
		requestbody = checkObject.toString();
		
		Log.info("RequestBody for checkmd5: " + requestbody);
		
		Map<String, String> requestheader = new HashMap<String, String>();
		requestheader.put("Content-Type", "application/json"); 
		for (String aadress : NetworkCache.getReadyMachines()){
			sendPOST(requestbody, requestheader, aadress + "/checkmd5");
		}
		

		//For testing purposes - shows info in html 
		StringBuffer response = new StringBuffer();
		response.append("<html><body> Hash: " + ResourceHolder.getHashToCrack() + " <br> Answer: " + ResourceHolder.getResultString() + "<br><br> Machines: ");
		for (int i = 0; i < NetworkCache.getAllMachines().size(); i++){
			response.append("<br>" + NetworkCache.getAllMachines().get(i));
		}
		response.append("</body></html>");
		sendResponse( response.toString(), httpExchange ); 
	}
}
