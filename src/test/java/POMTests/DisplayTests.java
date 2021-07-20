package POMTests;

import PageObjects.CartPage;
import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;
import org.testng.annotations.Factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisplayTests extends BaseTests{

    private String category;
    private String symbol;
    private String category1;
    private String symbol1;
    private String category2;
    private String symbol2;
    private String category3;
    private String symbol3;

    public DisplayTests(String category, String symbol, String category1, String symbol1,
                        String category2, String symbol2, String category3, String symbol3) {

        this.category = category;
        this.symbol = symbol;
        this.category1 = category1;
        this.symbol1 = symbol1;
        this.category2 = category2;
        this.symbol2 = symbol2;
        this.category3 = category3;
        this.symbol3 = symbol3;
    }

    @Factory
    public static Object[] displayTestsDataFactoryMethod() {

        DisplayTests firstDisplayTests = new DisplayTests("Mystic Moment", "mm04", "Mystic Moment",
                "mm07", "Folk&Boho","FB01","Vintage&Nature","sa32" );

        DisplayTests secondDisplayTests = new DisplayTests("Pastellove", "PL47", "Mystic Moment",
                "FB02", "Folk&Boho","FB01","Vintage&Nature","FB01" );

        return new Object[]{
                firstDisplayTests,
                secondDisplayTests};
    }

    @org.testng.annotations.Test
    public void displayAndNavigateProductGalleryTest(){

        ProductPage productPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol);
        
        String imageFirstFullProductGalleryFile = productPage
                .getImageFullProductGalleryFile();

        int numberOfThumbnails = productPage
                .getImageThumbnailProductGalleryFilesList()
                .size();

        String imageFirstThumbnailProductGalleryFile = productPage
                .getImageThumbnailProductGalleryFilesList()
                .get(0)
                .getAttribute("src");

        assertTrue(imageFirstFullProductGalleryFile.equals(imageFirstThumbnailProductGalleryFile), "Pierwsze duze zdjecie nie wyswietla sie prawidlowo");


        for(int i=1;i<numberOfThumbnails;i++) {

            String imageNextThumbnailProductGalleryFile = productPage
                    .changeImageFullProductGalleryFile(i);

            String imageNextFullProductGalleryFile = productPage
                    .getImageFullProductGalleryFile();

            assertTrue(imageNextFullProductGalleryFile.equals(imageNextThumbnailProductGalleryFile), "Duze zdjecie po kliknieciu miniaturki " + i +" nie wyswietla sie prawidlowo");

        }
    }

    @org.testng.annotations.Test
    public void displayNumberOfProductsInCartWhileAddingOneTest(){

        String number = "1";

        String numberOfProductsDisplay = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .viewMainPage()
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay, number, "Cyfra przy koszyku dla 1 produktu nie zgadza sie");
    }

    @org.testng.annotations.Test
    public void displayNumberOfProductsInCartWhileDeletingOneTest(){

        String number = "";
        String numberOfProductsDisplay = "";

        numberOfProductsDisplay = mainCategoryPage
                .viewCategoryByName(category)
                .addToCartByCategoryPage(symbol)
                .viewMainPage()
                .viewCartPage()
                .deleteFromCartPage(symbol)
                .viewMainPage()
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay, number, "Cyfra przy koszyku dla 1 produktu nie zgadza sie");
    }

    @org.testng.annotations.Test
    public void displayNumberOfProductsInCartWhileAddingThreeTest(){

        String number1 = "1";
        String numberOfProductsDisplay1 = "";

        String number2 = "2";
        String numberOfProductsDisplay2 = "";

        String number3 = "3";
        String numberOfProductsDisplay3 = "";

        numberOfProductsDisplay1 = mainCategoryPage
                .viewCategoryByName(category1)
                .addToCartByCategoryPage(symbol1)
                .viewMainPage()
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay1, number1, "Cyfra przy koszyku dla 1 produktu nie zgadza sie");

        numberOfProductsDisplay2 = mainCategoryPage
                .viewCategoryByName(category2)
                .addToCartByCategoryPage(symbol2)
                .viewMainPage()
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay2, number2, "Cyfra przy koszyku dla 2 produktow nie zgadza sie");

        numberOfProductsDisplay3 = mainCategoryPage
                .viewCategoryByName(category3)
                .addToCartByCategoryPage(symbol3)
                .viewMainPage()
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay3, number3, "Cyfra przy koszyku dla 3 produktow nie zgadza sie");

    }

    @org.testng.annotations.Test
    public void displayNumberOfProductsInCartWhileDeletingThreeTest(){

        String number1 = "2";
        String numberOfProductsDisplay1 = "";

        String number2 = "1";
        String numberOfProductsDisplay2 = "";

        String number3 = "";
        String numberOfProductsDisplay3 = "";


        CartPage cartPage = mainCategoryPage
                .viewCategoryByName(category1)
                .addToCartByCategoryPage(symbol1)
                .viewMainPage()
                .viewCategoryByName(category2).
                addToCartByCategoryPage(symbol2)
                .viewMainPage()
                .viewCategoryByName(category3)
                .addToCartByCategoryPage(symbol3);

        numberOfProductsDisplay1 = mainCategoryPage
                .viewCartPage()
                .deleteFromCartPage(symbol1)
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay1, number1, "Cyfra przy koszyku po usunieciu 1 produktu nie zgadza sie");

        numberOfProductsDisplay2 = mainCategoryPage
                .viewCartPage()
                .deleteFromCartPage(symbol2)
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay2, number2, "Cyfra przy koszyku po usunieciu 2 produktow nie zgadza sie");

        numberOfProductsDisplay3 = mainCategoryPage
                .viewCartPage()
                .deleteFromCartPage(symbol3)
                .getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay3, number3, "Cyfra przy koszyku po usunieciu 3 produktow nie zgadza sie");

    }

    @org.testng.annotations.Test
    public void displayCartWithoutContentMessageTest(){

        String message = "Brak produktów w koszyku.";

        String messageWhileCartEmpty = mainCategoryPage
                .viewCartPage()
                .getMessageWhileCartEmpty();

        assertEquals(message, messageWhileCartEmpty, "Komunikat w pustym koszyku nie zgadza się z oczekiwanym");
    }


}