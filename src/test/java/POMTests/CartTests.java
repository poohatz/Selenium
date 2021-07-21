package POMTests;

import PageObjects.*;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseTests{

    private String category;
    private String symbol;
    private String category1;
    private String symbol1;
    private String category2;
    private String symbol2;
    private  String quantity;
    private  String quantity1;
    private  String quantity2;
    private String totalAmountNormal;

    public CartTests(String category, String symbol, String category1, String symbol1, String category2,
                     String symbol2, String quantity, String quantity1, String quantity2, String totalAmountNormal) {

        this.category = category;
        this.symbol = symbol;
        this.category1 = category1;
        this.symbol1 = symbol1;
        this.category2 = category2;
        this.symbol2 = symbol2;
        this.quantity = quantity;
        this.quantity1 = quantity1;
        this.quantity2 = quantity2;
        this.totalAmountNormal = totalAmountNormal;
    }

    @Factory
    public static Object[] cartTestsDataFactoryMethod() {

        CartTests firstCartTests = new CartTests("Mystic Moment", "mm04", "Pastellove", "PL47", "Vintage&Nature",
                "sa32", "56", "121", "21", "433,30");

        CartTests secondCartTests = new CartTests("Folk&Boho", "FB01", "Mystic Moment", "mm07", "Folk&Boho",
                "FB02", "16", "345", "25", "11");

        return new Object[]{
                firstCartTests,
                secondCartTests};
    }

    @org.testng.annotations.Test
    @Description("Tries to add product to cart by product page")
    public void addOneProductToCartByProductPageTest() {

        String productSymbolInCart = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .addToCart()
                .getProductSymbolInCart(0);

        assertTrue(productSymbolInCart.equals(symbol), "Produkt nie dodaje sie do koszyka");

    }

    @org.testng.annotations.Test
    @Description("Tries to add some products to cart by product page")
    public void addSomeProductsToCartByProductPageTest() {

        String productSymbolInCart1 = mainCategoryPage
                .viewCategoryByName(category1)
                .viewProductBySymbol(symbol1)
                .addToCart()
                .getProductSymbolInCart(0);

        String productSymbolInCart2 = mainCategoryPage
                .viewCategoryByName(category2)
                .viewProductBySymbol(symbol2)
                .addToCart()
                .getProductSymbolInCart(1);

        assertTrue(productSymbolInCart1.equals(symbol1) && productSymbolInCart2.equals(symbol2));

    }

    @org.testng.annotations.Test
    @Description("Tries to add twice one product to cart")
    public void addTwiceSameProductToCartTest() {

        String productQuantityInCartPage1;
        String productQuantityInCartPage2 = null;

        mainCategoryPage.acceptCookie();

        CartPage productSymbolInCart1 = mainCategoryPage
                .viewCategoryByName(category1)
                .viewProductBySymbol(symbol1)
                .addToCart();

        CartPage productSymbolInCart2 = mainCategoryPage
                .viewCategoryByName(category1)
                .viewProductBySymbol(symbol1)
                .addToCart();

        productQuantityInCartPage1 = productSymbolInCart1.getProductQuantity(0);

        try {
            productQuantityInCartPage2 = productSymbolInCart2.getProductQuantity(1);
        } catch (Exception E) {
        }
        ;

        Assertions.assertNull(productQuantityInCartPage2, "Produkt nie dodaje sie prawidlowo");
        assertEquals(productQuantityInCartPage1, "40", "Ilosc produktu nie zgadza sie");
    }

    @org.testng.annotations.Test
    @Description("Tries to add product to cart by category page")
    public void addProductToCartFromCategoryPageTest() {

        String productSymbolInCart;

        CategoryPage categoryPage = mainCategoryPage
                .viewCategoryByName(category);

        CartPage cartPage = categoryPage
                .addToCartByCategoryPage(symbol);

        productSymbolInCart = cartPage
                .viewCartPage()
                .getProductSymbolInCart(0);

        assertTrue(productSymbolInCart.equals(symbol), "Nie udalo sie dodac produktu ze strony kategorii");

    }

    @org.testng.annotations.Test
    @Description("Changes quantity of product in cart")
    public void changeQuantityOfProductInCartPageTest() {

        CartPage cartPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol);

        String productQuantityInCartPage = cartPage
                .changeProductQuantity(0, quantity)
                .getProductQuantity(0);

        assertTrue(quantity.equals(productQuantityInCartPage));

    }

    @org.testng.annotations.Test
    @Description("calculates total amount in cart")
    public void calculateTotalAmountInCartPageTest() {

        String totalAmountInCartPage = "";

        int deliveryType = 1;
        int realizationType = 2;

        //totalAmountNormal after including delivery value and realization value to test data
        Float totalAmount = Float.parseFloat(totalAmountNormal.replace(",","."));
        if(deliveryType == 0 || deliveryType == 1) totalAmount += 15;
        if(deliveryType ==2) totalAmount += 20;
        if(realizationType ==1) totalAmount += 200;
        if(realizationType ==2) totalAmount += 450;
        String totalAmountFinal = totalAmount.toString();
        if(totalAmountFinal.contains(".")) totalAmountFinal = totalAmountFinal.replace(".",",");

        CartPage cartPage = mainCategoryPage
                .viewCategoryByName(category1)
                .addToCartByCategoryPage(symbol1)
                .changeProductQuantity(0, quantity1);

        cartPage.acceptCookie();

        totalAmountInCartPage = cartPage
                .setDeliveryType(deliveryType)
                .setRealizationType(realizationType)
                .calculateTotalAmount()
                .getTotalAmount();

        totalAmountInCartPage = mainCategoryPage
                .viewCategoryByName(category2)
                .viewProductBySymbol(symbol2)
                .addToCart()
                .changeProductQuantity(1, quantity2)
                .calculateTotalAmount()
                .getTotalAmount()
                .replace(" ","");

        if(totalAmountInCartPage.endsWith("0")&&totalAmountInCartPage.contains(",")) totalAmountInCartPage = totalAmountInCartPage.substring(0,totalAmountInCartPage.lastIndexOf("0"));

        assertTrue(totalAmountInCartPage.equals(totalAmountFinal), "Przeliczona suma w koszyku nie zgadza sie");
    }


    @org.testng.annotations.Test
    @Description("tries to set invalid quantity in cart")
    public void setIncorrectQuantityCartPageTest() {

        String message = "Wartość nie może być mniejsza niż 20.";
        String productQuantityInCart = "";
        String quantityAllertMessageCartTable = "";


        CartPage cartPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .addToCart()
                .changeProductQuantity(0, quantity);

        cartPage.acceptCookie();

        productQuantityInCart = cartPage
                .calculateTotalAmount()
                .getProductQuantity(0);

        quantityAllertMessageCartTable = (String)jse.executeScript(
                        "const quantity = document.querySelector(\"input#cart_items_0_quantity\");"
                         + "message = quantity.validationMessage; return message;");

        assertTrue(quantityAllertMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy za malej ilosci");
        assertTrue(productQuantityInCart.contains(quantity), "Strona przeladowuje sie przy za małej ilosci");

    }


    @org.testng.annotations.Test
    @Description("Deletes one product from cart")
    public void deleteOneProductFromCartPageTest(){

        String optionalSymbol = "";

        CartPage cartPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .deleteFromCartPage(symbol);

        try{

            optionalSymbol = cartPage.getProductSymbolInCart(0);
        }
        catch (IndexOutOfBoundsException e2) {}

        Assertions.assertFalse(optionalSymbol.equals(symbol), "Nie udalo sie usunac produktu " + symbol);
    }

    @org.testng.annotations.Test
    @Description("Deletes some products from cart")
    public void deleteSomeProductsFromCartPageTest(){

        String optionalSymbol1 = "";
        String optionalSymbol2 = "";

        CartPage cartPage = mainCategoryPage
                .viewCategoryByName(category1).
                addToCartByCategoryPage(symbol1);

        cartPage = mainCategoryPage
                .viewCategoryByName(category2)
                .addToCartByCategoryPage(symbol2)
                .deleteFromCartPage(symbol2)
                .deleteFromCartPage(symbol1);

        try{

            optionalSymbol1 = cartPage.getProductSymbolInCart(1);
            optionalSymbol2 = cartPage.getProductSymbolInCart(0);
        }
        catch (IndexOutOfBoundsException e2) {}

        Assertions.assertFalse(optionalSymbol1.equals(symbol1)||optionalSymbol2.equals(symbol2),
                "Nie udalo sie usunac ktoregos z produktów " + symbol1 + " lub " + symbol2);
    }

    @org.testng.annotations.Test
    @Description("Tries to order products without checking delivery and realization type")
    public void orderProductWithoutCheckingDeliveryAndRealizationTypeCartPage() {

        String message = "Wybierz jedną z opcji.";
        String deliveryTypeMessageCartTable = "";

        OrderingPage orderingPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .addToCart()
                .saveAndOrderProductsFromCart();

        deliveryTypeMessageCartTable = (String) jse.executeScript(
                "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                        + "message = delivery.validationMessage; return message;");

        assertTrue(deliveryTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranych opcjach dostawy");

    }

    @org.testng.annotations.Test
    @Description("Tries to order products without checking realization type")
    public void orderProductWithoutCheckingRealizationTypeCartPage() {

        int deliveryType = 0;

        String message = "Wybierz jedną z opcji.";
        String realizationTypeMessageCartTable = "";

        OrderingPage orderingPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .addToCart()
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart();

        realizationTypeMessageCartTable = (String) jse.executeScript(
                "const realization = document.querySelector(\"#cart_realizationType_2\");"
                        + "message = realization.validationMessage; return message;");

        assertTrue(realizationTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranej opcji przesyłki");

    }

    @org.testng.annotations.Test
    @Description("Tries to order products without checking delivery type")
    public void orderProductWithoutCheckingDeliveryTypeCartPage() {

        int realizationType = 1;

        String message = "Wybierz jedną z opcji.";
        String deliveryTypeMessageCartTable = "";

        OrderingPage orderingPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .addToCart()
                .setRealizationType(realizationType)
                .saveAndOrderProductsFromCart();

        deliveryTypeMessageCartTable = (String) jse.executeScript(
                "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                        + "message = delivery.validationMessage; return message;");

        assertTrue(deliveryTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranym trybie realizacji");

    }

}