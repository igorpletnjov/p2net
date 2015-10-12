package ee.ttu.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.PostHandler;
import ee.ttu.http.service.NetworkCache;

//Ressursipäringu tulemuse tagastamine algsele pärijale
public class ResourceReplyHandler extends PostHandler {
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		/*
		POST'itatud data:
			{"ip": "55.66.77.88", 
   				"port": "6788",
   				"id": "asasasas",
   				"resource": 100 
   			}
   				
   			ip: tulemuse saatnud masina ip
			port: tulemuse saatnud masina port
			id: esialgse päringu id (kui oli algselt antud)
			resource: minu rehkendusvõimsus (keskmine masin on 100) seda ei pea täpsemalt hindama, võib alati öelda 100
		 */
		
	}
}
