package com.cs.automationframework.pages;

import com.cs.automationframework.constants.Constants;
import com.cs.automationframework.pages.ProductDetailsPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

public class ProductListPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "div.product-tile div.pdp-link a")
    List<WebElement> products;

    public ProductListPage(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        PageFactory.initElements(driver, this);
    }

    public ProductDetailsPage openFirstProduct(){
        wait.until(visibilityOfAllElements(products));
        products.stream().findFirst().orElseThrow(null).click();
        return new ProductDetailsPage(driver);
    }

    public ProductDetailsPage viewNthProduct(int itemPosition){
        wait.until(visibilityOfAllElements(products));
        products.get(itemPosition-1).click();
        return new ProductDetailsPage(driver);
    }
}
