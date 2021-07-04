package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private WebDriver driver;
    By productInCartSymbolSelector = new By.ByCssSelector("h2 > a");
    String productInCartSymbol;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }


    public String getProductSymbolInCart() {

        productInCartSymbol = driver.findElement(productInCartSymbolSelector).getText();
        int spacePosition = productInCartSymbol.lastIndexOf(" ");
        productInCartSymbol = productInCartSymbol.substring(spacePosition+1);
        return productInCartSymbol;
    }
}