package ee.ttu.http.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ee.ttu.util.Log;

public class TextReader {
	public String TextReader(String fileName) {
	    String line;  // This will reference one line at a time
	    String text = "";

	    try {
	        // FileReader reads text files in the default encoding.
	        FileReader fileReader = 
	            new FileReader(fileName);

	        // Always wrap FileReader in BufferedReader.
	        BufferedReader bufferedReader = 
	            new BufferedReader(fileReader);

	        while((line = bufferedReader.readLine()) != null) {
	            text += line;
	        }   
	        
	        Log.info(fileName + ": " + text);
	        
	        text =  text.replace("[", "").replace("]", "").replace("\"", ""); //Remove unnecessary stuff
	        
	        String[] machinesSplit = text.split(",");
	        
	        NetworkCache network = new NetworkCache();
	        for (int i = 0; i < machinesSplit.length; i = i+2){
	        	network.allMachines.add(machinesSplit[i] + ":" + machinesSplit[i+1]);
	        }
	        
	        Log.info("Machine list: " + network.allMachines.toString());

	        bufferedReader.close(); // Always close files.
	    }
	    catch(FileNotFoundException ex) {
	        System.out.println(
	            "Unable to open file '" + fileName + "'");                
	    }
	    catch(IOException ex) {
	        System.out.println(
	            "Error reading file '" + fileName + "'");                  
	    }
		return text;
	}
}
