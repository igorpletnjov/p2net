package ee.ttu.http.handlers.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class BaseHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		System.err.println("DEBUG: Got new request: " + getRequestInfo(httpExchange) );
		System.err.println("DEBUG: Request headers: " + getRequestHeaders(httpExchange) );
		System.err.println("DEBUG: Request body: " + getRequestBody(httpExchange) );
	}
	
	// When encountering errors/exceptions
	public void sendEmptyResponse( int statusCode, HttpExchange httpExchange ) throws IOException {
		httpExchange.sendResponseHeaders( statusCode, 0 );
		DataOutputStream out = new DataOutputStream( httpExchange.getResponseBody() );
	    out.writeBytes("");
	    out.close();
	}
	
	// When everything is OK
	public void sendResponse( String response, HttpExchange httpExchange ) throws IOException {
		if ( !checkHeaderPresent("Content-Type", httpExchange ) )
			httpExchange.getResponseHeaders().add("Content-Type", "text/html");
		httpExchange.sendResponseHeaders( 200, response.length() );
		DataOutputStream out = new DataOutputStream( httpExchange.getResponseBody() );
	    out.writeBytes( response );
	    out.close();
	}
	
	public void sendJsonResponse( String response, HttpExchange httpExchange ) throws IOException {
		httpExchange.getResponseHeaders().add("Content-Type", "application/json");
		sendResponse(response, httpExchange);
	}
	
	String getRequestInfo( HttpExchange httpExchange ) {
		return httpExchange.getRequestMethod() + " " + httpExchange.getHttpContext().getPath();
	}
	
	String getRequestHeaders( HttpExchange httpExchange ) {
		Headers headers = httpExchange.getRequestHeaders();
		StringBuffer headerString = new StringBuffer();
		for (String key: headers.keySet())
			headerString.append( key + ":" + headers.get(key) + "\n" );
		
		return headerString.toString();
	}
	
	String getRequestBody( HttpExchange httpExchange ) throws IOException {
		StringBuffer body = new StringBuffer();
		BufferedReader br = new BufferedReader( new InputStreamReader( httpExchange.getRequestBody() ) );
		
		while (true) {
			String line = br.readLine();
			if (line != null)
				body.append( line + "\n");
			else
				break;
		}
		return body.toString();
	}
	
	// Checks if a certain header with a certain value is present
	public Boolean checkHeader(String name, String value, HttpExchange httpExchange) {
		Headers headers = httpExchange.getRequestHeaders();
		for ( String key: headers.keySet() )
			if ( key.equalsIgnoreCase(name) )
				for ( String headval: headers.get(key) )
					if ( headval.equalsIgnoreCase(value) )
						return true;
		return false;
	}
	
	public Boolean checkHeaderPresent(String name, HttpExchange httpExchange) {
		return httpExchange.getRequestHeaders().containsKey( name );
		
	}

}
