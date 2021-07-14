package CucumberTests;

import POMTests.BaseTests;
import PageObjects.CartPage;
import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeProductsQuantity extends BaseTests {

    private MainCategoryPage mainCategoryPage;
    private CategoryPage categoryPage;
    private ProductPage productPage;
    private CartPage cartPage;
    String quantityActual;
    Float totalAmountActual;


    @Given("User is in Cart Page with One Product {string} from {string}")
    public void userIsInCartPageWithOneProductFrom(String symbol, String category) {

        this.testSetUp();
        mainCategoryPage = new MainCategoryPage(driver);
        cartPage = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol);
    }

    @When("User changes quantity to {int}")
    public void userChangesQuantityTo(int quantity) {

        quantityActual = cartPage.changeProductQuantity(0, Integer.toString(quantity)).getProductQuantity(0);
    }

    @Then("Quantity of {int} is displaying")
    public void quantityOfIsDisplayingNear(int quantity) {

        assertEquals(quantity, Integer.parseInt(quantityActual));
    }

    @When("User changes quantity to {int} and checks PayU option, Standard option and click Calculate and Save Button")
    public void userChangesQuantityToAndChecksPayUOptionStandardOptionAndClickCalculateAndSaveButton(int quantity) {

        CartPage cartPage = this.cartPage.changeProductQuantity(0, Integer.toString(quantity));
        cartPage.acceptCookie();
        cartPage = cartPage.setDeliveryType(0).setRealizationType(0).calculateTotalAmount();
    }

    @And("User clicks Add to Cart near Product {string}")
    public void userClicksAddToCartNearProduct(String symbol2) {

        cartPage = categoryPage.addToCartByCategoryPage(symbol2);
    }

    @And("User changes quantity to {int} and click Calculate and Save Button")
    public void userChangesQuantityToAndClickCalculateAndSaveButton(int quantity2) {

        cartPage = cartPage.changeProductQuantity(1,Integer.toString(quantity2)).calculateTotalAmount();
    }

    @Then("Quantity of {string} wynosi {int} and quantity of {string} wynosi {int} and total amount wynosi {float}")
    public void quantityOfWynosiAndQuantityOfWynosiAndTotalAmountWynosi(String symbol, int quantity, String symbol2, int quantity2, float totalAmount) {

        totalAmountActual = Float.parseFloat(cartPage.getTotalAmount().replace(",","."));
        assertEquals(totalAmount,totalAmountActual);
    }

    @And("User goes into Category Page {string}")
    public void userGoesIntoCategoryPage(String category2) {

        categoryPage = mainCategoryPage.viewCategoryByName(category2);
    }


}
