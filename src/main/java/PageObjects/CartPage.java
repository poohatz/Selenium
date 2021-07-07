package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private By productInCartSymbolSelector = new By.ByCssSelector("h2 > a");
    private By cartSelector = new By.ByCssSelector("a > img[alt='Koszyk']");
    private By calculateTotalAmountButtonSelector = new By.ByCssSelector("#cart_save");
    private By totalAmountSelector = new By.ByCssSelector("div.cart-total-price > strong");
    private By acceptCookieSelector = new By.ByCssSelector("div#gdpr-warning > button.accept-cookie");
    private By payuDeliveryTypeOptionSelector = new By.ByCssSelector("#cart_deliveryType_4");
    private By standardRealizationTypeOptionSelector = new By.ByCssSelector("#cart_realizationType_1");


    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement acceptCookie;
    private String productInCartSymbol;
    private String symbol;
    private WebElement productQuantityInCart;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }
    public void acceptCookieCartPage(){
        wait = new WebDriverWait(driver,5);
        try {
            acceptCookie = driver.findElement(acceptCookieSelector);
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookie));
            if(acceptCookie!=null) acceptCookie.click();
        } catch (Exception E){
            System.out.println("Zgoda RODO zaakceptowana wczesniej");
        };
    }



    public String getProductSymbolInCart(int index) {

        productInCartSymbol = driver.findElements(productInCartSymbolSelector).get(index).getText();
        int spacePosition = productInCartSymbol.lastIndexOf(" ");
        productInCartSymbol = productInCartSymbol.substring(spacePosition+1);
        return productInCartSymbol;
    }

    public CartPage changeProductQuantity(int index, String quantity) {

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        productQuantityInCart = driver.findElement(inputQuantitySelector);
        productQuantityInCart.clear();
        productQuantityInCart.sendKeys(quantity);
        return this;

    }

    public CartPage calculateTotalAmount(){

        WebElement calculateTotalAmountButton = driver.findElement(calculateTotalAmountButtonSelector);
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(calculateTotalAmountButton));
        calculateTotalAmountButton.click();
        return this;

    }

    public String getTotalAmount(){

        WebElement totalAmountInCartPage = driver.findElement(totalAmountSelector);
        String totalAmount = totalAmountInCartPage.getText();
        return totalAmount;
    }


    public String getProductQuantity(int index){

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        productQuantityInCart = driver.findElement(inputQuantitySelector);
        String quantity = productQuantityInCart.getAttribute("value");
        return quantity;

    }

    public CartPage viewCartPage(){

        this.acceptCookieCartPage();

        CartPage cartPage = new CartPage(driver);
        driver.findElement(cartSelector).click();
        return cartPage;
    }

    public CartPage setDeliveryType(){

        CartPage cartPage = new CartPage(driver);
        driver.findElement(payuDeliveryTypeOptionSelector).click();
        return cartPage;
    }

    public CartPage setRealizationType(){

        CartPage cartPage = new CartPage(driver);
        driver.findElement(standardRealizationTypeOptionSelector).click();
        return cartPage;
    }

    public CartPage deleteFromCartPage(String symbol){

        By removeButtonCartPageSelector =
                new By.ByXPath(".//a[contains(text(), '" + symbol + "')]/parent::h2/following-sibling::a");
        driver.findElement(removeButtonCartPageSelector).click();
        return this;
    }
}