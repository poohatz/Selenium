package POMTests;

import PageObjects.CartPage;
import PageObjects.MainCategoryPage;
import PageObjects.OrderingPage;
import org.junit.jupiter.api.Test;
import org.testng.annotations.Factory;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderingTests extends BaseTests {

    private String name;
    private String address;
    private String city;
    private String code;
    private String email;
    private String email2;
    private String email3;
    private String email4;
    private String tel;
    private String comments;
    private Boolean invoice;
    private String nip;
    private String category;
    private String symbol;

    public OrderingTests(String name, String address, String city, String code, String email, String email2, String email3,
                         String email4, String tel, String comments, Boolean invoice, String nip, String category, String symbol) {

        this.name = name;
        this.address = address;
        this.city = city;
        this.code = code;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.email4 = email4;
        this.tel = tel;
        this.comments = comments;
        this.invoice = invoice;
        this.nip = nip;
        this.category = category;
        this.symbol = symbol;
    }

    @Factory
    public static Object[] orderingTestsDataFactoryMethod() {

        OrderingTests firstOrderingTests = new OrderingTests("Anna Kowal", "Lejka 3", "Sopot", "11-123",
                "anna@wp.pl", "annawp.pl","@wop.pl",";@asdasd","123123213","",true,
                "12309812398","Vintage&Nature","sa32");

        OrderingTests secondOrderingTests = new OrderingTests("Marek Kaczka", "Komorowska 12/3", "Bydgoszcz", "11-823",
                "marko@op.pl", "markowp.pl","@wmarko.pl",";@asdasd","600345313","mjbjbmbmhbm",true,
                "12376464368","Folk&Boho","FB03");

        return new Object[]{
                firstOrderingTests,
                secondOrderingTests};
    }

    @org.testng.annotations.Test
    public void orderProductsFromCartPageHappyPath() {

        int deliveryType = 0;
        int realizationType = 1;

        String heading = "Krok 1 z 2: Podaj dane do wysyłki";
        String headingStepOneOrderingPage = "";

        headingStepOneOrderingPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .setRealizationType(realizationType)
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart()
                .getHeadingOrderingPage();

        assertEquals(heading, headingStepOneOrderingPage, "Naglowek w pierwszym kroku zamowienia nie zgadza sie");

    }

    @org.testng.annotations.Test
    public void fillingAddressFormStepOneOrderingPageTestNegativeWays() {

        int deliveryType = 0;
        int realizationType = 1;

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

        OrderingPage orderingPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .setRealizationType(realizationType)
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart()
                .fillAddressInStepOneForm(address)
                .fillCityInStepOneForm(city)
                .fillCodeInStepOneForm(code)
                .fillEmailInStepOneForm(email)
                .fillTelInStepOneForm(tel)
                .isInvoiceNeededOptionSteoOneForm(invoice)
                .fillNipInStepOneForm(nip)
                .saveAndContinueOrderingPage();

        nameAlertMessage = (String) jse.executeScript(
                "const name = document.querySelector(\"#shipping_details_name\");"
                        + "message = name.validationMessage; return message;");

        assertEquals(message1, nameAlertMessage, "Komunikat jest bledny");


        //case 2 :: field address is empty

        orderingPage = orderingPage
                .clearAddressInStepOneForm()
                .fillNameInStepOneForm(name)
                .saveAndContinueOrderingPage();

        addressAlertMessage = (String) jse.executeScript(
                "const address = document.querySelector(\"#shipping_details_street\");"
                        + "message = address.validationMessage; return message;");

        assertEquals(message1, addressAlertMessage, "Komunikat jest bledny");


        //case 3 :: field code is empty

        orderingPage = orderingPage
                .clearCodeInStepOneForm()
                .fillAddressInStepOneForm(address)
                .saveAndContinueOrderingPage();

        codeAlertMessage = (String) jse.executeScript(
                "const code = document.querySelector(\"#shipping_details_postalCode\");"
                        + "message = code.validationMessage; return message;");

        assertEquals(message1, codeAlertMessage, "Komunikat jest bledny");


        //case 4 :: field email is empty

        orderingPage = orderingPage
                .clearEmailInStepOneForm()
                .fillCodeInStepOneForm(code)
                .saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");

        assertEquals(message1, emailAlertMessage, "Komunikat jest bledny");


        //case 5 :: field tel is empty

        orderingPage = orderingPage
                .clearTelInStepOneForm()
                .fillEmailInStepOneForm(email)
                .saveAndContinueOrderingPage();

        telAlertMessage = (String) jse.executeScript(
                "const tel = document.querySelector(\"#shipping_details_phone\");"
                        + "message = tel.validationMessage; return message;");

        assertEquals(message1, telAlertMessage, "Komunikat jest bledny");


        //case 6 :: field email has not got '@'

        orderingPage = orderingPage
                .clearEmailInStepOneForm()
                .fillTelInStepOneForm(tel)
                .fillEmailInStepOneForm(email2)
                .saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");

        assertEquals(message2, emailAlertMessage, "Komunikat jest bledny");


        //case 7 :: field email has got nothing before '@'

        orderingPage = orderingPage
                .clearEmailInStepOneForm()
                .fillEmailInStepOneForm(email3)
                .saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");

        assertEquals(message3, emailAlertMessage, "Komunikat jest bledny");


        //case 8 :: field email has got wrong sings

        orderingPage = orderingPage
                .clearEmailInStepOneForm()
                .fillEmailInStepOneForm(email4)
                .saveAndContinueOrderingPage();

        emailAlertMessage = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");

        assertEquals(message4, emailAlertMessage, "Komunikat jest bledny");

    }

    @org.testng.annotations.Test
    public void fillingAddressFormStepOneOrderingPageTestPositiveWays() {

        int deliveryType = 0;
        int realizationType = 1;

        String heading = "Krok 2 z 2: Sprawdź swoje zamówienie";
        String headingStepTwoOrderingPage = "";

        //case 1 :: all fields filled in

        OrderingPage orderingPage = new OrderingPage(driver);

        headingStepTwoOrderingPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .setRealizationType(realizationType)
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart()
                .fillNameInStepOneForm(name)
                .fillAddressInStepOneForm(address)
                .fillCityInStepOneForm(city)
                .fillCodeInStepOneForm(code)
                .fillEmailInStepOneForm(email)
                .fillTelInStepOneForm(tel)
                .isInvoiceNeededOptionSteoOneForm(invoice)
                .fillNipInStepOneForm(nip)
                .saveAndContinueOrderingPage()
                .getHeadingOrderingPage();

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

        headingStepTwoOrderingPage = orderingPage
                .returnToStepOneOrderingPage()
                .clearNipInStepOneForm()
                .saveAndContinueOrderingPage()
                .getHeadingOrderingPage();

        assertEquals(heading, headingStepTwoOrderingPage, "Naglowek w drugim kroku zamowienia nie zgadza sie");


        //case 4 :: only field comments is empty

        headingStepTwoOrderingPage = orderingPage
                .returnToStepOneOrderingPage()
                .clearCommentsInStepOneForm()
                .isInvoiceNeededOptionSteoOneForm(true)
                .fillNipInStepOneForm(nip)
                .saveAndContinueOrderingPage()
                .getHeadingOrderingPage();

        assertEquals(heading, headingStepTwoOrderingPage, "Naglowek w drugim kroku zamowienia nie zgadza sie");

    }

    @org.testng.annotations.Test
    public void finalizeOrderAndPayLaterInOrderingPagePositiveWay(){

        int deliveryType = 1 ;
        int realizationType = 1;

        String heading = "Dziękujemy za złożenie zamówienia w naszej firmie!";
        String finalOrderConfirmationHeading = "";

        finalOrderConfirmationHeading = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .setRealizationType(realizationType)
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart()
                .fillNameInStepOneForm(name)
                .fillAddressInStepOneForm(address)
                .fillCityInStepOneForm(city)
                .fillCodeInStepOneForm(code)
                .fillEmailInStepOneForm(email)
                .fillTelInStepOneForm(tel)
                .isInvoiceNeededOptionSteoOneForm(invoice)
                .fillNipInStepOneForm(nip)
                .saveAndContinueOrderingPage()
                .checkRodoConfirmation()
                .checkTermsConfirmation()
                .finalizeAndConfirmOrder()
                .getFinalHeadingOrderingPage();

        assertEquals(heading, finalOrderConfirmationHeading, "Końcowe potwierdzenie zamówienia wyświetla się niepoprawnie");

    }

    @org.testng.annotations.Test
    public void finalizeOrderAndPayLaterInOrderingPageNegativeWays(){

        int deliveryType = 1 ;
        int realizationType = 1;

        String message1 = "Zaznacz to pole, jeśli chcesz kontynuować.";
        String termsAlertMessage = "";
        String rodoAlertMessage = "";


        //case 1 :: Finalizing without accepting terms and rodo

        OrderingPage orderingPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .setRealizationType(realizationType)
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart()
                .fillNameInStepOneForm(name)
                .fillAddressInStepOneForm(address)
                .fillCityInStepOneForm(city)
                .fillCodeInStepOneForm(code)
                .fillEmailInStepOneForm(email)
                .fillTelInStepOneForm(tel)
                .isInvoiceNeededOptionSteoOneForm(invoice)
                .fillNipInStepOneForm(nip)
                .saveAndContinueOrderingPage()
                .finalizeAndConfirmOrder();

        termsAlertMessage = (String) jse.executeScript(
                "const terms = document.querySelector(\"#terms\");"
                        + "message = terms.validationMessage; return message;");

        assertEquals(message1, termsAlertMessage, "Komunikat jest bledny");


        //case 2 :: Finalizing without accepting terms

        orderingPage = orderingPage
                .viewMainPage()
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .setRealizationType(realizationType)
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart()
                .saveAndContinueOrderingPage()
                .checkRodoConfirmation()
                .finalizeAndConfirmOrder();

        termsAlertMessage = (String) jse.executeScript(
                "const terms = document.querySelector(\"#terms\");"
                        + "message = terms.validationMessage; return message;");

        assertEquals(message1, termsAlertMessage, "Komunikat jest bledny");


        //case 3 :: Finalizing without accepting rodo

        orderingPage = orderingPage
                .viewMainPage()
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .setRealizationType(realizationType)
                .setDeliveryType(deliveryType)
                .saveAndOrderProductsFromCart()
                .saveAndContinueOrderingPage()
                .checkTermsConfirmation()
                .finalizeAndConfirmOrder();

        rodoAlertMessage = (String) jse.executeScript(
                "const rodo = document.querySelector(\"#personal-data\");"
                        + "message = rodo.validationMessage; return message;");

        assertEquals(message1, rodoAlertMessage, "Komunikat jest bledny");
    }

}
