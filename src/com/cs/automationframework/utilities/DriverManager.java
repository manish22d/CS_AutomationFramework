package com.cs.automationframework.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


public class DriverManager {
	

	private static WebDriver driver;
	
	public static WebDriver getDriver() {
        String callerClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if(!callerClass.contains("BaseTestClass")) {
        	String message = "\n\n ---------- \n\nCaller of DriverManager.getDriver() should be BaseTestClass\n(not " + callerClass + ").\n\nModify " + callerClass + " by replacing 'DriverManager.getDriver()' with 'getDriver' (assuming this class extends testBase).\n\n ---------- \n\n";
        	//throw new RuntimeException(message);
        }
         
		// only init driver if needed
		if((driver == null ) || ((RemoteWebDriver)driver).getSessionId() == null) {
			driver = startDriver();
		}
		return driver;
		
			

	}
	
	public static WebDriver startDriver() {

		// check for overrides from environment variables
		String override = System.getProperty("browser") + "";
		System.out.println("override: " + override);

		if(override.equalsIgnoreCase("fire")) {
			System.out.println("init firefox driver");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else {
			System.out.println("init chrome driver");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

		return driver;
		
	}
	
	public static void stopDriver() {
		try {
			driver.quit();
		}
		catch(Exception e) {
			System.out.println("could not quit driver: " + e.getMessage());
		}
	}

	
}
