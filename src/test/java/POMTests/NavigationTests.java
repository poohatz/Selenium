package POMTests;

import PageObjects.CartPage;
import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationTests extends BaseTests{


    @org.testng.annotations.Test
    @Test
    public void navigateCategoryTest(){

        for(int i=0; i<categories.length; i++){

            String category = categories[i];
            String categoryName = mainCategoryPage
                    .viewCategoryByName(category)
                    .getCategoryName();

            assertTrue(category.equals(categoryName), "Strona kategorii nie działa prawidłowo");
        }
    }

    @org.testng.annotations.Test
    @Test
    public void navigateToProductPageTest() {

        String symbol = "CL06";
        String category = categories[9];

        String productSymbol = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol)
                .getProductSymbol();

        assertTrue(productSymbol.contains(symbol), "Strona produktu nie działa prawidłowo");
    }

    @org.testng.annotations.Test
    @Test
    public void navigateToNextProductTest(){

        String symbol = "mm04";
        String symbolNext = "mm07";
        String category = categories[1];

        ProductPage productPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol);

        String productSymbolNext = productPage
                .viewNextProductPage()
                .getProductSymbol();

        assertTrue(productSymbolNext.contains(symbolNext), "Przycisk 'Następny' nie działa prawidłowo");

    }

    @org.testng.annotations.Test
    @Test
    public void navigateToPreviousProductTest(){

        String symbol = "mm04";
        String symbolPrevious = "MM20200003";
        String category = categories[1];

        ProductPage productPage = mainCategoryPage
                .viewCategoryByName(category)
                .viewProductBySymbol(symbol);

        String productSymbolPrevious = productPage
                .viewPreviousProductPage()
                .getProductSymbol();

        assertTrue(productSymbolPrevious.contains(symbolPrevious), "Przycisk 'Poprzedni' nie działa prawidłowo");

    }

    @org.testng.annotations.Test
    @Test
    public void navigateAllProductsByNextButtonTest(){

        String category = categories[8];

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
    @Test
    public void navigateFromCartToMainPageAnRevertTest(){

        String category = categories[4];
        String symbol = "sa32";

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
