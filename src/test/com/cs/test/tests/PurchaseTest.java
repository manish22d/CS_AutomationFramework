package test.com.cs.test.tests;

import com.cs.automationframework.listeners.AnnotationTransformer;
import com.cs.automationframework.pageobjects.*;
import com.cs.automationframework.pages.HomePage;
import com.cs.automationframework.utilities.RunParameters;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


public class PurchaseTest extends BaseTest {
    HeaderPage headerPage;
    LandingPopup popup;
    LoginPage loginPage;
    TiePage tiePage;
    SolidSilkTiePage solidSilkTiePage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    PlaceOrderPage placeOrderPage;


    public PurchaseTest() {
        headerPage = new HeaderPage();
        popup = new LandingPopup();
        loginPage =  new LoginPage();
        tiePage = new TiePage();
        solidSilkTiePage = new SolidSilkTiePage();
        checkoutPage = new CheckoutPage();
        paymentPage = new PaymentPage();
        placeOrderPage = new PlaceOrderPage();
    }


//    @Test
//    public void testPurchase() throws InterruptedException {
//        util.get(loginUrl);
//        util.click(popup.noButton);
//        util.sendKeys(loginPage.usernameField, testUser);
//        util.sendKeys(loginPage.passwordField, testPass);
//        util.click(loginPage.loginButton);
//        Thread.sleep(1000);
//        util.get(tiePageUrl);
//        util.click(tiePage.firstTie);
//        util.click(solidSilkTiePage.navyTie);
//        util.click(solidSilkTiePage.addToCart);
//        util.get(checkoutUrl);
//        util.click(checkoutPage.nextPaymentButton);
//        util.sendKeys(paymentPage.cvvField, "123");
//        util.click(paymentPage.placeOrderButton);
//        util.click(placeOrderPage.placeOrder);
//
//        Thread.sleep(4000);
//
//    }

    @Test
    public void verifyDifferentAddressForDifferentProduct() throws InterruptedException {
        System.out.println(driver);
        homePage = new HomePage(driver);
        loginpage = homePage.closeModal().navigateToLogin();
        homePage = loginpage.performLogin(RunParameters.APP_USER, RunParameters.APP_PWD);
        Thread.sleep(5000);
        for (int i = 1; i <= 3; i++) {
            productListPage = homePage.navigateToMens().navigateToAccessories().navigateToGloves();
            productDetailsPage = productListPage.viewNthProduct(i);
            productDetailsPage
                    .selectColor()
                    .selectSize()
                    .addToCart();
        }
        cartPage = productDetailsPage.navigateToCartPage();
        cartPage.checkOut().selectDifferentShippingAddress().submitShipping().updateCVV("123").submitPayment().placeOrder();
        System.out.println("Order Number is : " + cartPage.getOrderNumber());
    }
}
