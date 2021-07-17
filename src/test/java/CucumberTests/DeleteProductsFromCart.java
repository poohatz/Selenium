package CucumberTests;

import POMTests.BaseTests;
import PageObjects.CartPage;
import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class DeleteProductsFromCart extends BaseTests {

    private CartPage cartPage;
    String optionalSymbol = "";
    String optionalSymbol2 = "";

    @Given("User is in Cart_page with One Product {string} from {string}")
    public void userIsInCart_pageWithOneProductFrom(String symbol, String category) {

        this.testSetUp();

        cartPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol);
    }

    @When("User clicks Delete button near {string} Product")
    public void userClicksDeleteButtonNearProduct(String symbol) {

        cartPage = cartPage.deleteFromCartPage(symbol);
    }



    @Then("Product {string} was deleted")
    public void productWasDeleted(String symbol) {

        try{

            optionalSymbol = cartPage.getProductSymbolInCart(0);
        }
        catch (IndexOutOfBoundsException e2) {}

        Assertions.assertFalse(optionalSymbol.equals(symbol));

    }

    @Given("User is in Cart_page with Two Product {string} from {string} and {string} from {string}")
    public void userIsInCart_pageWithTwoProductFromAndFrom(String symbol, String category, String symbol2, String category2) {

        this.testSetUp();

        cartPage = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol);

        cartPage = mainCategoryPage
                .viewCategoryByName(category2)
                .addToCartByCategoryPage(symbol2);

    }

    @Then("Products {string} and {string} were deleted")
    public void productsAndWereDeleted(String symbol, String symbol2) {

        try{

            optionalSymbol = cartPage.getProductSymbolInCart(1);
            optionalSymbol2 = cartPage.getProductSymbolInCart(0);
        }
        catch (IndexOutOfBoundsException e2) {}

        Assertions.assertFalse(optionalSymbol.equals(symbol)||optionalSymbol2.equals(symbol2));
    }
}
