package PageObjects;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MainCategoryPage extends BasePage {


    public MainCategoryPage(WebDriver driver) {
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

    @Step("Moves to proper category")
    public CategoryPage viewCategoryByName(String categoryName){

        CategoryPage categoryPage = new CategoryPage(driver);
        By categorySelector = new By.ByLinkText(categoryName);
        driver.findElement(categorySelector).click();
        logger.info("Moved to category page " + categoryName);
        return categoryPage;
    }



}