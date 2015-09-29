package ee.ttu.http.handlers.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ee.ttu.http.service.ParamsHelper;
import ee.ttu.util.Log;

public abstract class BaseHandler implements HttpHandler {
	
	// TODO This class should not be extended by non-model (abstract class) handlers TODO
	
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
	
	// Sending to other destinations
	
	public String sendGET( Map<String, String> requestParams, String URL ) {
		return send("GET", null, requestParams, null, URL);
	}
	
	public String sendPOST( String requestBody, Map<String, String> requestHeaders, String URL ) {
		return send("POST", requestBody, null, requestHeaders, URL);
	}
	
	String send(String requestMethod, String requestBody, Map<String, String> requestParams, Map<String, String> requestHeaders, String plainURL ) {
		URLConnection connection = null;
		BufferedReader br = null;
		
		try {
			if ( requestParams != null ) {
				ParamsHelper paramsHelper = new ParamsHelper();
				plainURL = ( plainURL += paramsHelper.create( requestParams ) );
				Log.debug("final URL -> " + plainURL);
			}
			
			URL url = new URL( plainURL );
			connection = url.openConnection();
			
			if ( "POST".equals( requestMethod ) ) // Makes the request a POST, otherwise it's a GET
				connection.setDoOutput( true );
			
			Log.info( "Opened connection to " + connection.getURL() );
			
			if ( requestHeaders != null ) {
				for ( String name: requestHeaders.keySet() ) {
					connection.setRequestProperty( name , requestHeaders.get( name ) );
				}
			}
			
			if ( requestBody != null ) {
				try ( OutputStream output = connection.getOutputStream() ) {
				    output.write( requestBody.getBytes() );
				    Log.debug("Successfully wrote request body to output");
				}
			}
			
			connection.setUseCaches(false);
			//connection.connect();
			
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
			Log.info( "Got response : " + response.toString() );
			if (br != null) br.close();
			
			// Everything went well, got a response
			return response.toString();
		} catch (Exception ex) {
			// Something went wrong, we probably don't have a response to return
			
			Log.error( ex.getMessage() );
			return null;
		}
	}

}
