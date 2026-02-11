package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FirstCart {
    private WebDriver driver;

    //    Below variable contains the web element locating checkout button in the cart page
    private By checkoutButton = By.id("checkout");

    public FirstCart(WebDriver driver){
        this.driver = driver;
    }

//Below method is created for clicking in the checkout button in the cart page
    public void clickCheckout(){
        driver.findElement(checkoutButton).click();
    }
}
