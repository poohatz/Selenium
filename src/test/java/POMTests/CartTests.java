package POMTests;

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

    @BeforeEach
    public void testSetUp(){

        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        driver.manage().window().maximize();
        driver.navigate().to("https://decarte.com.pl");

    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }

    /*@Test
    public void emptyCart(){
        MainProductPage mainProductPage = new ProductPage(driver);
        int productAmount = productPage.goTo(productUrl).addToCart().viewCart().getProductsAmount(productId);

        assertTrue(productAmount==1,
                "Remove button was not found for a product with id=386 (Egipt - El Gouna). " +
                        "Was the product added to cart?");
    }*/
}