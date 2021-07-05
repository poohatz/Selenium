package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {



    private WebDriver driver;
    private By productInCartSymbolSelector = new By.ByCssSelector("h2 > a");
    private String productInCartSymbol;
    private String symbol;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }


    public String getProductSymbolInCart(int index) {

        productInCartSymbol = driver.findElements(productInCartSymbolSelector).get(index).getText();
        int spacePosition = productInCartSymbol.lastIndexOf(" ");
        productInCartSymbol = productInCartSymbol.substring(spacePosition+1);
        return productInCartSymbol;
    }

    public CartPage changeProductQuantity(int index, Integer quantity) {

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        driver.findElement(inputQuantitySelector).sendKeys(quantity.toString());
        return this;

    }

    public String getProductQuantity(int index){

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        String quantity = driver.findElement(inputQuantitySelector).getAttribute("value");
        return quantity;

    }
}