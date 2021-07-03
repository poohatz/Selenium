package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String symbol;

    private By productSymbolSelector = new By.ByCssSelector("h1.product_name");
    private By buttonNextSelector = new By.ByCssSelector("div.container > div > div > div > a.next");
    private By buttonPreviousSelector = new By.ByCssSelector("div.container > div > div > div > a.prev");

    public ProductPage(WebDriver driver, String symbol) {

        this.driver = driver;
        this.symbol = symbol;
    }

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductSymbol() {

        String productSymbol = driver.findElement(productSymbolSelector).getText();
        System.out.println(productSymbol);
        return productSymbol;
    }

    public ProductPage viewNextProductPage(){

        wait = new WebDriverWait(driver,9);
        wait.until(ExpectedConditions.elementToBeClickable(buttonNextSelector));
        WebElement buttonNextProduct = driver.findElement(buttonNextSelector);
        buttonNextProduct.click();
        return this;
    }

    public ProductPage viewPreviousProductPage(){
        wait = new WebDriverWait(driver,9);
        wait.until(ExpectedConditions.elementToBeClickable(buttonPreviousSelector));
        WebElement buttonPreviousProduct = driver.findElement(buttonPreviousSelector);
        buttonPreviousProduct.click();
        return this;

    }
}