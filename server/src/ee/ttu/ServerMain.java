package ee.ttu;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

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
import ee.ttu.util.Log;

/*
 * autori(d):
 * 
 * Igor Pletnjov 135213IAPB 
 * Silver Saul, 120754IAPB
 * 
 */

public class ServerMain {
	public static void main(String[] args) throws Exception {
		
		HttpServer server = null;
		NetworkCache.setServerIP("localhost");
		NetworkCache.setServerPort(1215);
		
		try {
			if (args.length > 1)
				server = HttpServer.create(new InetSocketAddress( Integer.parseInt(args[0]) ), 0);
			else
				server = HttpServer.create(new InetSocketAddress( NetworkCache.getServerPort() ), 0);
		} catch (NumberFormatException nfex) {
			Log.error("Invalid port :" + args[0]);
			server = HttpServer.create(new InetSocketAddress( NetworkCache.getServerPort() ), 0);
		}
		
		Log.info("Started server on port " + server.getAddress().getPort());
		
		//Read in the machines
		TextReader file = new TextReader();
		file.readText("machines.txt");

		ResourceHolder.getContextList().add( server.createContext( "/index", new IndexHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/json", new JsonHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/send", new SendHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/resource", new ResourceHandler() ) ); //later sendPOST to reply (igor doesn't know lol)
		ResourceHolder.getContextList().add( server.createContext( "/resourcereply", new ResourceReplyHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/checkmd5", new CheckMD5Handler() ) ); //later sendPOST to answermd5
		ResourceHolder.getContextList().add( server.createContext( "/answermd5", new AnswerMD5Handler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/crack", new CrackHandler() ) ); //manually starts the entire process

		server.setExecutor(null);
		server.start();

	}
}
