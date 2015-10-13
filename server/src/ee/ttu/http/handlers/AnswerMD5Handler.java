package ee.ttu.http.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.PostHandler;

//Rehkenduse tulemuse tagastamine algsele päringu saatjale
public class AnswerMD5Handler extends PostHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		// siin polegi suurt midagi teha ju lol
		
		/*
			{"ip": "55.66.77.88", 
				"port": "6788",
				"id": "asasasas",
				"md5": "siinonmd5string",
				"result": 0,
				"resultstring": "sssasasc"
			}
			
			ip: tulemuse saatnud masina ip
			port: tulemuse saatnud masina port
			id: esialgse rehkenduspäringu id
			md5: bruteforcetav hash stringina
			result: mis sai (leidsin stringi: 0, ei leidnud stringi: 1, ei jõudnud rehkendada: 2)
			resultstring: leitud string (kui on), mille md5 hash ongi bruteforcetav hash					
		 */
	}
}
