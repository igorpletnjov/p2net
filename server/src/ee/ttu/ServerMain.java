package ee.ttu;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import ee.ttu.http.handlers.IndexHandler;
import ee.ttu.http.handlers.JsonHandler;
import ee.ttu.http.handlers.SendHandler;
import ee.ttu.http.service.ResourceHolder;

/*
 * autori(d):
 * 
 * Igor Pletnjov 135213IAPB 
 * 
 */

public class ServerMain {

	public static void main(String[] args) throws Exception {
		HttpServer server = null;
			
		try {
			if (args.length > 1)
				server = HttpServer.create(new InetSocketAddress( Integer.parseInt(args[0]) ), 0);
			else
				server = HttpServer.create(new InetSocketAddress( 1215 ), 0);
		} catch (NumberFormatException nfex) {
			System.err.println("Invalid port :" + args[0]);
			server = HttpServer.create(new InetSocketAddress( 1215 ), 0);
		}
		
		System.out.println("Started server on port " + server.getAddress().getPort());
		
		ResourceHolder.getContextList().add( server.createContext( "/index", new IndexHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/json", new JsonHandler() ) );
		ResourceHolder.getContextList().add( server.createContext( "/send", new SendHandler() ) );
		
		server.setExecutor(null);
		server.start();

	}

}
