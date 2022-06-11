package com.cs.automationframework.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Utilities {

	public Utilities(WebDriver driver){
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
	}
	
	

	
	
	public WebDriver driver;
	
	public WebDriverWait wait;
	
	public Map<String, Long> loadTimes = new HashMap<String, Long>();
	
	public void sendKeys(By by, String input){
		WebElement element = findClickableElement(by);
		if(element != null) {
			element.sendKeys(input);
		}
		else {
			System.out.println("could not send keys '" + input + "' to element :" + by);
		}
	}
	
/**
 * searches the current driver context for the element until it is located, or until the Wait object's max timeout is reached
 * @param locator
 * @return
 */
public WebElement findElement(By locator){
	WebElement element = null;
	try{
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	catch(Exception e) {					
		System.out.println("\nunable to find element: " + locator);
	}
	return element;
}
	





	public WebElement findClickableElement(By by){
		
		WebElement element = null;
		try{
			element = wait.until(ExpectedConditions.elementToBeClickable(by));
		}
		catch(Exception e) {
			System.out.println("\nunable to find element: " + by);
		}
		
		return element;
		
	}


	public List<WebElement> findElements(By by){
		List<WebElement> elements = null;
		try{
			elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		}
		catch(Exception e) {
			System.out.println("\nunable to find elements: " + by);
		}
		return elements;
	}
	
	public void click(By by) {
		WebElement element;
		try{
			element = findClickableElement(by);
			click(element);
		}
		catch(Exception e) {
			System.out.println("\nelement was not clickable: " + by);
		}
		
	}
	
	public void click(WebElement element) {
		try {
			element.click();
		}
		catch(Exception e) {
			System.out.println("\nelement was not clickable: " + element);
			System.out.println("attempting JS click...");
			
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		}
	}
	
	public boolean isDisplayed(By by) {
		return (wait.until(ExpectedConditions.visibilityOfElementLocated(by)) != null);
	}

	public boolean isPresent(By by) {
		return (wait.until(ExpectedConditions.presenceOfElementLocated(by)) != null);
	}
	
	public boolean isHidden(By by) {
		
		long start = System.currentTimeMillis();
		boolean hidden = false;
		hidden = Boolean.TRUE.equals(wait.until(ExpectedConditions.invisibilityOfElementLocated(by)));
		Long waited = (System.currentTimeMillis() - start);
		//System.out.println("waited " + waited + " milliseconds for element [" + by + "] to hide");
		
		// capture chart name & load time
		String caller = Thread.currentThread().getStackTrace()[2].getMethodName();	
		if(caller.contains("LoadingTime")) {
			loadTimes.put(caller.replace("test", "").replace("ChartLoadingTime", ""), waited);
		}
		
		
		return hidden;
	}
	

	
	public void printLoadTimes() {
		System.out.println("");
		for (String key : loadTimes.keySet()) {
		    System.out.println(loadTimes.get(key) + ": " + key);
		}
		System.out.println("");

	}
	
	
	public String getText(By locator) {
		try {
			String text = findElement(locator).getText();
			System.out.println("getting text for " + locator + ": \n " + text);
			return text;
		}
		catch(Exception e) {
			return "error getting text for: " + locator;
		}
	}
	
	public String getText(WebElement element) {
		try {
			System.out.println("getting text for " + element);
			String text = element.getText();
			System.out.println(text);
			return text;
		}
		catch(Exception e) {
			return "error getting text for element: " + element;
		}
	}
	
	public List<String> getTexts(List<WebElement> elements){
		List<String> texts = new ArrayList();
		for(WebElement element: elements) {
			try {
				texts.add(element.getText());
			}
			catch(Exception e) {
				texts.add("error getting text for: " + element.toString());
			}
		}
		return texts;
	}
	
	public List<String> getTexts(By locator){
		List<WebElement> elements = findElements(locator);
		return getTexts(elements);
	}
	

	

	
	public boolean isElementPresent(By by) {
		   try {
		       driver.findElement(by);
		       return true;
		   } catch (NoSuchElementException e) {
		       return false;
		   }
		}
	
	public String selectDropdown(String fieldname, String value) {
		
		try {
				
			Select dropdown = new Select(driver.findElement(By.name(fieldname)));			
			dropdown.selectByVisibleText(value);
			
	       
		}
		catch(Exception e) {
			
			return "no value found in the drop down: " + value;
		}
		return null;
	}
	


	
	public String execute(String JS, WebElement element) {
		try {
			String retVal = ((JavascriptExecutor) driver).executeScript(JS, element).toString();
			
			// wait a few seconds because this is usually used for scrolling, so we need to wait for the animation to complete
			Thread.sleep(3333);
			return retVal;
		}
		catch(Exception e) {
			return "error executing JS";
		}

	}

	/**
	 * A convenience method so you can util.get() instead of util.driver.get()
	 * @param url		Url as a String
	 */
	public void get (String url){
		System.out.println("getting url: " + url);
		driver.get(url);
	}

	/**
	 * A convenience method so you can util.login() instead of util.get(SessionManager.login())
	 * @param user		user as an Enum
	 */
	public void login (String user){
		get(SessionManager.login(user));
	}


	
}
	

