package POMTests;

import PageObjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseTests{


    @Test
    public void addOneProductToCartByProductPageTest() {

        String category = categories[4];
        String symbol = "sa32";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        String productSymbolInCart = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).addToCart().getProductSymbolInCart(0);

        assertTrue(productSymbolInCart.equals(symbol), "Produkt nie dodaje sie do koszyka");

    }

    @Test
    public void addSomeProductsToCartByProductPageTest() {

        String category1 = categories[4];
        String symbol1 = "sa32";
        String category2 = categories[5];
        String symbol2 = "PL47";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        String productSymbolInCart1 = mainCategoryPage.viewCategoryByName(category1).viewProductBySymbol(symbol1).addToCart().getProductSymbolInCart(0);
        String productSymbolInCart2 = mainCategoryPage.viewCategoryByName(category2).viewProductBySymbol(symbol2).addToCart().getProductSymbolInCart(1);

        assertTrue(productSymbolInCart1.equals(symbol1) && productSymbolInCart2.equals(symbol2), "Ktorys z produktow nie dodaje sie do koszyka");
    }

    @Test
    public void addTwiceSameProductToCartTest() {

        String category1 = categories[4];
        String symbol1 = "sa32";
        String category2 = categories[4];
        String symbol2 = "sa32";
        String productQuantityInCartPage1;
        String productQuantityInCartPage2 = null;


        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        CartPage productSymbolInCart1 = mainCategoryPage.viewCategoryByName(category1).viewProductBySymbol(symbol1).addToCart();
        CartPage productSymbolInCart2 = mainCategoryPage.viewCategoryByName(category2).viewProductBySymbol(symbol2).addToCart();

        productQuantityInCartPage1 = productSymbolInCart1.getProductQuantity(0);

        try {
            productQuantityInCartPage2 = productSymbolInCart2.getProductQuantity(1);
        } catch (Exception E) {
        }
        ;

        Assertions.assertNull(productQuantityInCartPage2, "Produkt nie dodaje sie prawidlowo");
        assertEquals(productQuantityInCartPage1, "40", "Ilosc produktu nie zgadza sie");
    }

    @Test
    public void addProductToCartFromCategoryPageTest() {

        String category = categories[4];
        String symbol = "sa32";
        String productSymbolInCart;

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        CategoryPage categoryPage = mainCategoryPage.viewCategoryByName(category);
        CartPage cartPage = categoryPage.addToCartByCategoryPage(symbol);
        productSymbolInCart = cartPage.viewCartPage().getProductSymbolInCart(0);

        assertTrue(productSymbolInCart.equals(symbol), "Nie udalo sie dodac produktu ze strony kategorii");

    }

    @Test
    public void changeQuantityOfProductInCartPageTest() {

        String category = categories[4];
        String symbol = "sa32";
        String quantity = "78";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        CartPage cartPage = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol);
        String productQuantityInCartPage = cartPage.changeProductQuantity(0, "78").getProductQuantity(0);

        assertTrue(quantity.equals(productQuantityInCartPage));

    }

    @Test
    public void calculateTotalAmountInCartPageTest() {

        String category1 = categories[5];
        String symbol1 = "PL43";
        String quantity1 = "33";

        String category2 = categories[5];
        String symbol2 = "PL58";
        String quantity2 = "20";

        String totalAmountNormal = "152,40";
        String totalAmountInCartPage = "";

        int deliveryType = 1;
        int realizationType = 2;

        //including delivery value and realization value to test data
        Float totalAmount = Float.parseFloat(totalAmountNormal.replace(",","."));
        if(deliveryType == 0 || deliveryType == 1) totalAmount += 15;
        if(deliveryType ==2) totalAmount += 20;
        if(realizationType ==1) totalAmount += 200;
        if(realizationType ==2) totalAmount += 450;
        String totalAmountFinal = totalAmount.toString();
        if(totalAmountFinal.contains(".")) totalAmountFinal = totalAmountFinal.replace(".",",");

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        CartPage cartPage = mainCategoryPage.viewCategoryByName(category1).addToCartByCategoryPage(symbol1).changeProductQuantity(0, quantity1);
        cartPage.acceptCookie();
        totalAmountInCartPage = cartPage.setDeliveryType(deliveryType).setRealizationType(realizationType).calculateTotalAmount().getTotalAmount();
        totalAmountInCartPage = mainCategoryPage.viewCategoryByName(category2).viewProductBySymbol(symbol2).addToCart().
                changeProductQuantity(1, quantity2).calculateTotalAmount().getTotalAmount().replace(" ","");

        if(totalAmountInCartPage.endsWith("0")&&totalAmountInCartPage.contains(",")) totalAmountInCartPage = totalAmountInCartPage.substring(0,totalAmountInCartPage.lastIndexOf("0"));

        assertTrue(totalAmountInCartPage.equals(totalAmountFinal), "Przeliczona suma w koszyku nie zgadza sie");
    }


    @Test
    public void setIncorrectQuantityCartPageTest() {

        String category = categories[4];
        String symbol = "sa32";
        String quantity = "15";
        String message = "Wartość nie może być mniejsza niż 20.";
        String productQuantityInCart = "";
        String quantityAllertMessageCartTable = "";


        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        CartPage cartPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).addToCart().
                changeProductQuantity(0, quantity);
        cartPage.acceptCookie();

        quantityAllertMessageCartTable = (String)jse.executeScript(
                        "const quantity = document.querySelector(\"input#cart_items_0_quantity\");"
                         + "message = quantity.validationMessage; return message;");

        productQuantityInCart = cartPage.calculateTotalAmount().getProductQuantity(0);

        assertTrue(quantityAllertMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy za malej ilosci");
        assertTrue(productQuantityInCart.contains(quantity), "Strona przeladowuje sie przy za małej ilosci");

    }


    @Test
    public void deleteOneProductFromCartPageTest(){

        String category = categories[4];
        String symbol = "sa32";
        String optionalSymbol = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();

        CartPage cartPage = mainCategoryPage.viewCategoryByName(category).
                            addToCartByCategoryPage(symbol).deleteFromCartPage(symbol);

        try{

            optionalSymbol = cartPage.getProductSymbolInCart(0);
        }
        catch (IndexOutOfBoundsException e2) {}

        Assertions.assertFalse(optionalSymbol.equals(symbol), "Nie udalo sie usunac produktu " + symbol);
    }

    @Test
    public void deleteSomeProductsFromCartPageTest(){

        String category1 = categories[4];
        String symbol1 = "sa32";
        String optionalSymbol1 = "";

        String category2 = categories[5];
        String symbol2= "PL47";
        String optionalSymbol2 = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();

        CartPage cartPage = mainCategoryPage.viewCategoryByName(category1).
                addToCartByCategoryPage(symbol1);
        cartPage = mainCategoryPage.viewCategoryByName(category2).addToCartByCategoryPage(symbol2).
                   deleteFromCartPage(symbol2).deleteFromCartPage(symbol1);

        try{

            optionalSymbol1 = cartPage.getProductSymbolInCart(1);
            optionalSymbol2 = cartPage.getProductSymbolInCart(0);
        }
        catch (IndexOutOfBoundsException e2) {}

        Assertions.assertFalse(optionalSymbol1.equals(symbol1)||optionalSymbol2.equals(symbol2),
                "Nie udalo sie usunac ktoregos z produktów " + symbol1 + " lub " + symbol2);
    }

    @Test
    public void orderProductWithoutCheckingDeliveryAndRealizationTypeCartPage() {

        String category = categories[4];
        String symbol = "sa32";
        String message = "Wybierz jedną z opcji.";
        String deliveryTypeMessageCartTable = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        OrderingPage orderingPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).
                addToCart().saveAndOrderProductsFromCart();

        deliveryTypeMessageCartTable = (String) jse.executeScript(
                "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                        + "message = delivery.validationMessage; return message;");

        assertTrue(deliveryTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranych opcjach dostawy");

    }

    @Test
    public void orderProductWithoutCheckingRealizationTypeCartPage() {

        String category = categories[4];
        String symbol = "sa32";
        int deliveryType = 0;

        String message = "Wybierz jedną z opcji.";
        String realizationTypeMessageCartTable = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        OrderingPage orderingPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).
                addToCart().setDeliveryType(deliveryType).saveAndOrderProductsFromCart();

        realizationTypeMessageCartTable = (String) jse.executeScript(
                "const realization = document.querySelector(\"#cart_realizationType_2\");"
                        + "message = realization.validationMessage; return message;");

        assertTrue(realizationTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranej opcji przesyłki");

    }

    @Test
    public void orderProductWithoutCheckingDeliveryTypeCartPage() {

        String category = categories[4];
        String symbol = "sa32";
        int realizationType = 1;

        String message = "Wybierz jedną z opcji.";
        String deliveryTypeMessageCartTable = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        OrderingPage orderingPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).
                addToCart().setRealizationType(realizationType).saveAndOrderProductsFromCart();

        deliveryTypeMessageCartTable = (String) jse.executeScript(
                "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                        + "message = delivery.validationMessage; return message;");

        assertTrue(deliveryTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranym trybie realizacji");

    }

}