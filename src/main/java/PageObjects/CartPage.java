package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
}