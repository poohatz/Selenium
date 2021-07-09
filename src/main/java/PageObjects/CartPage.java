package PageObjects;

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
        return totalAmountInCartPage.getText();
    }


    public String getProductQuantity(int index){

        By inputQuantitySelector = new By.ByCssSelector("input#cart_items_" + index + "_quantity");
        productQuantityInCart = driver.findElement(inputQuantitySelector);
        return productQuantityInCart.getAttribute("value");

    }

    public CartPage setDeliveryType(int i){

        CartPage cartPage = new CartPage(driver);
        cartPage.acceptCookie();
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(deliveryTypeOptionSelector));
        driver.findElements(deliveryTypeOptionSelector).get(i).click();
        return cartPage;
    }

    public CartPage setRealizationType(int i){

        CartPage cartPage = new CartPage(driver);
        cartPage.acceptCookie();
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(realizationTypeOptionSelector));
        driver.findElements(realizationTypeOptionSelector).get(i).click();
        return cartPage;
    }

    public CartPage deleteFromCartPage(String symbol){

        By removeButtonCartPageSelector =
                new By.ByXPath(".//a[contains(text(), '" + symbol + "')]/parent::h2/following-sibling::a");
        driver.findElement(removeButtonCartPageSelector).click();
        return this;
    }

    public OrderingPage saveAndOrderProductsFromCart(){

        this.acceptCookie();
        OrderingPage orderingPage = new OrderingPage(driver);
        driver.findElement(saveAndOrderButtonSelector).click();
        return orderingPage;

    }


}