package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String symbol;

    private By productSymbolSelector = new By.ByCssSelector("h1.product_name");
    private By buttonNextSelector = new By.ByCssSelector("div.container > div > div > div > a.next");
    private By buttonPreviousSelector = new By.ByCssSelector("div.container > div > div > div > a.prev");
    private By addToCartButtonSelector = new By.ByCssSelector("form > div > button");

    By imageThumbnailProductGallerySelector = new By.ByCssSelector("img[data-index]");
    public List<WebElement> imageThumbnailProductGalleryFilesList;

    public ProductPage(WebDriver driver, String symbol) {

        this.driver = driver;
        this.symbol = symbol;
    }

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductSymbol() {

        String productSymbol = driver.findElement(productSymbolSelector).getText();
        System.out.println(productSymbol);
        return productSymbol;
    }

    public ProductPage viewNextProductPage(){

        wait = new WebDriverWait(driver,9);
        wait.until(ExpectedConditions.elementToBeClickable(buttonNextSelector));
        WebElement buttonNextProduct = driver.findElement(buttonNextSelector);
        buttonNextProduct.click();
        return this;
    }

    public ProductPage viewPreviousProductPage(){

        wait = new WebDriverWait(driver,9);
        wait.until(ExpectedConditions.elementToBeClickable(buttonPreviousSelector));
        WebElement buttonPreviousProduct = driver.findElement(buttonPreviousSelector);
        buttonPreviousProduct.click();
        return this;

    }

    public CartPage addToCart(){

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonSelector));
        WebElement addToCartButton = driver.findElement(addToCartButtonSelector);
        addToCartButton.click();
        CartPage cartPage = new CartPage(driver);
        return cartPage;

    }

    public String getImageFullProductGalleryFile() {

        By imageFullProductGallerySelector = new By.ByCssSelector("img[alt*=' " + symbol + " ']");
        String imageFullProductGalleryFile = driver.findElements(imageFullProductGallerySelector).get(0).getAttribute("src");
        return imageFullProductGalleryFile;
    }

    public List<WebElement> getImageThumbnailProductGalleryFilesList(){

        List<WebElement> imageThumbnailProductGalleryFilesList = new ArrayList<>();
        imageThumbnailProductGalleryFilesList = driver.findElements(imageThumbnailProductGallerySelector);
        return imageThumbnailProductGalleryFilesList;
    }

    public String changeImageFullProductGalleryFile(int imageThumbnailProductsGalleryFilesIndex){

        wait = new WebDriverWait(driver, 10);
        imageThumbnailProductGalleryFilesList = driver.findElements(imageThumbnailProductGallerySelector);
        WebElement imageThumbnailProductGallery = imageThumbnailProductGalleryFilesList.get(imageThumbnailProductsGalleryFilesIndex);
        wait.until(ExpectedConditions.elementToBeClickable(imageThumbnailProductGallery));
        imageThumbnailProductGallery.click();
        return this.getImageFullProductGalleryFile();
    }
}