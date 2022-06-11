package com.cs.automationframework.utilities;

public class RunParameters {

	// for JWT auth
	public static String JWT_CLIENT_SECRET = System.getProperty("JWT_CLIENT_SECRET");
	public static String JWT_AUTH_PATH_TO_SERVER_KEY = System.getProperty("JWT_AUTH_PATH_TO_SERVER_KEY");
	public static String JWT_INSTANCE_URL = System.getProperty("JWT_INSTANCE_URL");

	// for Command.class to access your sfdx CLI (needed for Windows only) provide the path to your sdfx installation (bin folder) without spaces
	public static String PATH_TO_SFDX_INSTALLATION;

	// username for running sfdx commands
	public static String SYSADMIN_USERNAME = System.getProperty("SYSADMIN_USERNAME");

	public static String APP_URL = System.getProperty("APP_URL") != null ?System.getProperty("APP_URL") :"https://zzxh-004.sandbox.us01.dx.commercecloud.salesforce.com/s/RefArch/home?lang=en_US";

	public static String APP_USER = System.getProperty("APP_USER")!= null ?System.getProperty("APP_USER") :"shoprunner@sandbox.com";

	public static String APP_PWD = System.getProperty("APP_PWD")!= null ?System.getProperty("APP_PWD") :"Universe-1";

	// test personas
	public static String PERSONA_1 = System.getProperty("PERSONA_1");






	// used for making logging easy to read because maven/surefire are very verbose!
	public static String logPrefix = "\n\n\n###############################\n\n";

	// validate and format passed parameters
//	static {
//
//		// output some useful info for using JWT auth
//		if(JWT_CLIENT_SECRET == null){
//			throw new IllegalArgumentException(logPrefix + "JWT_CLIENT_SECRET is not set. See the readme for instructions to obtain and set this value." + logPrefix);
//		}
//		if(JWT_INSTANCE_URL == null){
//			throw new IllegalArgumentException(logPrefix + "JWT_INSTANCE_URL is not set. See the readme for instructions to obtain and set this value." + logPrefix);
//		}
//
//		if(JWT_AUTH_PATH_TO_SERVER_KEY == null){
//			throw new IllegalArgumentException(logPrefix + "JWT_AUTH_PATH_TO_SERVER_KEY is not set. See the readme for instructions to obtain and set this value." + logPrefix);
//		}
//		else{
//			// defensively format the path, as needed for path value provided, and host OS
//			if(!JWT_AUTH_PATH_TO_SERVER_KEY.contains("server.key")){
//				if(String.valueOf(JWT_AUTH_PATH_TO_SERVER_KEY.charAt(JWT_AUTH_PATH_TO_SERVER_KEY.length())) != "/"){
//					JWT_AUTH_PATH_TO_SERVER_KEY = JWT_AUTH_PATH_TO_SERVER_KEY + "/";
//				}
//				JWT_AUTH_PATH_TO_SERVER_KEY = JWT_AUTH_PATH_TO_SERVER_KEY + "server.key";
//			}
//			if (System.getProperty("os.name").toLowerCase().contains("win")) {
//				JWT_AUTH_PATH_TO_SERVER_KEY = JWT_AUTH_PATH_TO_SERVER_KEY.replace("\\", "/");
//			}
//		}
//
//		// output some useful info for sfdx commands
//		if(SYSADMIN_USERNAME == null){
//			throw new IllegalArgumentException(logPrefix + "SYSADMIN_USERNAME is not set. See the readme for instructions to obtain and set this value." + logPrefix);
//		}


//	}










//	// used for SOAP auth
	public static String TESTSCIENCE_ORG_INSTANCE_URL = System.getProperty("TESTSCIENCE_ORG_INSTANCE_URL");
	public static String TESTSCIENCE_SYSADMIN_USERNAME = System.getProperty("TESTSCIENCE_SYSADMIN_USERNAME");
	public static String TESTSCIENCE_SYSADMIN_PASSWORD = System.getProperty("TESTSCIENCE_SYSADMIN_PASSWORD");
	public static String TESTSCIENCE_SYSADMIN_SECURITY_TOKEN = System.getProperty("TESTSCIENCE_SYSADMIN_SECURITY_TOKEN");
	public static String SOAP_API_AUTH_ENDPOINT = "https://login.salesforce.com/services/Soap/u/34.0/";




	
}