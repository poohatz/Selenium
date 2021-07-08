package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebElement acceptCookie;

    protected By acceptCookieSelector = new By.ByCssSelector("div#gdpr-warning > button.accept-cookie");
    private final By numberOfProductsDisplaySelector = new By.ByCssSelector("span.cart-number");
    private final By cartSelector = new By.ByCssSelector("a > img[alt='Koszyk']");



    public BasePage(WebDriver driver) {
        
        this.driver = driver;

    }
    public BasePage(){

    }

    public void acceptCookie(){

        wait = new WebDriverWait(driver,5);

        try {

            acceptCookie = driver.findElement(acceptCookieSelector);
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookie));
            if(acceptCookie!=null) acceptCookie.click();
        }
        catch (Exception E){

            System.out.println("Zgoda RODO zaakceptowana wczesniej");
        }
    }

    public MainCategoryPage viewMainPage(){

        driver.navigate().to("https://www.decarte.com.pl/sklep/zaproszenia-slubne");
        return new MainCategoryPage(driver);
    }

    public String getNumberOfProductsDisplay(){

        String numberOfProductsDisplay = "";

        try {

            numberOfProductsDisplay = driver.findElement(numberOfProductsDisplaySelector).getText();
        }
        catch(NoSuchElementException e){}

        return numberOfProductsDisplay;
    }

    public CartPage viewCartPage(){

        CartPage cartPage = new CartPage(driver);
        driver.findElement(cartSelector).click();
        return cartPage;
    }
}
