package com.cs.automationframework.pages;

import com.cs.automationframework.constants.Constants;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "login-form-email")
    WebElement userIdTextbox;

    @FindBy(id = "login-form-password")
    WebElement pwdTextbox;

    @FindBy(xpath = "//button[contains(.,'Login')]")
    WebElement logOnButton;

    public LoginPage(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        PageFactory.initElements(driver, this);
    }


    public HomePage performLogin(String userId, String pwd){
        wait.until(elementToBeClickable(userIdTextbox));
        userIdTextbox.sendKeys(userId);
        pwdTextbox.sendKeys(pwd);
        logOnButton.click();
        return new HomePage(driver);
    }
}
