package POMTests;

import PageObjects.CartPage;
import PageObjects.MainCategoryPage;
import configuration.ConfigurationProperties;
import configuration.DriverManager;
import configuration.PropertiesLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTests {

    protected WebDriver driver;
    protected JavascriptExecutor jse;
    protected MainCategoryPage mainCategoryPage;
    protected CartPage cartPage;

    protected final String[] categories = {"Nowości","Mystic Moment", "Folk&Boho", "Wild Garden", "Vintage&Nature", "Pastellove",
            "Royal Style", "Simple Beauty", "Classic Elegance", "Colors of Love", "Passion&Fun"};


    @BeforeEach
    @BeforeMethod
    public void testSetUp(){

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties propertiesFromFile = propertiesLoader.getPropertiesFromFile("configuration.properties");
        ConfigurationProperties.setProperties(propertiesFromFile);

        driver = DriverManager.getWebDriver();

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        driver.manage().window().maximize();
        driver.navigate().to("https://www.decarte.com.pl/sklep/zaproszenia-slubne");

        jse = (JavascriptExecutor) driver;

        mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();


    }

    @AfterEach
    @AfterMethod
    public void closeDriver() {

        DriverManager.disposeDriver();
    }
}
