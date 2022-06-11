package test.com.cs.test.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import com.cs.automationframework.listeners.AnnotationTransformer;
import com.cs.automationframework.pages.*;
import com.cs.automationframework.utilities.DriverManager;
import com.cs.automationframework.utilities.RunParameters;
import com.cs.automationframework.utilities.SessionManager;
import com.cs.automationframework.utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;


public class BaseTest {

	public WebDriver driver;
	protected Utilities util;
	protected final String baseUrl = "https://zzxh-004.sandbox.us01.dx.commercecloud.salesforce.com/on/demandware.store/Sites-RefArch-Site/en_US";
	protected final String loginUrl = baseUrl + "/Login-Show";
	protected final String tiePageUrl =  "https://zzxh-004.sandbox.us01.dx.commercecloud.salesforce.com/s/RefArch/mens/accessories/ties/?lang=en_US";
	protected final String checkoutUrl = "https://zzxh-004.sandbox.us01.dx.commercecloud.salesforce.com/on/demandware.store/Sites-RefArch-Site/en_US/Checkout-Begin?stage=shipping#shipping";
	protected final String testUser = "tsemre3@codescience.com";
	protected final String testPass = "Universe-1";

	public LoginPage loginpage;
	public HomePage homePage;
	public ProductListPage productListPage;
	public ProductDetailsPage productDetailsPage;
	public CartPage cartPage;

	public WebDriver getDriver() {
		return DriverManager.getDriver();
	}


	@BeforeSuite
	public void setup(){
		System.out.println("hello");
		driver=getDriver();

		driver.navigate().to(RunParameters.APP_URL);

		System.out.println(homePage);

	}

	@BeforeMethod
	public void setup(Method method) throws IOException {
//		util = new Utilities(getDriver());
//
//		// log the test name name
//		String fullName = this.getClass().getName() + "." + method.getName();
//		System.out.println("/n/nrunning test: " + fullName);
//
//		// init utilities
//		util = new Utilities(getDriver());
//
//		// authenticate to the org
//		//util.driver.get(SessionManager.getAuthenticatedURL(RunParameters.TESTSCIENCE_ORG_INSTANCE_URL, RunParameters.TESTSCIENCE_SYSADMIN_USERNAME, RunParameters.TESTSCIENCE_SYSADMIN_PASSWORD, RunParameters.TESTSCIENCE_SYSADMIN_SECURITY_TOKEN));



	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverManager.stopDriver();
	}


}
