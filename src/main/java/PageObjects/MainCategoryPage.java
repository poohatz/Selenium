package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryPage {

    private WebDriver driver;
    private CategoryPage categoryPage;
    private By category;


    public MainCategoryPage(WebDriver driver) {

        this.driver = driver;
    }



    public CategoryPage viewCategoryByName(String categoryName){

        categoryPage = new CategoryPage(driver,categoryName);
        category = new By.ByLinkText(categoryName);
        driver.findElement(category).click();
        return categoryPage;
    }


}