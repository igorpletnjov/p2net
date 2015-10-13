package ee.ttu.http.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import ee.ttu.http.handlers.model.PostHandler;

//Rehkenduspäringu saatmine
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
  			   
			ip: rehkenduspäringu saatja ip
			port: rehkenduspäringu saatja port
			id: rehkenduspäringu id
			md5: hash stringina
			ranges: list stringidest, kus võivad olla wildcardid ?
			wildcard: optsionaalne: kui olemas, siis sümbol, mida wildcardina ? asemel kasutatakse
			symbolrange: optsionaalne: list baidivahemike paaridest, mida wildcardi asemel katsetada.
		 */
		
	}
}
