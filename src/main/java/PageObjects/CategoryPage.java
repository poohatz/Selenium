package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CategoryPage extends BasePage{

    private final By categoryNameSelector = new By.ByCssSelector("h1");
    private final By productSymbolFirstSelector = new By.ByCssSelector("ul#products > li > a > img");
    private final By productSymbolLastSelector = new By.ByCssSelector("p.description");


    public CategoryPage(WebDriver driver) {

        super(driver);
    }

    @Override
    public void acceptCookie() {
        super.acceptCookie();
    }

    @Override
    public MainCategoryPage viewMainPage() {
        return super.viewMainPage();
    }

    @Override
    public String getNumberOfProductsDisplay() {
        return super.getNumberOfProductsDisplay();
    }

    @Override
    public CartPage viewCartPage() {
        return super.viewCartPage();
    }

    @Override
    public String getMessageWhileCartEmpty() {
        return super.getMessageWhileCartEmpty();
    }

    public String getCategoryName(){

        return driver.findElement(this.categoryNameSelector).getText();
    }


    public String findLastProductSymbol(){

        List<WebElement> ProductsFromCategory = driver.findElements(productSymbolLastSelector);
        int index = ProductsFromCategory.size()-1;

        WebElement lastProductFromCategory = ProductsFromCategory.get(index);
        String lastProductSymbol = lastProductFromCategory.getText();
        lastProductSymbol = lastProductSymbol.substring(6);
        int spacePosition = lastProductSymbol.indexOf(" ");
        lastProductSymbol = lastProductSymbol.substring(0, spacePosition-1);

        return lastProductSymbol;

    }

    public ProductPage viewProductBySymbol(String symbol){

        By productSymbolSelector = new By.ByCssSelector("img[alt='Model " + symbol + "']");
        wait = new WebDriverWait(driver, 7);
        super.acceptCookie();

        wait.until(ExpectedConditions.elementToBeClickable(productSymbolSelector));
        ProductPage productPage = new ProductPage(driver, symbol);
        driver.findElement(productSymbolSelector).click();
        logger.info("Moved into page of product " + symbol);
        return productPage;
    }


    public ProductPage viewFirstProductPage(){

        ProductPage productPage = new ProductPage(driver);
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(productSymbolFirstSelector));
        WebElement firstProductFromCategory = driver.findElements(productSymbolFirstSelector).get(0);
        firstProductFromCategory.click();

        String productSymbol = productPage.getProductSymbol();
        System.out.println(productSymbol);
        productPage = new ProductPage(driver, productSymbol);
        return productPage;

    }

    public CartPage addToCartByCategoryPage(String symbol){

        this.acceptCookie();

        By orderButtonCategoryPage = new By.ByXPath(".//a/img[@alt='Model " + symbol + "']/parent::a/parent::li/form/button");
        driver.findElement(orderButtonCategoryPage).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.viewCartPage();
        logger.info(symbol + " was added to cart");
        return cartPage;
    }

}