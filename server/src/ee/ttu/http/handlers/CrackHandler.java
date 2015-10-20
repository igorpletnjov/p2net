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
	public static int tempRange;
	public static int range;
	public static int i;
	public static int j;
	public static String answerTest;
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
		    Thread.sleep(1); //1000 = 1 sec. Testimise jaoks lühike aeg. Reaalsuses oleks pikem aeg.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
			//MD5Cracker cracker = new MD5Cracker();
			/*for(int i = 0; i < NetworkCache.getReadyMachines().size(); i++) {
				while(MD5Cracker.range > 0) {
					MD5Cracker.j = 62 - MD5Cracker.range;
					if(MD5Cracker.range - MD5Cracker.tempRange > 0 && MD5Cracker.range - (2 * MD5Cracker.tempRange) > 0 ) {
						MD5Cracker.tempRange = MD5Cracker.range;
					}
					MD5Cracker.range = MD5Cracker.range - MD5Cracker.tempRange;
				}
				
			}*/
			MD5Cracker cracker = new MD5Cracker();
			cracker.generate("", 60, 0, ResourceHolder.getHashToCrack());
			MD5Cracker.range();
		
		
		
		
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
		int gg = MD5Cracker.tempRange;
		String tempLen = MD5Cracker.tempSet.charAt(MD5Cracker.j) + "-" + MD5Cracker.tempSet.charAt(MD5Cracker.tempRange-1);
        System.out.println("----------------------------- ");
        //Log.debug("suurus:" + NetworkCache.getReadyMachines().size());
        //Log.debug("suurus:" + tempRange);
		JSONObject checkObject = new JSONObject();
		checkObject.put("ip", NetworkCache.getServerIP());
		checkObject.put("port", String.valueOf( NetworkCache.getServerPort() ));
		checkObject.put("id", "sdfgsd45");
		checkObject.put("md5", ResourceHolder.getHashToCrack());
		checkObject.put("range", tempLen);
		
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
