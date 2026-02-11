package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageCheckOut {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By zipCode = By.id("postal-code");
    private final By continueBtn = By.id("continue");
    private final By finishBtn = By.id("finish");
    private final By successMsg = By.className("complete-header");

    public PageCheckOut(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    // ✅ Advanced + reusable: parameterized info
    public void fillCheckoutInfo(String first, String last, String zip) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement firstEl = wait.until(ExpectedConditions.elementToBeClickable(firstName));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", firstEl);
        firstEl.clear();
        firstEl.sendKeys(first);

        WebElement lastEl = wait.until(ExpectedConditions.elementToBeClickable(lastName));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", lastEl);
        lastEl.clear();
        lastEl.sendKeys(last);

        WebElement zipEl = wait.until(ExpectedConditions.elementToBeClickable(zipCode));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", zipEl);
        zipEl.clear();
        zipEl.sendKeys(zip);

        WebElement cont = wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", cont);
        cont.click();
    }

    // ✅ Backward compatible: keeps old method working too
    public void fillCheckoutInfo() {
        fillCheckoutInfo("Sudin", "Khanal", "44600");
    }

    public void finishCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg)).getText();
    }
}
