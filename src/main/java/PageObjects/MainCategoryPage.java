package PageObjects;

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

    public CategoryPage viewCategoryByName(String categoryName){

        CategoryPage categoryPage = new CategoryPage(driver);
        By categorySelector = new By.ByLinkText(categoryName);
        driver.findElement(categorySelector).click();
        return categoryPage;
    }



}