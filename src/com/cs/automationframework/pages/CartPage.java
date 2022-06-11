package com.cs.automationframework.pages;


import com.cs.automationframework.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "saved-payment-security-code")
    WebElement cvvTextBox;

    @FindBy(css = ".checkout-btn")
    WebElement checkoutBtn;

    @FindBy(css = ".submit-shipping")
    WebElement submitShippingBtn;

    @FindBy(css = ".submit-payment")
    WebElement submitPaymentBtn;

    @FindBy(css = ".place-order")
    WebElement placeOrderBtn;

    @FindBy(css = "span.order-number")
    WebElement orderNumber;

    @FindBy(css = "label.checkout-checkbox[for='multiShipCheck']")
    WebElement multiShippingAddress;

    @FindBy(css = ".multi-shipping .shipping-form")
    List<WebElement> products;

    public CartPage(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        PageFactory.initElements(driver, this);
    }

    public CartPage checkOut(){
        wait.until(elementToBeClickable(checkoutBtn));
        checkoutBtn.click();
        return this;
    }

    public CartPage submitShipping(){
        wait.until(elementToBeClickable(submitShippingBtn));
        submitShippingBtn.click();
        return this;
    }

    public CartPage updateCVV(String cvv){
        wait.until(elementToBeClickable(cvvTextBox));
        cvvTextBox.sendKeys(cvv);
        return this;
    }

    public CartPage submitPayment(){
        wait.until(elementToBeClickable(submitPaymentBtn));
        submitPaymentBtn.click();
        return this;
    }

    public CartPage placeOrder(){
        wait.until(elementToBeClickable(placeOrderBtn));
        placeOrderBtn.click();
        return this;
    }

    public String getOrderNumber(){
        wait.until(visibilityOf(orderNumber));
        return orderNumber.getText();
    }

    public CartPage selectDifferentShippingAddress(){
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(elementToBeClickable(multiShippingAddress));
        if(!multiShippingAddress.isSelected())
            multiShippingAddress.click();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(visibilityOfAllElements(products));
        products.forEach(product->{
            product.findElement(By.cssSelector(".btn-edit-multi-ship")).click();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            new Select( product.findElement(By.name("shipmentSelector"))).selectByIndex(3);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            product.findElement(By.cssSelector(".save-shipment")).click();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return this;
    }
}
