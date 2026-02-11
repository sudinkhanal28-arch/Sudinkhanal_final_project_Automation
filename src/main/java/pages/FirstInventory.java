package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class FirstInventory {
    private WebDriver driver;
//    Below variable contains the web element locating dropdown icon in the inventory page
    private By filterDropdown = By.className("product_sort_container");

    //    Below variable contains the web element locating name of the products in the inventory page
    private By productNames = By.className("inventory_item_name");

    //    Below variable contains the web element locating prices of the products icon in the inventory page
    private By productPrices = By.className("inventory_item_price");

    //    Below variable contains the web element locating a specific product in the inventory page
    private By addBackpack = By.id("add-to-cart-sauce-labs-backpack");

    //    Below variable contains the web element locating a spefific product in the inventory page
    private By addBikeLight = By.id("add-to-cart-sauce-labs-bike-light");

    //    Below variable contains the web element locating cart icon in the inventory page
    private By cartIcon = By.className("shopping_cart_link");

    public FirstInventory(WebDriver driver){
        this.driver = driver;
    }

    //Below are the two method for performing actions of filter

//    Below method helps to sort product name in descending order
    public void sortByNameDesc(){
        new Select(driver.findElement(filterDropdown))
                .selectByVisibleText("Name (Z to A)");
    }

    //    Below method helps to sort product price in low to high order
    public void sortByPriceLowToHigh(){
        new Select(driver.findElement(filterDropdown))
                .selectByVisibleText("Price (low to high)");
    }


    //Below two methods are created for verifying whether the expected result is observed or not

//    Below method checks whether the product name is sorted to descending order or not
    public boolean isProductNameSortedDesc(){
        List<String> actualNames = new ArrayList<>();

        driver.findElements(productNames)
                .forEach(e -> actualNames.add(e.getText()));

        List<String> sortedNames = new ArrayList<>(actualNames);
        sortedNames.sort(Collections.reverseOrder());

        return actualNames.equals(sortedNames);
    }

    //    Below method checks whether the product price is sorted to low to high order or not
    public boolean isProductPriceSortedLowToHigh(){
        List<Double> actualPrices = new ArrayList<>();

        driver.findElements(productPrices)
                .forEach(e ->
                        actualPrices.add(
                                Double.parseDouble(e.getText().replace("$", ""))
                        )
                );

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        return actualPrices.equals(sortedPrices);
    }


//Below two methods are created for performing actions of cart

//    Below method helps to add items to the cart
    public void addItemsToCart(){
        driver.findElement(addBackpack).click();
        driver.findElement(addBikeLight).click();
    }

    //    Below method helps to redirect to the cart page by clicking on cart button
    public void goToCart(){
        driver.findElement(cartIcon).click();
    }
}
