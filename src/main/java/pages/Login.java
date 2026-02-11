package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By userNameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");

    // Stable locator for SauceDemo error banner
    private final By errorBanner = By.cssSelector("h3[data-test='error']");

    // Target URL after successful login
    private static final String INVENTORY_URL = "https://www.saucedemo.com/inventory.html";

    public Login(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    /** Clears and types username safely */
    public Login enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField)).clear();
        driver.findElement(userNameField).sendKeys(username);
        return this;
    }

    /** Clears and types password safely */
    public Login enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).clear();
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    /** Clicks login */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    /**
     * Login action used by tests.
     * Keeps the original signature so other tests won't break.
     */
    public void loginToSystem(String userNameValue, String passwordValue) {
        enterUsername(userNameValue);
        enterPassword(passwordValue);
        clickLogin();
    }

    /**
     * Generic error message getter (works for any invalid login scenario).
     * Returns null if no error banner appears within the wait window.
     */
    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorBanner)).getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    /**
     * Backward-compatible method name.
     * Delegates to generic error getter.
     */
    public String getLockedOutUserErrorMessage() {
        return getErrorMessage();
    }

    /** Convenience: wait until login succeeds (inventory URL). */
    public boolean waitForSuccessfulLogin() {
        try {
            return wait.until(ExpectedConditions.urlToBe(INVENTORY_URL));
        } catch (TimeoutException e) {
            return false;
        }
    }
}