package POMTests;

import PageObjects.CartPage;
import PageObjects.MainCategoryPage;
import PageObjects.OrderingPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderingTests extends BaseTests {

    @Test
    public void orderProductWithoutCheckingDeliveryAndRealizationTypeCartPage() {

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

                deliveryTypeMessageCartTable = (String) jse.executeScript(
                        "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                                + "message = delivery.validationMessage;" +
                                "return message;");

            } else {

                throw new IllegalStateException("This driver does not support JavaScript!");
            }

        } catch (Exception e) {
        }

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

        try {

            JavascriptExecutor jse = (JavascriptExecutor) driver;

            if (driver instanceof JavascriptExecutor) {

                realizationTypeMessageCartTable = (String) jse.executeScript(
                        "const realization = document.querySelector(\"#cart_realizationType_2\");"
                                + "message = realization.validationMessage;" +
                                "return message;");
            } else {

                throw new IllegalStateException("This driver does not support JavaScript!");
            }

        } catch (Exception e) {
        }

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

        try {

            JavascriptExecutor jse = (JavascriptExecutor) driver;

            if (driver instanceof JavascriptExecutor) {

                deliveryTypeMessageCartTable = (String) jse.executeScript(
                        "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                                + "message = delivery.validationMessage;" +
                                "return message;");

            } else {

                throw new IllegalStateException("This driver does not support JavaScript!");
            }

        } catch (Exception e) {
        }

        assertTrue(deliveryTypeMessageCartTable.equals(message), "Nie wyswietla sie poprawny alert przy nie wybranym trybie realizacji");

    }

    @Test
    public void orderProductsFromCartPageHappyPath() {

        String category = categories[4];
        String symbol = "sa32";
        int deliveryType = 0;
        int realizationType = 1;

        String heading = "Krok 1 z 2: Podaj dane do wysyłki";
        String headingStepOneOrderingPage = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        headingStepOneOrderingPage = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol).
                setRealizationType(realizationType).setDeliveryType(deliveryType).saveAndOrderProductsFromCart().getHeadingOrderingPage();

        assertEquals(heading, headingStepOneOrderingPage, "Naglowek w pierwszym kroku zamowienia nie zgadza sie");

    }

    @Test
    public void fillingAddressFormStepOneOrderingPageTestNegativeWays() {

        String name = "Maria Biedronka";
        String address = "Kowalewskiego 3/4";
        String city = "Sopot";
        String code = "83-123";
        String email = "maria1@o2.pl";
        String email2 = "maria1o2.pl";
        String email3 = "@maria1o2.pl";
        String email4 = "maria&;#@o2.pl";
        String tel = "600123098";
        String comments = "Prosze o formularz";
        Boolean invoice = true;
        String nip = "7333300440";
        int deliveryType = 0;
        int realizationType = 1;

        String category = categories[4];
        String symbol = "sa32";
        String message1 = "Wypełnij to pole.";
        String message2 = "Uwzględnij znak „@” w adresie e-mail. W adresie „" + email2 + "” brakuje znaku „@”.";
        String message3 = "Podaj część przed znakiem „@”. Adres „" + email3 + "” jest niepełny.";
        String message4 = "Część przed znakiem „@” nie może zawierać symbolu „;”.";

        String nameAlertMessage = "";
        String addressAlertMessage = "";
        String codeAlertMessage = "";
        String telAlertMessage = "";
        String emailAlertMessage = "";
        String cityAlertMessage = "";
        String commentsAlertMessage = "";
        String nipAlertMessage = "";


        //case 1 :: field name is empty

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();

        OrderingPage orderingPage = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol).
                setRealizationType(realizationType).setDeliveryType(deliveryType).saveAndOrderProductsFromCart().fillAddressInStepOneForm(address).fillCityInStepOneForm(city).
                fillCodeInStepOneForm(code).fillEmailInStepOneForm(email).fillTelInStepOneForm(tel).
                isInvoiceNeededOptionSteoOneForm(invoice).fillNipInStepOneForm(nip).saveAndContinueOrderingPage();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        nameAlertMessage = (String) jse.executeScript(
                "const name = document.querySelector(\"#shipping_details_name\");"
                        + "message = name.validationMessage; return message;");

        assertEquals(message1, nameAlertMessage, "Komunikat jest bledny");


        //case 2 :: field address is empty

        orderingPage = orderingPage.clearAddressInStepOneForm().fillNameInStepOneForm(name).saveAndContinueOrderingPage();

        addressAlertMessage = (String) jse.executeScript(
                "const address = document.querySelector(\"#shipping_details_street\");"
                        + "message = address.validationMessage; return message;");

        assertEquals(message1, addressAlertMessage, "Komunikat jest bledny");


        //case 3 :: field code is empty

        orderingPage = orderingPage.clearCodeInStepOneForm().fillAddressInStepOneForm(address).saveAndContinueOrderingPage();

        codeAlertMessage = (String) jse.executeScript(
                "const code = document.querySelector(\"#shipping_details_postalCode\");"
                        + "message = code.validationMessage; return message;");

        assertEquals(message1, codeAlertMessage, "Komunikat jest bledny");


        //case 4 :: field email is empty

        orderingPage = orderingPage.clearEmailInStepOneForm().fillCodeInStepOneForm(code).saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");

        assertEquals(message1, emailAlertMessage, "Komunikat jest bledny");


        //case 5 :: field tel is empty

        orderingPage = orderingPage.clearTelInStepOneForm().fillEmailInStepOneForm(email).saveAndContinueOrderingPage();

        telAlertMessage = (String) jse.executeScript(
                "const tel = document.querySelector(\"#shipping_details_phone\");"
                        + "message = tel.validationMessage; return message;");

        assertEquals(message1, telAlertMessage, "Komunikat jest bledny");


        //case 6 :: field email has not got '@'

        orderingPage = orderingPage.clearEmailInStepOneForm().fillTelInStepOneForm(tel).fillEmailInStepOneForm(email2).saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");
        System.out.println(emailAlertMessage);

        assertEquals(message2, emailAlertMessage, "Komunikat jest bledny");


        //case 7 :: field email has got nothing before '@'

        orderingPage = orderingPage.clearEmailInStepOneForm().fillEmailInStepOneForm(email3).saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");
        System.out.println(emailAlertMessage);

        assertEquals(message3, emailAlertMessage, "Komunikat jest bledny");


        //case 8 :: field email has got wrong sings

        orderingPage = orderingPage.clearEmailInStepOneForm().fillEmailInStepOneForm(email4).saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");
        System.out.println(emailAlertMessage);

        assertEquals(message4, emailAlertMessage, "Komunikat jest bledny");

    }

    @Test
    public void fillingAddressFormStepOneOrderingPageTestPositiveWays() {

        String name = "Maria Biedronka";
        String address = "Kowalewskiego 3/4";
        String city = "Sopot";
        String code = "83-123";
        String email = "maria1@o2.pl";
        String tel = "600123098";
        String comments = "Prosze o formularz";
        Boolean invoice = true;
        String nip = "7333300440";
        int deliveryType = 0;
        int realizationType = 1;

        String category = categories[4];
        String symbol = "sa32";
        String heading = "Krok 2 z 2: Sprawdź swoje zamówienie";
        String headingStepTwoOrderingPage = "";

        //case 1 :: all fields filled in

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        OrderingPage orderingPage = new OrderingPage(driver);
        mainCategoryPage.acceptCookie();

        headingStepTwoOrderingPage = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol).
                setRealizationType(realizationType).setDeliveryType(deliveryType).saveAndOrderProductsFromCart().fillNameInStepOneForm(name).fillAddressInStepOneForm(address).fillCityInStepOneForm(city).
                fillCodeInStepOneForm(code).fillEmailInStepOneForm(email).fillTelInStepOneForm(tel).
                isInvoiceNeededOptionSteoOneForm(invoice).fillNipInStepOneForm(nip).saveAndContinueOrderingPage().getHeadingOrderingPage();

        assertEquals(heading, headingStepTwoOrderingPage, "Naglowek w drugim kroku zamowienia nie zgadza sie");

        //case 2 :: checking if address data in summary is the same

        Map<String, String> summaryDataFromOrderingPage = orderingPage.getAddressDataFromStepTwoOrderingPage();

        assertEquals(name, summaryDataFromOrderingPage.get("name"), "Imie w drugim kroku zamowienia nie zgadza sie");
        assertEquals(address, summaryDataFromOrderingPage.get("address"), "Adres w drugim kroku zamowienia nie zgadza sie");
        assertEquals(code, summaryDataFromOrderingPage.get("code"), "Kod w drugim kroku zamowienia nie zgadza sie");
        assertEquals(city, summaryDataFromOrderingPage.get("city"), "Miasto w drugim kroku zamowienia nie zgadza sie");
        assertEquals(email, summaryDataFromOrderingPage.get("email"), "Email w drugim kroku zamowienia nie zgadza sie");
        assertEquals(tel, summaryDataFromOrderingPage.get("tel"), "Telefon w drugim kroku zamowienia nie zgadza sie");
        assertEquals(nip, summaryDataFromOrderingPage.get("nip"), "Nip w drugim kroku zamowienia nie zgadza sie");

        //case 3 :: only field nip is empty

        headingStepTwoOrderingPage = orderingPage.returnToStepOneOrderingPage().clearNipInStepOneForm().saveAndContinueOrderingPage().getHeadingOrderingPage();

        assertEquals(heading, headingStepTwoOrderingPage, "Naglowek w drugim kroku zamowienia nie zgadza sie");


        //case 4 :: only field comments is empty

        headingStepTwoOrderingPage = orderingPage.returnToStepOneOrderingPage().clearCommentsInStepOneForm().isInvoiceNeededOptionSteoOneForm(true).fillNipInStepOneForm(nip).saveAndContinueOrderingPage().getHeadingOrderingPage();

        assertEquals(heading, headingStepTwoOrderingPage, "Naglowek w drugim kroku zamowienia nie zgadza sie");

    }

    @Test
    public void finalizeOrderAndPayLaterInOrderingPagePositiveWay(){

        String name = "Maria Biedronka";
        String address = "Kowalewskiego 3/4";
        String city = "Sopot";
        String code = "83-123";
        String email = "maria1@o2.pl";
        String tel = "600123098";
        String comments = "Prosze o formularz";
        Boolean invoice = true;
        String nip = "7333300440";
        int deliveryType = 1 ;
        int realizationType = 1;

        String category = categories[4];
        String symbol = "sa32";
        String heading = "Dziękujemy za złożenie zamówienia w naszej firmie!";
        String finalOrderConfirmationHeading = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);

        finalOrderConfirmationHeading = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol).
                setRealizationType(realizationType).setDeliveryType(deliveryType).saveAndOrderProductsFromCart().fillNameInStepOneForm(name).
                fillAddressInStepOneForm(address).fillCityInStepOneForm(city).
                fillCodeInStepOneForm(code).fillEmailInStepOneForm(email).fillTelInStepOneForm(tel).
                isInvoiceNeededOptionSteoOneForm(invoice).fillNipInStepOneForm(nip).saveAndContinueOrderingPage().
                checkRodoConfirmation().checkTermsConfirmation().finalizeAndConfirmOrder().getFinalHeadingOrderingPage();

        assertEquals(heading, finalOrderConfirmationHeading, "Końcowe potwierdzenie zamówienia wyświetla się niepoprawnie");

    }

}
