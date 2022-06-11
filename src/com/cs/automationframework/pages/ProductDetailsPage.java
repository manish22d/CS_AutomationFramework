package com.cs.automationframework.pages;


import com.cs.automationframework.constants.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

public class ProductDetailsPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "button.color-attribute")
    List<WebElement> colors;

    @FindBy(id = "accessorySize-1")
    WebElement size;

    @FindBy(css = "button.add-to-cart")
    WebElement addToCartButton;

    @FindBy(css = "a.minicart-link")
    WebElement cartButton;

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        PageFactory.initElements(driver, this);
    }

    public ProductDetailsPage selectColor() throws InterruptedException {
        Thread.sleep(5000);
        if (!colors.isEmpty()) {
            wait.until(visibilityOfAllElements(colors));
            colors.stream().findAny().orElseThrow(null).click();
        }
        return this;
    }

    public ProductDetailsPage selectSize() {
        wait.until(visibilityOfAllElements(size));
        new Select(size).selectByIndex(1);
        return this;
    }

    public ProductDetailsPage addToCart() {
        wait.until(elementToBeClickable(addToCartButton));
        addToCartButton.click();
        return this;
    }

    public CartPage navigateToCartPage() {
        wait.until(elementToBeClickable(cartButton));
        cartButton.click();
        return new CartPage(driver);
    }

}
