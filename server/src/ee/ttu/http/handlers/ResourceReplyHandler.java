package ee.ttu.http.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.PostHandler;
import ee.ttu.http.service.NetworkCache;

//Ressursip�ringu tulemuse tagastamine algsele p�rijale
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
			id: esialgse p�ringu id (kui oli algselt antud)
			resource: minu rehkendusv�imsus (keskmine masin on 100) seda ei pea t�psemalt hindama, v�ib alati �elda 100
		 */
		
	}
}
