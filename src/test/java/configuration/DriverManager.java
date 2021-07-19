package configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    private static WebDriver driver;

    private DriverManager() {
    }

    public static WebDriver getWebDriver() {

        if (driver == null) {
            driver = BrowserFactory.getBrowser(LocalWebDriverProperties.getLocalBrowser());
        }

        return driver;
    }

    public static void disposeDriver(){
        driver.close();
        if (!LocalWebDriverProperties.getLocalBrowser().equals(BrowserType.FIREFOX)){
            driver.quit();
        }
        driver = null;
    }
}