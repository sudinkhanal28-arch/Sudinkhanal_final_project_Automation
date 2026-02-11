package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.FirstCart;
import pages.FirstInventory;
import pages.Login;
import pages.PageCheckOut;

public class TestCheckOut extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginAndReachCheckout() {
        Login login = new Login(driver);
        login.loginToSystem("standard_user", "secret_sauce");

        Assert.assertTrue(
                login.waitForSuccessfulLogin(),
                "Login failed or did not navigate to inventory page."
        );

        FirstInventory inventory = new FirstInventory(driver);
        inventory.addItemsToCart();
        inventory.goToCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart"),
                "User was not navigated to cart page."
        );

        FirstCart cart = new FirstCart(driver);
        cart.clickCheckout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one"),
                "User was not navigated to checkout info page (step one)."
        );
    }

    @Test(description = "Validate end-to-end checkout flow", groups = {"checkout", "smoke"})
    public void completeCheckoutTest() {
        PageCheckOut checkout = new PageCheckOut(driver);

        // Use explicit values (more realistic + deterministic)
        checkout.fillCheckoutInfo("Sudin", "Khanal", "44600");
        checkout.finishCheckout();

        Assert.assertEquals(
                checkout.getSuccessMessage().trim(),
                "Thank you for your order!",
                "Order success message did not match."
        );
    }
}