package POMTests;

import PageObjects.CartPage;
import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import org.testng.annotations.Factory;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationTests extends BaseTests{

    private String category;
    private String symbol;
    private String symbolNext;
    private String symbolPrevious;

    public NavigationTests(String category, String symbol, String symbolNext, String symbolPrevious) {

        this.category = category;
        this.symbol = symbol;
        this.symbolNext = symbolNext;
        this.symbolPrevious = symbolPrevious;
    }

    @Factory
    public static Object[] navigationTestsDataFactoryMethod() {

        NavigationTests firstNavigationTests = new NavigationTests("Mystic Moment", "mm04", "mm07", "MM20200003");
        NavigationTests secondNavigationTests = new NavigationTests("Passion&Fun", "MP02", "MP03", "MP01");

        return new Object[]{
                firstNavigationTests,
                secondNavigationTests};
    }

    @org.testng.annotations.Test
    @Description("Checks if navigation between categories works on")
    public void navigateCategoryTest(){

        for(int i=0; i<categories.length; i++){

            String category_actual = categories[i];
            String categoryName = mainCategoryPage
                    .viewCategoryByName(category_actual)
                    .getCategoryName();

            assertTrue(category_actual.equals(categoryName), "Strona kategorii nie działa prawidłowo");
        }

    }

    @org.testng.annotations.Test
    @Description("Tries to navigate to Product Page")
    public void navigateToProductPageTest() {

        String productSymbol = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .getProductSymbol();

        assertTrue(productSymbol.contains(symbol), "Strona produktu nie działa prawidłowo");
    }

    @org.testng.annotations.Test
    @Description("Checks if button next works properly")
    public void navigateToNextProductTest(){

        ProductPage productPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol);

        String productSymbolNext = productPage
                .viewNextProductPage()
                .getProductSymbol();

        assertTrue(productSymbolNext.contains(symbolNext), "Przycisk 'Następny' nie działa prawidłowo");

    }

    @org.testng.annotations.Test
    @Description("Checks if button previous works properly")
    public void navigateToPreviousProductTest(){

        ProductPage productPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol);

        String productSymbolPrevious = productPage
                .viewPreviousProductPage()
                .getProductSymbol();

        assertTrue(productSymbolPrevious.contains(symbolPrevious), "Przycisk 'Poprzedni' nie działa prawidłowo");

    }

    @org.testng.annotations.Test
    @Description("Checks if all products display properly while using next button")
    public void navigateAllProductsByNextButtonTest(){


        CategoryPage categoryPage = mainCategoryPage.viewCategoryByName(category);

        String productSymbolLast = categoryPage.findLastProductSymbol();
        String symbolLast = productSymbolLast;

        ProductPage productPage = categoryPage.viewFirstProductPage();

        String productSymbolFirst = productPage.getProductSymbol();
        String productSymbolNext = productSymbolFirst;


        do {
            productSymbolNext = productPage.viewNextProductPage().getProductSymbol();
            System.out.println(productSymbolNext);
        }
        while (!productSymbolNext.contains(symbolLast));

        assertTrue(productSymbolNext.contains(symbolLast), "Przejście przez wszystkie produkty nie działa prawidłowo");

    }


    @org.testng.annotations.Test
    @Description("Checks if it is possible to navigate from cart to main page and then come back without loss of content")
    public void navigateFromCartToMainPageAnRevertTest(){

        String productSymbolInCart = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .addToCart()
                .getProductSymbolInCart(0);

        mainCategoryPage.viewMainPage();

        String productSymbolAfterRevert = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .addToCart()
                .getProductSymbolInCart(0);

        assertEquals(productSymbolAfterRevert, productSymbolInCart, "Nawigacja do strony glownej nie dziala, badz zawartosc koszyka nie zapisuje sie");

    }

}
