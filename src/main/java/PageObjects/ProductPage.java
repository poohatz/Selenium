package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String symbol;

    By productSymbolSelector = new By.ByCssSelector("h1.product_name");

    public ProductPage(WebDriver driver, String symbol) {

        this.driver = driver;
        this.symbol = symbol;
    }

    public ProductPage() {
    }

    public String getProductSymbol() {

        String productSymbol = driver.findElement(productSymbolSelector).getText();
        System.out.println(productSymbol);
        return productSymbol;
    }
}