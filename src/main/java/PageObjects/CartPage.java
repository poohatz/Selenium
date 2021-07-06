package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private WebDriver driver;
    private By productInCartSymbolSelector = new By.ByCssSelector("h2 > a");
    private By cartSelector = new By.ByCssSelector("a > img[alt='Koszyk']");
    private String productInCartSymbol;
    private String symbol;
    private WebElement productQuantityInCart;

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
        productQuantityInCart = driver.findElement(inputQuantitySelector);
        productQuantityInCart.clear();
        productQuantityInCart.sendKeys(quantity.toString());
        return this;

    }


    public String getProductQuantity(int index){

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        productQuantityInCart = driver.findElement(inputQuantitySelector);
        String quantity = productQuantityInCart.getAttribute("value");
        return quantity;

    }

    public CartPage viewCartPage(){

        CartPage cartPage = new CartPage(driver);
        driver.findElement(cartSelector).click();
        return cartPage;
    }
}