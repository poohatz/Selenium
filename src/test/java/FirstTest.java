import com.google.common.annotations.VisibleForTesting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class FirstTest {

    WebDriver driver;
    Actions actions;

    @BeforeEach
    public void driverSetup(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        actions = new Actions(driver);

    }
    //@AfterEach
    public void driverQuit(){
        driver.quit();
    }

    @org.junit.jupiter.api.Test
    public void test(){
        String title = "Wikipedia, wolna encyklopedia";
        String subUrl = "iki/Wikipedia:Strona_g";


        driver.get("https://pl.wikipedia.org/wiki/Wikipedia:Strona_g%C5%82%C3%B3wna");
        driver.findElement(By.cssSelector("a[href=\"/wiki/Pomnik_Drzewa_Pawiackiego\"]")).click();
        driver.navigate().back();
        Assertions.assertEquals(title, driver.getTitle(), "tytul neizgodny");

        Assertions.assertTrue(driver.getCurrentUrl().contains(subUrl), "url niezgodny");
    }

    @Test
    public void cookiesTest() throws InterruptedException {
        driver.get("https://pl.wikipedia.org/wiki/Wikipedia:Strona_g%C5%82%C3%B3wna");
        Thread.sleep(1000);
        int cookiesNumber = driver.manage().getCookies().size();
        //Assertions.assertEquals(8, cookiesNumber, "ilosc ciastek nie zgadza sie");
        Cookie myCookie = new Cookie("name", "wojtek");
        driver.manage().addCookie(myCookie);
        Cookie isCookieAdded = driver.manage().getCookieNamed("name");
        Assertions.assertNotNull(isCookieAdded, "ciastko nie dodalo sie");
        Assertions.assertEquals("wojtek", isCookieAdded.getValue(), "wartosc ciasta niezgodna");
        driver.manage().deleteCookieNamed("name");
        Assertions.assertNull(driver.manage().getCookieNamed("name"),"ciasto nie usunelo sie");
        Cookie geoipCookie = driver.manage().getCookieNamed("GeoIP");
        String domain = geoipCookie.getDomain();
        String path = geoipCookie.getPath();
        Boolean http = geoipCookie.isHttpOnly();
        Assertions.assertEquals(".wikipedia.org",domain,"domena nie zgadza sie");
        Assertions.assertFalse(http, "nie jest false");
    }

    @Test
    public void findElementTest(){
        driver.get("https://decarte.com.pl");
        driver.findElement(By.id("three-circles-invitations")).click();
        driver.findElement(By.linkText("Nowości")).click();
        driver.findElement(By.cssSelector("img[src=\"https://www.decarte.com.pl/media/cache/product_thumb/zaproszenia/produkty/fa415cfacadad132eaac29d6aa375449d7c405f1.jpg\"]")).click();
        driver.findElement(By.cssSelector("button[class=\"btn btn-primary\"]")).click();
        driver.findElement(By.id("cart_items_0_quantity")).clear();
        driver.findElement(By.id("cart_items_0_quantity")).sendKeys("145");
        driver.findElement(By.id("cart_deliveryType_2")).click();
        driver.findElement(By.id("cart_realizationType_1")).click();
        driver.findElement(By.id("cart_items_0_quantity")).clear();
        driver.findElement(By.id("cart_items_0_quantity")).sendKeys("21");
        driver.findElement(By.cssSelector("#gdpr-warning > button")).click();
        driver.findElement(By.id("cart_save")).click();
        driver.findElement(By.id("cart_save_and_order")).click();
        driver.findElement(By.id("shipping_details_name")).sendKeys("Wojtek Puchalski");
        driver.findElement(By.id("shipping_details_street")).sendKeys("Srebrniki 5B/17");
        driver.findElement(By.id("shipping_details_postalCode")).sendKeys("80-282");
        driver.findElement(By.id("shipping_details_city")).sendKeys("Gdańsk");
        driver.findElement(By.id("shipping_details_email")).sendKeys("wpuchalski@o2.pl");
        driver.findElement(By.id("shipping_details_phone")).sendKeys("600 151 804");
        driver.findElement(By.id("shipping_details_notes")).sendKeys("test selenium webdriver - zakup produktu - happy path, sorki za SPAM ;)");
        driver.findElement(By.id("shipping_details_hasInvoice")).click();
        driver.findElement(By.id("shipping_details_name")).submit();
        driver.findElement(By.id("terms")).click();
        driver.findElement(By.id("personal-data")).click();
        driver.findElement(By.id("terms")).submit();

    }

    @ParameterizedTest(name = "login {0} haslo {1}")
    @CsvSource({"kupa,'',kupa nie boli",
    "'',kupa,kupa boli"})
    public void negativeLogin(String username,String password,String exceptedAlert){
        String alert = login(username, password);
        Assertions.assertEquals(exceptedAlert,alert,"bledny komunikat");

    }
    public String login(String username,String password){
        driver.get("https://poczta.o2.pl/zaloguj");
        driver.findElement(By.id("login")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login")).submit();
        String alert = driver.findElement(By.id("formError")).getText();
        return alert;
    }

    @Test
    public void testActions(){
        driver.get("https://fakestore.testelka.pl/actions/");
        //adasdaslkjlkjlk

    }

}
