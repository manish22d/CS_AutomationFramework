package com.cs.automationframework.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class SessionManager {

	private static class DxParams{
		String accessToken;
		String instanceUrl;
		String authenticatedUrl;
	}

	// once authenticated, store authenticated urls here, and refer to them instead of re-authenticating
	// to avoid sfdx org request limit error
	private static HashMap<String, DxParams> DX_PARAMS = new HashMap<String, DxParams>();


	/**
	 * Returns a "logged in" url - a url assembled with org instance url + access token
	 * @param persona
	 */
	public static String login(String persona) {
		return getauthenticatedUrl(persona);
	}

	/**
	 * Get the authenticatedUrl for the given user
	 *
	 * @return
	 */
	public static String getauthenticatedUrl(String username) {
		return getDxParams(username).authenticatedUrl;
	}

	/**
	 * Returns the stored DxParams for the given user, unless params have not yet been retrieved for this user - then gets params and stores them before returning
	 * @param username
	 * @return
	 */
	private static DxParams getDxParams(String username) {

		// if params have already been retrieved for this user, use those
		DxParams dxParams = DX_PARAMS.get(username);

		// if we dont have params for this user yet, get them
		if(dxParams == null){

			// log useful info for loggin in with test users
			if(username == null){
				throw new IllegalArgumentException(RunParameters.logPrefix + "The username is null. See the readme for instructions for adding and using test users." + RunParameters.logPrefix);
			}

			dxParams = new DxParams();

			// authenticate the user
			System.out.println("Authenticating via JWT: " + username);
			new Command().execute("sfdx force:auth:jwt:grant -u " + username + " -f " + RunParameters.JWT_AUTH_PATH_TO_SERVER_KEY + " -i " + RunParameters.JWT_CLIENT_SECRET);

			// get the access token & instance url
			String JSON = new Command().execute("sfdx force:org:display -u " + username + " --verbose --json");
			JsonObject jsonObject = new JsonParser().parse(JSON).getAsJsonObject();
			dxParams.accessToken = jsonObject.getAsJsonObject("result").get("accessToken").getAsString();
			dxParams.instanceUrl = jsonObject.getAsJsonObject("result").get("instanceUrl").getAsString();

			// get authenticatedUrl URL from open command
			JSON = new Command().execute("sfdx force:org:open --urlonly -u " + username + " --json");
			jsonObject = new JsonParser().parse(JSON).getAsJsonObject();
			dxParams.authenticatedUrl = jsonObject.getAsJsonObject("result").get("url").getAsString();
			DX_PARAMS.put(username, dxParams);

		}

		System.out.println("Got access token: " + dxParams.accessToken);
		System.out.println("Got instance url: " + dxParams.instanceUrl);
		System.out.println("Got authenticated url: " + dxParams.authenticatedUrl);

		return dxParams;

	}
	

	public static String getAuthenticatedURL(String baseUrl, String username, String userpass, String sectoken) throws MalformedURLException, IOException {
		return baseUrl + "/secur/frontdoor.jsp?sid=" + getSessionID(username, userpass, sectoken);
	}
	
	public static String getSessionID(String username, String userpass, String sectoken) throws MalformedURLException, IOException {
		String input = readInput(doOutput(username, userpass, sectoken));
		return input.replaceAll("^.*<sessionId>(.*)</sessionId>.*$","$1").trim();
	}
	
	public static String getMetadataServerUrl(String username, String userpass, String sectoken) throws MalformedURLException, IOException {
		String input = readInput(doOutput(username, userpass, sectoken));
		return input.replaceAll("^.*<metadataServerUrl>(.*)</metadataServerUrl>.*$","$1").trim();
	}
	
	public static String getSOAPServerUrl(String username, String userpass, String sectoken) throws MalformedURLException, IOException {
		String input = readInput(doOutput(username, userpass, sectoken));
		String soap = input.replaceAll("^.*<serverUrl>(.*)</serverUrl>.*$","$1").trim();
	    System.out.println("soap: " + soap);

		return soap;
	}
	
	private static String readInput(HttpURLConnection conn) throws IOException {
		InputStream in = conn.getInputStream();
		StringBuilder b = new StringBuilder();
		byte[] buf = new byte[2000];
		int n = 0;
		while((n=in.read(buf))!=-1) {
			b.append(new String(buf,0,n));
		}
		return b.toString();
	}
	
	private static HttpURLConnection doOutput(String username, String userpass, String sectoken) throws UnsupportedEncodingException, IOException {
		HttpURLConnection conn = getConnection();
		OutputStream out = conn.getOutputStream();
		out.write(getLoginRequestXML(username, userpass, sectoken));
		out.flush();
		return conn;
	}
	
	private static byte[] getLoginRequestXML(String username, String password, String securityToken) throws UnsupportedEncodingException {
	    System.out.println("logging in with: " + username + ", " + password);
		byte[] msg = new String(
				"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n"+
				"<env:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n"+
				"              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"+
				"              xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"+
				"    <env:Body>\n"+
		        "        <n1:login xmlns:n1=\"urn:partner.soap.sforce.com\">\n"+
		        "            <n1:username>"+username+"</n1:username>\n"+
		        "            <n1:password>"+password+securityToken+"</n1:password>\n"+
		        "        </n1:login>\n"+
		        "    </env:Body>\n"+
		        "</env:Envelope>\n").getBytes("UTF-8");
		return msg;
	}
	
	private static HttpURLConnection getConnection() throws MalformedURLException, IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(RunParameters.SOAP_API_AUTH_ENDPOINT).openConnection();
		conn.setDoOutput(true);
		conn.addRequestProperty("Content-Type", "text/xml");
		conn.addRequestProperty("SOAPAction", "login");
		return conn;
	}



	
}
