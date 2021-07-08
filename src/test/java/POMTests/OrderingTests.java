package POMTests;

import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.OrderingPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderingTests extends BaseTests {

    @Test
    public void orderProductWithoutCheckingDeliveryAndRealizationTypeCartPage(){

        String category = categories[4];
        String symbol = "sa32";
        String message = "Wybierz jedną z opcji.";
        String deliveryTypeMessageCartTable = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        OrderingPage orderingPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).
                    addToCart().saveAndOrderProductsFromCart();

        try {

            JavascriptExecutor jse = (JavascriptExecutor) driver;

            if (driver instanceof JavascriptExecutor) {

                deliveryTypeMessageCartTable = (String)jse.executeScript(
                        "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                                + "message = delivery.validationMessage;" +
                                "return message;");

            } else {

                throw new IllegalStateException("This driver does not support JavaScript!");
            }

        } catch (Exception e) {}

        assertTrue(deliveryTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranych opcjach dostawy");

    }

    @Test
    public void orderProductWithoutCheckingRealizationTypeCartPage(){

        String category = categories[4];
        String symbol = "sa32";
        String message = "Wybierz jedną z opcji.";
        String realizationTypeMessageCartTable = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        OrderingPage orderingPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).
                addToCart().setDeliveryType().saveAndOrderProductsFromCart();

        try {

            JavascriptExecutor jse = (JavascriptExecutor) driver;

            if (driver instanceof JavascriptExecutor) {

                realizationTypeMessageCartTable = (String)jse.executeScript(
                        "const realization = document.querySelector(\"#cart_realizationType_2\");"
                                + "message = realization.validationMessage;" +
                                "return message;");
            } else {

                throw new IllegalStateException("This driver does not support JavaScript!");
            }

        } catch (Exception e) {}

        assertTrue(realizationTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranej opcji przesyłki");

    }

    @Test
    public void orderProductWithoutCheckingDeliveryTypeCartPage(){

        String category = categories[4];
        String symbol = "sa32";
        String message = "Wybierz jedną z opcji.";
        String deliveryTypeMessageCartTable = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        OrderingPage orderingPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol).
                addToCart().setRealizationType().saveAndOrderProductsFromCart();

        try {

            JavascriptExecutor jse = (JavascriptExecutor) driver;

            if (driver instanceof JavascriptExecutor) {

                deliveryTypeMessageCartTable = (String)jse.executeScript(
                        "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                                + "message = delivery.validationMessage;" +
                                "return message;");

            } else {

                throw new IllegalStateException("This driver does not support JavaScript!");
            }

        } catch (Exception e) {}

        assertTrue(deliveryTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranym trybie realizacji");

    }

    @Test
    public void orderProductsFromCartPageHappyPath(){

        String category = categories[4];
        String symbol = "sa32";
        String heading = "Krok 1 z 2: Podaj dane do wysyłki";
        String headingStepOneOrderingPage = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        headingStepOneOrderingPage = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol).
                setRealizationType().setDeliveryType().saveAndOrderProductsFromCart().getHeadingStepOneOrderingPage();

        assertEquals(heading, headingStepOneOrderingPage, "Naglowek w pierwszym kroku zamowienia nie zgadza sie");

    }
}
