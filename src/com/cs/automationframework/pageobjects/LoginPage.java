package com.cs.automationframework.pageobjects;

import org.openqa.selenium.By;
import com.cs.automationframework.utilities.Utilities;
import com.cs.automationframework.pageobjects.*;

public class LoginPage {
    protected Utilities util;
    public static By usernameField = By.id("login-form-email");
    public static By passwordField = By.id("login-form-password");
    public static By loginButton = By.xpath("//button[text()='Login']");



}
