package com.force.api.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HttpResponseValidator {
	
	private static Log log = LogFactory.getLog(HttpResponseValidator.class);
	
	public static int validateResponse(String link) {
		int code = -1;
		try {
			URL url = new URL(link);
			
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			code = connection.getResponseCode();
			log.info("The response code is : " + code);	
		} 
		catch (MalformedURLException e) {
			log.info(e.getMessage());
		
		} catch (IOException e) {
			log.info(e.getMessage());
		
		}
		return code;
	}
	
	public static int validateResponseRedirect(String link) throws InterruptedException {
		int code = -1;
        //StringBuffer result = new StringBuffer();
        try {
               URL url = new URL(link);               
               HttpURLConnection connection = (HttpURLConnection)url.openConnection();
               connection.setRequestMethod("GET");
               connection.setInstanceFollowRedirects(true);
               connection.connect();
               int responseCode = connection.getResponseCode();
               System.out.println( responseCode );
               String location = connection.getHeaderField( "Location" );
               System.out.println( location );
               Thread.sleep(5000);
               
               if (responseCode >= 300 && responseCode <= 307 && responseCode != 306 && responseCode != HttpURLConnection.HTTP_NOT_MODIFIED) {
            	   code = HttpResponseValidator.validateResponse(location);                   
               }       
               
               System.out.println(code);
               
              /* InputStream response = connection.getInputStream();
               BufferedReader reader = null;
               reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
               for (String line; (line = reader.readLine()) != null;) {
            	   result.append(line);
               }
               System.out.println("The response is : " + result);    */ 
        } 
        catch (MalformedURLException e) {
               log.info(e.getMessage());
        
        } catch (IOException e) {
               log.info(e.getMessage());
        
        }
        return code;
 }

}
