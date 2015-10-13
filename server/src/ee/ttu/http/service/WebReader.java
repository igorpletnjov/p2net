package ee.ttu.http.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.BaseHandler;
import ee.ttu.util.Log;

public class WebReader extends BaseHandler {
		
		public void reader(String website) throws IOException{
			URLConnection connection = null;
			BufferedReader br = null;
			URL url = new URL(website);
			connection = url.openConnection();
			Log.info("Opened connection to " + connection.getURL());
				
			connection.setUseCaches(false);
				
			br = new BufferedReader( new InputStreamReader( connection.getInputStream() ));
			String line = br.readLine();

			Log.info("Got response : " + line);
			if (br != null) br.close();
			
			line =  line.replace("[", "").replace("]", "").replace("\"", ""); //Remove unnecessary stuff
		      
		      String[] machinesSplit = line.split(",");

		      if (line!= ""){
		      	 
			        	//Add together the ip and port
		      		for (int i = 0; i < machinesSplit.length; i = i+2){
		      				NetworkCache.knownMachines.add(machinesSplit[i] + ":" + machinesSplit[i+1]); 
		      		 }
			        
		      }
		      Log.info("List of machines: " + NetworkCache.knownMachines);
		}
}
