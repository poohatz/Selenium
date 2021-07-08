package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends BasePage{

    private String symbol;

    private final By productSymbolSelector = new By.ByCssSelector("h1.product_name");
    private final By buttonNextSelector = new By.ByCssSelector("div.container > div > div > div > a.next");
    private final By buttonPreviousSelector = new By.ByCssSelector("div.container > div > div > div > a.prev");
    private final By addToCartButtonSelector = new By.ByCssSelector("form > div > button");
    private final By imageThumbnailProductGallerySelector = new By.ByCssSelector("img[data-index]");

    public List<WebElement> imageThumbnailProductGalleryFilesList;

    public ProductPage(WebDriver driver, String symbol) {

        super(driver);
        this.symbol = symbol;
    }

    public ProductPage(WebDriver driver) {
        super(driver);

    }

    @Override
    public void acceptCookie() {
        super.acceptCookie();
    }

    @Override
    public MainCategoryPage viewMainPage() {
        return super.viewMainPage();
    }

    @Override
    public String getNumberOfProductsDisplay() {
        return super.getNumberOfProductsDisplay();
    }

    @Override
    public CartPage viewCartPage() {
        return super.viewCartPage();
    }

    @Override
    public String getMessageWhileCartEmpty() {
        return super.getMessageWhileCartEmpty();
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
        return new CartPage(driver);
    }

    public String getImageFullProductGalleryFile() {

        By imageFullProductGallerySelector = new By.ByCssSelector("img[alt*=' " + symbol + " ']");
        return driver.findElements(imageFullProductGallerySelector).get(0).getAttribute("src");
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