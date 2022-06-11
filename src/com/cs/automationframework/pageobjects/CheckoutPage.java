package com.cs.automationframework.pageobjects;

import org.openqa.selenium.By;

public class CheckoutPage {
    public By nextPaymentButton = By.className("submit-shipping");
    public By groundShipping = By.xpath("//input[contains(@id, \"shippingMethod-001\")]");
    public By twoDayShipping = By.xpath("//input[contains(@id, \"shippingMethod-002\")]");
    public By overnightShipping = By.xpath("//input[contains(@id, \"shippingMethod-003\")]");
}
