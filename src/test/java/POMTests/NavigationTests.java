package POMTests;

import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTests {

    WebDriver driver;
    //Wait wait = new WebDriverWait(driver,10);
    MainCategoryPage mainCategoryPage;
    CategoryPage categoryPage;
    ProductPage productPage;

    String[] categories = {"Nowości","Mystic Moment", "Folk&Boho", "Wild Garden", "Vintage&Nature", "Pastellove",
                            "Royal Style", "Simple Beauty", "Classic Elegance", "Colors of Love", "Passion&Fun"};
    String[] products = {"CL06"};

    @BeforeEach
    public void testSetUp(){

        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        driver.manage().window().maximize();
        driver.navigate().to("https://www.decarte.com.pl/sklep/zaproszenia-slubne");

    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }


    @Test
    public void navigateCategoryTest(){

        mainCategoryPage = new MainCategoryPage(driver);

        for(int i=0; i<categories.length; i++){

            String category = categories[i];
            String categoryName = mainCategoryPage.viewCategoryByName(category).getCategoryName();

            assertTrue(category.equals(categoryName), "Strona kategorii nie działa prawidłowo");
        }
    }

    @Test
    public void navigateToProductPageTest() {

        mainCategoryPage = new MainCategoryPage(driver);

        String symbol = products[0];
        String category = categories[9];
        String productSymbol = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).getProductSymbol();

        assertTrue(productSymbol.contains(symbol), "Strona produktu  nie działa prawidłowo");
    }


}