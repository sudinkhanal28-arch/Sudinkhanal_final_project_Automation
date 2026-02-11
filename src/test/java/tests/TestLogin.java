package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.Login;

/**
 * Login workflow tests for SauceDemo.
 *
 * Notes:
 * - Uses TestNG DataProvider to keep scenarios declarative.
 * - Separates "action" and "assert" steps for readability.
 */
public class TestLogin extends BaseTest {

    private static final String INVENTORY_URL = "https://www.saucedemo.com/inventory.html";
    private static final String LOCKED_OUT_ERROR = "Epic sadface: Sorry, this user has been locked out.";

    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][]{
                // username, password, expectSuccess, expectedError
                {"standard_user", "secret_sauce", true, null},
                {"locked_out_user", "secret_sauce", false, LOCKED_OUT_ERROR},
        };
    }

    @Test(dataProvider = "loginScenarios", description = "Validate login behavior for multiple user scenarios")
    public void loginScenarioTest(String username,
                                 String password,
                                 boolean expectSuccess,
                                 String expectedError) {
        Login login = new Login(driver);

        // Act
        login.loginToSystem(username, password);

        // Assert
        if (expectSuccess) {
            assertLandedOnInventory();
        } else {
            assertLoginError(login, expectedError);
        }
    }

    @Test(description = "Smoke: verify inventory page URL after a successful login", groups = {"smoke"})
    public void successfulLoginNavigatesToInventory() {
        Login login = new Login(driver);
        login.loginToSystem("standard_user", "secret_sauce");
        assertLandedOnInventory();
    }

    @Test(description = "Negative: locked out user should see the proper error", groups = {"negative"})
    public void lockedOutUserSeesErrorMessage() {
        Login login = new Login(driver);
        login.loginToSystem("locked_out_user", "secret_sauce");
        assertLoginError(login, LOCKED_OUT_ERROR);
    }

    private void assertLandedOnInventory() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, INVENTORY_URL, "User should be redirected to the inventory page after login");
    }

    private void assertLoginError(Login login, String expectedError) {
        String actualError = login.getLockedOutUserErrorMessage();
        Assert.assertEquals(actualError, expectedError, "Error message should match expected text");
    }
}
