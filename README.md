# CS_AutomationFramework-Java-Selenium

Welcome to the Codescience Java/Selenium Test Automation Framework Template.

It is intended to be a reusable Codebase for future Quality Engineers to jump start their QE efforts on projects. Please add your improvements by sharing a pull request in the #automated-testing Slack channel. 

## Installation	
	
1. Install Java https://java.com/en/download (windows/mac)
2. Install Maven https://www.baeldung.com/install-maven-on-windows-linux-mac (windows/mac)
3. Clone this repo
4. Download dependencies (run this command after each change to dependencies pom.xml) From project root: 
	
		mvn dependency:go-offline 		

5. Allow system permission to WebDriver (run this once after initial clone - only required for linux/mac) From project root:

		chmod +x resource/webdrivers/chromedriver/mac/chromedriver
		
		
## Run

Run all tests

	mvn test -DJWT_CLIENT_SECRET=literalId -DSYSADMIN_USERNAME=literlUsername -DJWT_INSTANCE_URL=literalUrl -DPERSONA_1=literalUsername -DJWT_AUTH_PATH_TO_SERVER_KEY=literalFilepath
	
-
## Authentication
The framework uses SFDX for authentication. Different parameters are passed for authentication to different types of orgs.

### Parameters
Follow the steps here sforce.co/3ETKPQG to obtain the `JWT_CLIENT_SECRET` value (referred to as "consumer key" in this doc). 

The `SYSADMIN_USERNAME` is the username of any sys admin user in the org. This is needed for running sfdx commands.

The `JWT_INSTANCE_URL` is the base url of the org (which can be copied from the browser's address bar).

Create as many test user personas in the org as needed for testing. You can pass the usernames as env variables by appending them to the command like `-DPERSONA_1=literalUsername` as above.

The `JWT_AUTH_PATH_TO_SERVER_KEY` value is the filepath to the server.key file (which is also generated following these steps sforce.co/3ETKPQG).

---
When adding new parameters, dont forget to create environment variables in circleci > projects > project > project settings > environment variables. 
And also update the "run tests" command in config.yaml

---
Each new test user require a one-time manual authentication with the connected app, one time:

    sfdx force:auth:web:login -i <consumer key output from connected app creation in devhub>

This command will then prompt for the "consumer secret" output from org UI on connected app creation. This value can be retrieved from the org only once, so store it accordingly. 

---


In the server.key file (generated in steps sforce.co/3ETKPQG) replace all newlines with escaped `\n` and paste the resulting file contents into a CircleCI environment variable `JWT_SERVER_KEY`

