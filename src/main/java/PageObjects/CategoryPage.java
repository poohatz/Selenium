package PageObjects;

import com.sun.tools.javac.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String name;
    private String category;
    private String symbol;
    private ProductPage productPage;

    private By categoryNameSelector = new By.ByCssSelector("h1");
    private By productSymbolSelector;
    private By acceptCookieSelector = new By.ByCssSelector("div#gdpr-warning > button.accept-cookie");
    private By productSymbolFirstSelector = new By.ByCssSelector("ul#products > li > a > img");
    private WebElement acceptCookie;
    private WebElement firstProductFromCategory;
    private WebElement lastProductFromCategory;



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

        productSymbolSelector = new By.ByCssSelector("img[alt='Model " + symbol + "']");
        wait = new WebDriverWait(driver, 7);
        try {
            acceptCookie = driver.findElement(acceptCookieSelector);
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookie));
            if(acceptCookie!=null) acceptCookie.click();
        } catch (Exception E){
            System.out.println("Zgoda RODO zaakceptowana wczesniej");
        };

        wait.until(ExpectedConditions.elementToBeClickable(productSymbolSelector));
        productPage = new ProductPage(driver,symbol);
        driver.findElement(productSymbolSelector).click();
        return productPage;
    }


    public ProductPage viewFirstProductPage(){

        ProductPage productPage = new ProductPage(driver);
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(productSymbolFirstSelector));
        firstProductFromCategory = driver.findElements(productSymbolFirstSelector).get(0);
        firstProductFromCategory.click();

        try {
            acceptCookie = driver.findElement(acceptCookieSelector);
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookie));
            if(acceptCookie!=null) acceptCookie.click();
        } catch (Exception E){
            System.out.println("Zgoda RODO zaakceptowana wczesniej");
        };

        String productSymbol = productPage.getProductSymbol();
        System.out.println(productSymbol);
        productPage = new ProductPage(driver,productSymbol);
        return productPage;

    }
}