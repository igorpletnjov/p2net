package ee.ttu.http.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.sun.net.httpserver.HttpExchange;

import ee.ttu.http.handlers.model.GetHandler;
import ee.ttu.util.Log;

// TODO Just a test/showcase class, do not use this TODO
// Will have to make SendGetHandler and SendPostHandler base sending classes + unique class methods
public class SendHandler extends GetHandler {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		super.handle(httpExchange);
		
		// Will have to use lower level java.net classes for this
		URLConnection connection = null;
		BufferedReader br = null;
		try {
			URL url = new URL("http://www.example.com");
			connection = url.openConnection();
			Log.info("Opened connection to " + connection.getURL());
			
			connection.setUseCaches(false);
			
			br = new BufferedReader( new InputStreamReader( connection.getInputStream() ));
			StringBuffer response = new StringBuffer();
			String line = null;
			while (true) {
				line = br.readLine();
				Log.debug("Got line : " + line);
				if (line != null)
					response.append(line);
				else break;
			}
			Log.info("Got response : " + response.toString());
			if (br != null) br.close();
			
			// Send the result of our GET to the initial request
			sendResponse( response.toString(), httpExchange );
		} catch (Exception ex) {
			// Always catch these kinds of exceptions
			
			Log.error(ex.getMessage());
			sendEmptyResponse(500, httpExchange);
		}
		
	}

}
