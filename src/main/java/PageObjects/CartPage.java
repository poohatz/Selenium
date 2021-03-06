package PageObjects;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage{

    private final By productInCartSymbolSelector = new By.ByCssSelector("h2 > a");
    private final By calculateTotalAmountButtonSelector = new By.ByCssSelector("#cart_save");
    private final By totalAmountSelector = new By.ByCssSelector("div.cart-total-price > strong");
    private final By deliveryTypeOptionSelector = new By.ByCssSelector("input[type='radio'][name='cart[deliveryType]']");
    private final By realizationTypeOptionSelector = new By.ByCssSelector("input[type='radio'][name='cart[realizationType]']");
    private final By saveAndOrderButtonSelector = new By.ByCssSelector("#cart_save_and_order");

    private String symbol;
    private WebElement productQuantityInCart;

    public CartPage(WebDriver driver) {
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

    public String getProductSymbolInCart(int index) {

        String productInCartSymbol = driver.findElements(productInCartSymbolSelector).get(index).getText();
        int spacePosition = productInCartSymbol.lastIndexOf(" ");
        productInCartSymbol = productInCartSymbol.substring(spacePosition+1);
        return productInCartSymbol;
    }

    @Step("changes product quantity")
    public CartPage changeProductQuantity(int index, String quantity) {

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        productQuantityInCart = driver.findElement(inputQuantitySelector);
        productQuantityInCart.clear();
        productQuantityInCart.sendKeys(quantity);
        logger.info("Changed quantity on " + quantity);
        return this;

    }
    @Step("Calculates total amount")
    public CartPage calculateTotalAmount(){

        WebElement calculateTotalAmountButton = driver.findElement(calculateTotalAmountButtonSelector);
        wait = new WebDriverWait(driver,7);
        wait.until(ExpectedConditions.elementToBeClickable(calculateTotalAmountButton));
        calculateTotalAmountButton.click();
        logger.info("Total amount changed");

        return this;

    }

    @Step("Gets total amount")
    public String getTotalAmount(){

        WebElement totalAmountInCartPage = driver.findElement(totalAmountSelector);
        logger.info("Total amount is " + totalAmountInCartPage);

        return totalAmountInCartPage.getText();
    }


    @Step("Displays product quantity")
    public String getProductQuantity(int index){

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        productQuantityInCart = driver.findElement(inputQuantitySelector);
        logger.info("Quantity is " + productQuantityInCart);
        return productQuantityInCart.getAttribute("value");

    }

    @Step("sets up delivery type")
    public CartPage setDeliveryType(int i){

        CartPage cartPage = new CartPage(driver);
        cartPage.acceptCookie();
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(deliveryTypeOptionSelector));
        driver.findElements(deliveryTypeOptionSelector).get(i).click();
        logger.info("Delivery type changed");
        return cartPage;
    }

    @Step("sets up realization type")
    public CartPage setRealizationType(int i){

        CartPage cartPage = new CartPage(driver);
        cartPage.acceptCookie();
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(realizationTypeOptionSelector));
        driver.findElements(realizationTypeOptionSelector).get(i).click();
        logger.info("Realization type changed");
        return cartPage;
    }

    @Step("deletes product from cart")
    public CartPage deleteFromCartPage(String symbol){

        By removeButtonCartPageSelector =
                new By.ByXPath(".//a[contains(text(), '" + symbol + "')]/parent::h2/following-sibling::a");
        driver.findElement(removeButtonCartPageSelector).click();
        logger.info(symbol + " deleted from cart");
        return this;
    }

    @Step("clicks Save and Order Button")
    public OrderingPage saveAndOrderProductsFromCart(){

        this.acceptCookie();
        OrderingPage orderingPage = new OrderingPage(driver);
        driver.findElement(saveAndOrderButtonSelector).click();
        logger.info("Save and Order Button clicked");
        return orderingPage;

    }


}