package POMTests;

import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests {

    WebDriver driver;
    MainCategoryPage mainCategoryPage;
    CategoryPage categoryPage;
    ProductPage productPage;

    String[] categories = {"Nowości","Mystic Moment", "Folk&Boho", "Wild Garden", "Vintage&Nature", "Pastellove",
            "Royal Style", "Simple Beauty", "Classic Elegance", "Colors of Love", "Passion&Fun"};


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
    public void addOneProductToCartByProductPageTest(){

        String category = categories[4];
        String symbol = "sa32";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        String productSymbolInCart = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).addToCart().getProductSymbolInCart(0);

        assertTrue(productSymbolInCart.equals(symbol),"Produkt nie dodaje sie do koszyka");

    }

    @Test
    public void addSomeProductsToCartByProductPageTest(){

        String category1 = categories[4];
        String symbol1 = "sa32";
        String category2 = categories[5];
        String symbol2 = "PL47";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        String productSymbolInCart1 = mainCategoryPage.viewCategoryByName(category1).viewProductBySymbol(symbol1).addToCart().getProductSymbolInCart(0);
        String productSymbolInCart2 = mainCategoryPage.viewCategoryByName(category2).viewProductBySymbol(symbol2).addToCart().getProductSymbolInCart(1);

        assertTrue(productSymbolInCart1.equals(symbol1) && productSymbolInCart2.equals(symbol2),"Ktorys z produktow nie dodaje sie do koszyka");
    }


}