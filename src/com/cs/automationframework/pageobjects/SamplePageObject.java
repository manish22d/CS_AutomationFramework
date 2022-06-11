package com.cs.automationframework.pageobjects;

import com.cs.automationframework.utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SamplePageObject {
	
	Utilities util;
	public SamplePageObject(WebDriver driver){
		util = new Utilities(driver);
	}
	
	public static By SEARCH_FIELD = By.cssSelector(".slds-input");
	
	public void enterSearch(String input){
		System.out.println("sending keys: " + input);

		util.sendKeys(SEARCH_FIELD, input);
	}
}
