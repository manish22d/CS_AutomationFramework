package com.cs.datainjection;

import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.Connector;
import com.sforce.soap.metadata.CustomField;
import com.sforce.soap.metadata.CustomObject;
import com.sforce.soap.metadata.DeploymentStatus;
import com.sforce.soap.metadata.FieldSet;
import com.sforce.soap.metadata.FieldSetItem;
import com.sforce.soap.metadata.FieldType;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.SharingModel;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.force.api.ApiConfig;
import com.force.api.ForceApi;
import com.force.api.QueryResult;

public class SobjectCreation {
	String salesforceUsername = "";
	MetadataConnection metadataConnection;
	public String URL = "https://login.salesforce.com/services/Soap/c/35.0/";
	    ForceApi api;
	    public void loginMetaDataApi() throws ConnectionException {
	        ConnectorConfig partnerConfig = new ConnectorConfig();
	        ConnectorConfig metadataConfig = new ConnectorConfig();
	        partnerConfig.setUsername(salesforceUsername);
	        //TODO:: Insert Actual Values not Null
	        String salesforcePassword = null;
			String salesforcePasswordToken = null;
			partnerConfig.setPassword(salesforcePassword + salesforcePasswordToken);
	        PartnerConnection partnerConnection = com.sforce.soap.partner.Connector.newConnection(partnerConfig);
	        LoginResult result = partnerConnection.login(salesforceUsername, salesforcePassword + salesforcePasswordToken);
	        partnerConfig.setServiceEndpoint(result.getServerUrl());
	        metadataConfig.setServiceEndpoint(result.getMetadataServerUrl());
	        metadataConfig.setSessionId(partnerConnection.getSessionHeader().getSessionId());
	        metadataConnection = Connector.newConnection(metadataConfig);
	    }
	    public void createCustomObject() throws Exception {
	        // create a new custom object
	        String objectName = "Customer";
	        String displayName = "Customer";
	        CustomObject co = new CustomObject();
	        co.setFullName(objectName + "__c");
	        co.setDeploymentStatus(DeploymentStatus.Deployed);
	        co.setDescription("Created Object using the Metadata API");
	        co.setLabel(displayName);
	        co.setPluralLabel(displayName + "s");
	        co.setSharingModel(SharingModel.ReadWrite);
	        co.setEnableActivities(true);
	        // create the text id field
	        CustomField field = new CustomField();
	        field.setType(FieldType.Text);
	        field.setDescription("The custom object identifier field");
	        field.setLabel(displayName);
	        field.setFullName(objectName + "__c");
	        // add the field to the custom object
	        co.setNameField(field);
	        AsyncResult[] ars = metadataConnection.create(new CustomObject[] { co });
	        if (ars == null) {
	            System.out.println("The object was not created successfully");
	            return;
	        }
	        String createdObjectId = ars[0].getId();
	        String[] ids = new String[] { createdObjectId };
	        boolean done = false;
	        long waitTimeMilliSecs = 1000;
	        AsyncResult[] arsStatus = null;
	        /**
	         * After the create() call completes, we must poll the results of the
	         * checkStatus() call until it indicates that the create operation is
	         * completed.
	         */
	        while (!done) {
	            arsStatus = metadataConnection.checkStatus(ids);
	            if (arsStatus == null) {
	                System.out.println("The object status cannot be retrieved");
	                return;
	            }
	            done = arsStatus[0].isDone();
	            if (arsStatus[0].getStatusCode() != null) {
	                System.out.println("Error status code: " + arsStatus[0].getStatusCode());
	                System.out.println("Error message: " + arsStatus[0].getMessage());
	            }
	            Thread.sleep(waitTimeMilliSecs);
	            // double the wait time for the next iteration
	            waitTimeMilliSecs *= 2;
	            System.out.println("The object state is " + arsStatus[0].getState());
	        }
	        System.out.println("The ID for the created object is " + arsStatus[0].getId());
	    }
}
