package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    public WebDriver driver;
    public WebDriverWait wait;
    public WebElement acceptCookie;
    public Logger logger = (Logger) LogManager.getRootLogger();


    protected By acceptCookieSelector = new By.ByCssSelector("div#gdpr-warning > button.accept-cookie");
    private final By numberOfProductsDisplaySelector = new By.ByCssSelector("span.cart-number");
    private final By cartSelector = new By.ByCssSelector("a > img[alt='Koszyk']");
    private final By messageWhileCartEmptySelector = new By.ByCssSelector("#cart_items");



    public BasePage(WebDriver driver) {
        
        this.driver = driver;

    }
    public BasePage(){

    }

    public void acceptCookie(){

        wait = new WebDriverWait(driver,10);

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

    public String getMessageWhileCartEmpty(){

        CartPage cartePage = new CartPage(driver);
        return driver.findElement(messageWhileCartEmptySelector).getText();
    }
}
