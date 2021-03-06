package ee.ttu;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import ee.ttu.http.handlers.AnswerMD5Handler;
import ee.ttu.http.handlers.CheckMD5Handler;
import ee.ttu.http.handlers.CrackHandler;
import ee.ttu.http.handlers.IndexHandler;
import ee.ttu.http.handlers.JsonHandler;
import ee.ttu.http.handlers.ResourceHandler;
import ee.ttu.http.handlers.ResourceReplyHandler;
import ee.ttu.http.handlers.SendHandler;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.http.service.ResourceHolder;
import ee.ttu.http.service.TextReader;
import ee.ttu.http.service.WebReader;
import ee.ttu.util.Log;

/*
 * autori(d):
 * 
 * Igor Pletnjov 135213IAPB 
 * Silver Saul, 120754IAPB
 * Sten-Hendrik Pihlak, 134672IAPB
 */

public class ServerMain {
	
	final static int defaultPort = 1215;
	public static void main(String[] args) throws Exception {
		HttpServer server = null;
		NetworkCache.setServerIP("127.0.0.1"); // Hardcoded to test on single machine
		//For testing: remove machines.txt, activate 1216, 1217,1218, add machines.txt, activate 1215 - doesnt actually work :(
		
		try {
			if (args.length >= 1)
				server = HttpServer.create(new InetSocketAddress( Integer.parseInt(args[0]) ), 0);
			else
				server = HttpServer.create(new InetSocketAddress( defaultPort ), 0);
		} catch (NumberFormatException nfex) {
			Log.error("Invalid port ->" + args[0]);
			server = HttpServer.create(new InetSocketAddress( defaultPort ), 0);
		}
		
		Log.info("Started server on port " + server.getAddress().getPort());
		NetworkCache.setServerPort( server.getAddress().getPort() );
		
		//Read in the machines from txt
		TextReader file = new TextReader();
		file.readText("machines.txt");
		//Log.debug("size: "  + CrackHandler.tempRange);
		
		
		
		//Read in the machines from website
		WebReader web = new WebReader();
		web.reader("http://dijkstra.cs.ttu.ee/~Silver.Saul/P2MD5.txt");
			
		ResourceHolder.getContextList().add( server.createContext( "/index", new IndexHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/json", new JsonHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/send", new SendHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/resource", new ResourceHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/resourcereply", new ResourceReplyHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/checkmd5", new CheckMD5Handler() ) ); 
		ResourceHolder.getContextList().add( server.createContext( "/answermd5", new AnswerMD5Handler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/crack", new CrackHandler() ) ); 

		server.setExecutor( Executors.newCachedThreadPool() );
		server.start();

	}
}
