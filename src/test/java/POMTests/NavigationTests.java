package POMTests;

import PageObjects.CartPage;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationTests {

    WebDriver driver;
    //Wait wait = new WebDriverWait(driver,10);
    MainCategoryPage mainCategoryPage;
    CategoryPage categoryPage;
    ProductPage productPage;

    String[] categories = {"Nowości","Mystic Moment", "Folk&Boho", "Wild Garden", "Vintage&Nature", "Pastellove",
                            "Royal Style", "Simple Beauty", "Classic Elegance", "Colors of Love", "Passion&Fun"};
    String[] products = {"CL06","mm04","FB01", "jt100", "PL47", "sa32","CL05", "Cl37", "mm07"};

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

        String symbol = "CL06";
        String category = categories[9];

        mainCategoryPage = new MainCategoryPage(driver);
        String productSymbol = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).getProductSymbol();

        assertTrue(productSymbol.contains(symbol), "Strona produktu nie działa prawidłowo");
    }

    @Test
    public void navigateToNextProductTest(){

        String symbol = "mm04";
        String symbolNext = "mm07";
        String category = categories[1];

        mainCategoryPage = new MainCategoryPage(driver);
        categoryPage = new CategoryPage(driver, category);
        productPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol);

        String productSymbolNext = productPage.viewNextProductPage().getProductSymbol();

        assertTrue(productSymbolNext.contains(symbolNext), "Przycisk 'Następny' nie działa prawidłowo");

    }

    @Test
    public void navigateToPreviousProductTest(){

        String symbol = "mm04";
        String symbolPrevious = "MM20200003";
        String category = categories[1];

        mainCategoryPage = new MainCategoryPage(driver);
        categoryPage = new CategoryPage(driver, category);
        productPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol);

        String productSymbolPrevious = productPage.viewPreviousProductPage().getProductSymbol();

        assertTrue(productSymbolPrevious.contains(symbolPrevious), "Przycisk 'Poprzedni' nie działa prawidłowo");

    }

    @Test
    public void navigateAllProductsByNextButtonTest(){

        //String symbolFirst = "FR48";
        //String symbolLast = "WG208";
        String category = categories[8];

        mainCategoryPage = new MainCategoryPage(driver);
        categoryPage = new CategoryPage(driver, category);

        CategoryPage categoryPage = mainCategoryPage.viewCategoryByName(category);
        String productSymbolLast = categoryPage.findLastProductSymbol();
        String symbolLast = productSymbolLast;

        ProductPage productPage = categoryPage.viewFirstProductPage();
        String productSymbolFirst = productPage.getProductSymbol();
        String productSymbolNext = productSymbolFirst;


        do {
            productSymbolNext = productPage.viewNextProductPage().getProductSymbol();
            System.out.println(productSymbolNext);
        }
        while (!productSymbolNext.contains(symbolLast));

        assertTrue(productSymbolNext.contains(symbolLast), "Przejście przez wszystkie produkty nie działa prawidłowo");

    }

    @Test
    public void addToCartByProductPageTest(){

        String category = categories[4];
        String symbol = "sa32";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        String productSymbolInCart = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).addToCart().getProductSymbolInCart();

        assertTrue(productSymbolInCart.equals(symbol),"Produkt nie dodaje sie do koszyka");

    }

    @Test
    public void navigateFromCartToMainPageAnRevertTest(){

        String category = categories[4];
        String symbol = "sa32";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        String productSymbolInCart = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).addToCart().getProductSymbolInCart();

        mainCategoryPage.viewMainPage();
        String productSymbolAfterRevert = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).addToCart().getProductSymbolInCart();

        assertEquals(productSymbolAfterRevert, productSymbolInCart, "Nawigacja do strony glownej nie dziala, badz zawartosc koszyka nie zapisuje sie");



    }

}
