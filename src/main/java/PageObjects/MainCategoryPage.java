package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryPage {

    private WebDriver driver;
    private CategoryPage categoryPage;
    private By category;

    public By acceptCookieSelector = new By.ByCssSelector("div#gdpr-warning > button.accept-cookie");
    public WebElement acceptCookie;
    private WebDriverWait wait;


    public MainCategoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptCookieMainCategoryPage(){
        wait = new WebDriverWait(driver,5);
        try {
            acceptCookie = driver.findElement(acceptCookieSelector);
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookie));
            if(acceptCookie!=null) acceptCookie.click();
        } catch (Exception E){
            System.out.println("Zgoda RODO zaakceptowana wczesniej");
        };
    }


    public CategoryPage viewCategoryByName(String categoryName){

        categoryPage = new CategoryPage(driver,categoryName);
        category = new By.ByLinkText(categoryName);
        driver.findElement(category).click();
        return categoryPage;
    }

    public MainCategoryPage viewMainPage(){

        driver.navigate().to("https://www.decarte.com.pl/sklep/zaproszenia-slubne");
        return this;
    }


}