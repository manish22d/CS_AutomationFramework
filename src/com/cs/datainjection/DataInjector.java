package com.cs.datainjection;

import com.force.api.ApiConfig;
import com.force.api.ForceApi;
import com.force.api.QueryResult;

class DataInjector{
	public void setApi() {
		// Instantiating api by setting username and pwd
		System.out.println("In getApi");
		ApiConfig ac = new ApiConfig();
		//TODO:: Replace null values with actual credentials
		String salesforceUsername = null;
		String salesforcePassword = null;
		String salesforcePasswordToken = null;
		ac.setUsername(salesforceUsername);
		
		ac.setPassword(salesforcePassword + salesforcePasswordToken);
		ac.setLoginEndpoint("https://login.salesforce.com/services/Soap/u/34.0/");
		ForceApi api = new ForceApi(ac);
	}
	
	public String createData(String type, Object sObject) {
		System.out.println(type + " being created :");
		ForceApi api = null;
		return api.createSObject(type, sObject);
	}	
}