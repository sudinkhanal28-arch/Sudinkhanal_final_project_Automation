package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.FirstInventory;
import pages.Login;

public class TestInventory extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginAndOpenInventory() {
        Login login = new Login(driver);
        login.loginToSystem("standard_user", "secret_sauce");

        // Wait for inventory page navigation (stability)
        Assert.assertTrue(
                login.waitForSuccessfulLogin(),
                "Login did not navigate to inventory page. Cannot run inventory tests."
        );
    }

    @Test(priority = 1, groups = {"sorting"})
    public void verifyFilterByNameDescending() {
        FirstInventory inventory = new FirstInventory(driver);
        inventory.sortByNameDesc();

        Assert.assertTrue(
                inventory.isProductNameSortedDesc(),
                "Products are NOT sorted by Name (Z to A)"
        );
    }

    @Test(priority = 2, groups = {"sorting"})
    public void verifyFilterByPriceLowToHigh() {
        FirstInventory inventory = new FirstInventory(driver);
        inventory.sortByPriceLowToHigh();

        Assert.assertTrue(
                inventory.isProductPriceSortedLowToHigh(),
                "Products are NOT sorted by Price (Low to High)"
        );
    }

    @Test(priority = 3, groups = {"cart"})
    public void verifyAddItemsAndNavigateToCart() {
        FirstInventory inventory = new FirstInventory(driver);

        inventory.addItemsToCart();
        inventory.goToCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart.html"),
                "User was NOT navigated to cart page"
        );
    }
}