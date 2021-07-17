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

public class AddProductsToCart extends BaseTests {

    private CategoryPage categoryPage;
    private CartPage cartPage;

    @Given("User is in Main Category Page")
    public void userIsInMainCategoryPage() {

        this.testSetUp();
        mainCategoryPage.viewMainPage();
    }

    @When("User goes to Category Page {string}")
    public void userGoesToCategoryPage(String category) {

        categoryPage = mainCategoryPage.viewCategoryByName(category);
    }

    @And("User goes to Product Page {string} and clicks Add to Cart")
    public void userGoesToProductPageAndClicksAddToCart(String symbol) {

        cartPage = categoryPage
                .viewProductBySymbol(symbol)
                .addToCart();
    }

    @Then("Cart Page with Product {string} is displayed")
    public void cartPageWithProductIsDisplayed(String symbol) {

        String actualSymbol = cartPage.getProductSymbolInCart(0);
        assertEquals(actualSymbol,symbol);
    }

    @Then("Cart Page with Product {string} and Product {string} is displayed")
    public void cartPageWithProductAndProductIsDisplayed(String symbol, String symbol2) {

        String actualSymbol = cartPage.getProductSymbolInCart(0);
        String actualSymbol2 = cartPage.getProductSymbolInCart(1);

        assertTrue(actualSymbol.equals(symbol) && actualSymbol2.equals(symbol2));
    }

}
