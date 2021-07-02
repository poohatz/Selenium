package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String name;
    private ProductPage productPage;

    private By categoryNameSelector = new By.ByCssSelector("h1");
    private By productSymbolSelector = new By.ByCssSelector("img[alt='Model CL06']");
    private By acceptCookieSelector = new By.ByCssSelector("div#gdpr-warning > button.accept-cookie");

    public CategoryPage(WebDriver driver, String name) {

        this.name = name;
        this.driver = driver;
    }

    public CategoryPage() {
    }

    public String getCategoryName(){

        String categoryName = driver.findElement(this.categoryNameSelector).getText();
        return categoryName;
    }

    public ProductPage viewProductBySymbol(String symbol){

        wait = new WebDriverWait(driver, 10);
        if(acceptCookieSelector !=null) driver.findElement(acceptCookieSelector).click();
        wait.until(ExpectedConditions.elementToBeClickable(productSymbolSelector));
        productPage = new ProductPage(driver,symbol);
        driver.findElement(productSymbolSelector).click();
        return productPage;
    }
}