package CucumberTests;

import POMTests.BaseTests;
import PageObjects.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterAll;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderProductsFromCart extends BaseTests {

    private MainCategoryPage mainCategoryPage;
    private CategoryPage categoryPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private OrderingPage orderingPage;
    private String email;
    private String confirmationOrderMessage = "Dziękujemy za złożenie zamówienia w naszej firmie!";

    @Given("User is in Cart_Page with One Product {string} from {string}")
    public void user_is_in_Cart_Page_with_One_Product_from(String symbol, String category) {

        this.testSetUp();
        mainCategoryPage = new MainCategoryPage(driver);
        cartPage = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol);
    }



    @When("User changes quantity to {int}, checks cash on delivery option, standard mode option and clicks Save and Order button")
    public void userChangesQuantityToChecksCashOnDeliveryOptionStandardModeOptionAndClicksSaveAndOrderButton(int quantity) {

        orderingPage = cartPage.changeProductQuantity(0,Integer.toString(quantity)).setDeliveryType(2).setRealizationType(0).saveAndOrderProductsFromCart();
    }


    @And("User enters correct data to Address Form and clicks Continue")
    public void userEntersCorrectDataToAddressFormAndClicksContinue(DataTable dataTable) {

        Map<String,String> data = dataTable.asMap(String.class,String.class);
        String name = data.get("name");
        String address = data.get("address");
        String code = data.get("code");
        String city = data.get("city");
        String email = data.get("email");
        String tel = data.get("tel");
        String comments = data.get("comments");
        String nip = data.get("nip");

        orderingPage = orderingPage.fillNameInStepOneForm(name).
                fillAddressInStepOneForm(address).fillCityInStepOneForm(city).
                fillCodeInStepOneForm(code).fillEmailInStepOneForm(email).fillTelInStepOneForm(tel).
                isInvoiceNeededOptionSteoOneForm(true).fillNipInStepOneForm(nip).saveAndContinueOrderingPage();

    }


    @And("User checks Terms Confirmation option, RODO accept option and clicks Finalize and Confirm Order")
    public void userChecksTermsConfirmationOptionRODOAcceptOptionAndClicksFinalizeAndConfirmOrder() {

        orderingPage = orderingPage.checkRodoConfirmation().checkTermsConfirmation().finalizeAndConfirmOrder();
    }


    @Then("Message informing about making order is displayed")
    public void messageInformingAboutMakingOrderIsDisplayed() {

        String confirmationOrderMessageActual = orderingPage.getFinalHeadingOrderingPage();
        assertEquals(confirmationOrderMessageActual, confirmationOrderMessage);
    }


    @When("User changes quantity to {int}, checks standard mode option and clicks Save and Order button")
    public void userChangesQuantityChecksStandardModeOptionAndClicksSaveAndOrderButton(int quantity) {

        orderingPage = cartPage.changeProductQuantity(0,Integer.toString(quantity)).setRealizationType(0).saveAndOrderProductsFromCart();
    }

    @Then("Alert informing User must choice Delivery Option")
    public void alertInformingUserMustChoiceDeliveryOption() {


        String deliveryTypeAlertActual = (String) jse.executeScript(
                "const delivery = document.querySelector(\"#cart_deliveryType_4\");"
                        + "message = delivery.validationMessage; return message;");
        String alert = "Wybierz jedną z opcji.";

        assertEquals(deliveryTypeAlertActual, alert);
    }

    @And("User enters data with invalid email to Address Form and clicks Continue")
    public void userEntersDataWithInvalidEmailToAddressFormAndClicksContinue(DataTable dataTable) {

        Map<String,String> data = dataTable.asMap(String.class,String.class);
        String name = data.get("name");
        String address = data.get("address");
        String code = data.get("code");
        String city = data.get("city");
        email = data.get("email");
        String tel = data.get("tel");
        String comments = data.get("comments");
        String nip = data.get("nip");

        orderingPage = orderingPage.fillNameInStepOneForm(name).
                fillAddressInStepOneForm(address).fillCityInStepOneForm(city).
                fillCodeInStepOneForm(code).fillEmailInStepOneForm(email).fillTelInStepOneForm(tel).
                isInvoiceNeededOptionSteoOneForm(true).fillNipInStepOneForm(nip).saveAndContinueOrderingPage();
    }

    @Then("Alert informing User must enter correct email")
    public void alertInformingUserMustEnterCorrectEmail() {


        String emailAlertActual = (String) jse.executeScript(
                "const email = document.querySelector(\"#shipping_details_email\");"
                        + "message = email.validationMessage; return message;");
        String alert = "Uwzględnij znak „@” w adresie e-mail. W adresie „" + email + "” brakuje znaku „@”.";

        assertEquals(emailAlertActual, alert);

    }

}
