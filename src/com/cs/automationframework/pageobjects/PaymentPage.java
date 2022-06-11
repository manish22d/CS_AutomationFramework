package com.cs.automationframework.pageobjects;

import org.openqa.selenium.By;

public class PaymentPage {
    public By cvvField = By.id("saved-payment-security-code");
    public By placeOrderButton = By.className("submit-payment");
}
