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
		
		/* "N sekundit peale ressursip�ringu tegemist (N sea algul n�iteks paari sekundi peale) 
		 * jaotab ressursip�ringu tegija �lesande talle OK vastanud masinate vahel �ra. 
		 * Edaspidiseid OK teateid ta ignoreerib (elegantsem oleks vastata, et ei kasuta).
		 * Seej�rel saadab ta k�igile OK vastanud masinatele �lesandes�numi, 
		 * mis sisaldab md5 hash stringi ja vahemikku katsetatavast s�nalistist."
		 */

		//Waiting
		try {
		    Thread.sleep(10000); //1000 = 1 sec. Testimise jaoks l�hike aeg. Reaalsuses oleks pikem aeg.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

        
        //TEST: Kutsub kohe ise crackeri v�lja
        //Generate v�lja kutsutud! Muutujad on, str: 88, pos: 60, length: 0, toCrack: dd97813dd40be87559aaefed642c3fbb
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
        //System.out.println("Leitud s�na on: " + findWord);
        
        //TEST: Otsib kombinatsioonid vastavalt numbritele
        LetterCombination test2 = new LetterCombination();
        int nr = 10000;
        int nr2 = 100000;
		System.out.println("TEST: At " + nr + " is " + test2.generateCombinations(4, list, nr));
		
		LetterCombination test3 = new LetterCombination();
		System.out.println("TEST: At " + nr2 + " is " + test3.generateCombinations(4, list, nr2));
		*/
		
		//TODO hetkel leiame ise k�ik vahemikud generateCombinationiga. Meie peaksime andma pigem numbrid ja iga arvuti kasutab
		//				ise seda letterCombinatsionsit, et leida enda vahemik. Seej�rel vaatab, kas lahendus kuulub vahemikku, nt hetkel a - arN (1-5000)
		
		/* TODO - Sten
		 * Selle klassi k�ivitab peaarvuti, mis saadab t�kke v�lja teistele arvutitele
		 * T�kkide v�lja saatmine erinevatele arvutitele toimub siin all
		 *	for loop k�ib l�bi k�ik k�ik getReadyMachines()'id ja saadab neile POSTi
		 * Arvutite arv, mida me saame kasutada on: NetworkCache.getReadyMachines().size()
		 * checkObject.put paneb requestbody'sse hetkel meil k�ik info json kujul - sinul on t�en�oliselt
		 * 				vaja teha mingisugune checkObject.put("range", t�kk); loopi sisse (ja siis �kki sinna l�kata ka see requestbody = checkObject.toString(); ?)
		 * 				Ma ei saanud 100% su lahendusest aru, kuidas sa neid vahemikke t�keldad,
		 * 				aga kui sa saad nt enne seda for loopi mingid vahemikud k�tte ja array'sse pandud, siis saab for loopi �mber teha i++ variandiks, 
		 *				kus i v�tab nii masina kui ka range t�ki array'st (arvestades, et need on �he pikad)?
		 *	Teine v�imalus on teha see arvutus praeguse loopi sees ja talletada ja igakord kui uuesti loop l�heb k�ima, siis v�tab kuidagi j�rgmise t�ki ja annab uuele masinale.
		 *
		 *	Kui sa mitu serverid ei taha k�ivitada, siis v�id lisada ise siin ajutiselt arvuteid getReadyMachine listi
		 * 	nt NetworkCache.getAllMachines().add("localhost:2000");	
		 * Kasuta logimist v�i syso, et n�ha kas saadab jupid �igesti v�lja, kui on palju arvuteid
		 * 
		 * Edasi mine CheckMD5Handlerisse, sest seal tuleb need t�kid vastu v�tta
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
