package POMTests;

import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisplayTests {

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
    public void displayAndNavigateProductGalleryTest(){

        String category = categories[3];
        String symbol = "WG08";


        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        ProductPage productPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol);
        
        String imageFirstFullProductGalleryFile = productPage.getImageFullProductGalleryFile();
        int numberOfThumbnails = productPage.getImageThumbnailProductGalleryFilesList().size();
        String imageFirstThumbnailProductGalleryFile = productPage.getImageThumbnailProductGalleryFilesList().get(0).getAttribute("src");
        assertTrue(imageFirstFullProductGalleryFile.equals(imageFirstThumbnailProductGalleryFile), "Pierwsze duze zdjecie nie wyswietla sie prawidlowo");


        for(int i=1;i<numberOfThumbnails;i++) {

            String imageNextThumbnailProductGalleryFile = productPage.changeImageFullProductGalleryFile(i);
            String imageNextFullProductGalleryFile = productPage.getImageFullProductGalleryFile();
            assertTrue(imageNextFullProductGalleryFile.equals(imageNextThumbnailProductGalleryFile), "Duze zdjecie po kliknieciu miniaturki " + i +" nie wyswietla sie prawidlowo");

        }
    }


}