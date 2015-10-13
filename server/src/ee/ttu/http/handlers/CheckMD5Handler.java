package ee.ttu.http.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.PostHandler;

//Rehkendusp�ringu saatmine
public class CheckMD5Handler extends PostHandler{
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		
		
		/* 
		POST-itatud data:
			{"ip": "55.66.77.88", 
				"port": "6788",
   				"id": "siinonid",
     			 "md5": "siinonmd5string",
      			"ranges": ["ax?o?ssss","aa","ab","ac","ad"],
      			"wildcard": "?",
     			"symbolrange": [[3,10],[100,150]]
			}
  			   
			ip: rehkendusp�ringu saatja ip
			port: rehkendusp�ringu saatja port
			id: rehkendusp�ringu id
			md5: hash stringina
			ranges: list stringidest, kus v�ivad olla wildcardid ?
			wildcard: optsionaalne: kui olemas, siis s�mbol, mida wildcardina ? asemel kasutatakse
			symbolrange: optsionaalne: list baidivahemike paaridest, mida wildcardi asemel katsetada.
		 */
		
	}
}
