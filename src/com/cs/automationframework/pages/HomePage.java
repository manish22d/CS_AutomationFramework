package com.cs.automationframework.pages;

import com.cs.automationframework.constants.Constants;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    @FindBy(css = "div.user a")
    WebElement loginLink;

    @FindBy(id = "mens")
    WebElement mensNavLink;
    @FindBy(id = "mens-clothing")
    WebElement mensClothingNavLink;

    @FindBy(id = "mens-accessories")
    WebElement mensAccessoriesNavLink;
    @FindBy(id = "mens-clothing-suits")
    WebElement mensClothingSuitNavLink;

    @FindBy(id = "mens-accessories-ties")
    WebElement mensClothingTiesNavLink;

    @FindBy(id = "mens-accessories-gloves")
    WebElement mensClothingGlovesNavLink;
    public HomePage(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public LoginPage navigateToLogin(){
        wait.until(elementToBeClickable(loginLink));
        loginLink.click();
        return new LoginPage(driver);
    }
    public HomePage closeModal(){
        driver.findElement(By.cssSelector(".affirm")).click();
        return this;
    }

    public HomePage navigateToMens(){
        wait.until(visibilityOf(mensNavLink));
        actions.moveToElement(mensNavLink).build().perform();
        return this;
    }
    public HomePage navigateToClothing(){
        wait.until(visibilityOf(mensClothingNavLink));
        actions.moveToElement(mensClothingNavLink).build().perform();
        return this;
    }

    public HomePage navigateToAccessories(){
        wait.until(visibilityOf(mensAccessoriesNavLink));
        actions.moveToElement(mensAccessoriesNavLink).build().perform();
        return this;
    }
    public ProductListPage navigateToSuit(){
        wait.until(visibilityOf(mensClothingSuitNavLink));
        actions.click(mensClothingSuitNavLink).build().perform();
        return new ProductListPage(driver);
    }

    public ProductListPage navigateToTies(){
        wait.until(visibilityOf(mensClothingTiesNavLink));
        actions.click(mensClothingTiesNavLink).build().perform();
        return new ProductListPage(driver);
    }

    public ProductListPage navigateToGloves(){
        wait.until(visibilityOf(mensClothingGlovesNavLink));
        actions.click(mensClothingGlovesNavLink).build().perform();
        return new ProductListPage(driver);
    }
}

