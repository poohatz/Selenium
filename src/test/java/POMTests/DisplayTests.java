package POMTests;

import PageObjects.CategoryPage;
import PageObjects.MainCategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisplayTests extends BaseTests{


    @Test
    public void displayAndNavigateProductGalleryTest(){

        String category = categories[3];
        String symbol = "WG08";


        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        ProductPage productPage = mainCategoryPage.viewCategoryByName(category).viewProductBySymbol(symbol);
        
        String imageFirstFullProductGalleryFile = productPage.getImageFullProductGalleryFile();
        int numberOfThumbnails = productPage.getImageThumbnailProductGalleryFilesList().size();
        String imageFirstThumbnailProductGalleryFile = productPage.getImageThumbnailProductGalleryFilesList().get(0).getAttribute("src");
        assertTrue(imageFirstFullProductGalleryFile.equals(imageFirstThumbnailProductGalleryFile), "Pierwsze duze zdjecie nie wyswietla sie prawidlowo");


        for(int i=1;i<numberOfThumbnails;i++) {

            String imageNextThumbnailProductGalleryFile = productPage.changeImageFullProductGalleryFile(i);
            String imageNextFullProductGalleryFile = productPage.getImageFullProductGalleryFile();
            assertTrue(imageNextFullProductGalleryFile.equals(imageNextThumbnailProductGalleryFile), "Duze zdjecie po kliknieciu miniaturki " + i +" nie wyswietla sie prawidlowo");

        }
    }

    @Test
    public void displayNumberOfProductsInCartWhileAddingOneTest(){

        String category = categories[3];
        String symbol = "WG08";
        String number = "1";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();
        String numberOfProductsDisplay = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol).viewMainPage().getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay, number, "Cyfra przy koszyku dla 1 produktu nie zgadza sie");
    }

    @Test
    public void displayNumberOfProductsInCartWhileDeletingOneTest(){

        String category = categories[3];
        String symbol = "WG08";
        String number = "";
        String numberOfProductsDisplay = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();

        numberOfProductsDisplay = mainCategoryPage.viewCategoryByName(category).addToCartByCategoryPage(symbol).viewMainPage().
                                viewCartPage().deleteFromCartPage(symbol).viewMainPage().getNumberOfProductsDisplay();

        assertEquals(numberOfProductsDisplay, number, "Cyfra przy koszyku dla 1 produktu nie zgadza sie");
    }

    @Test
    public void displayNumberOfProductsInCartWhileAddingThreeTest(){

        String category1 = categories[3];
        String symbol1 = "WG08";
        String number1 = "1";
        String numberOfProductsDisplay1 = "";

        String category2 = categories[4];
        String symbol2 = "sa32";
        String number2 = "2";
        String numberOfProductsDisplay2 = "";

        String category3 = categories[5];
        String symbol3 = "PL47";
        String number3 = "3";
        String numberOfProductsDisplay3 = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();

        numberOfProductsDisplay1 = mainCategoryPage.viewCategoryByName(category1).addToCartByCategoryPage(symbol1).viewMainPage().getNumberOfProductsDisplay();
        assertEquals(numberOfProductsDisplay1, number1, "Cyfra przy koszyku dla 1 produktu nie zgadza sie");

        numberOfProductsDisplay2 = mainCategoryPage.viewCategoryByName(category2).addToCartByCategoryPage(symbol2).viewMainPage().getNumberOfProductsDisplay();
        assertEquals(numberOfProductsDisplay2, number2, "Cyfra przy koszyku dla 2 produktow nie zgadza sie");

        numberOfProductsDisplay3 = mainCategoryPage.viewCategoryByName(category3).addToCartByCategoryPage(symbol3).viewMainPage().getNumberOfProductsDisplay();
        assertEquals(numberOfProductsDisplay3, number3, "Cyfra przy koszyku dla 3 produktow nie zgadza sie");

    }

    @Test
    public void displayNumberOfProductsInCartWhileDeletingThreeTest(){

        String category1 = categories[3];
        String symbol1 = "WG08";
        String number1 = "2";
        String numberOfProductsDisplay1 = "";

        String category2 = categories[4];
        String symbol2 = "sa32";
        String number2 = "1";
        String numberOfProductsDisplay2 = "";

        String category3 = categories[5];
        String symbol3 = "PL47";
        String number3 = "";
        String numberOfProductsDisplay3 = "";

        MainCategoryPage mainCategoryPage = new MainCategoryPage(driver);
        mainCategoryPage.acceptCookie();

        mainCategoryPage.viewCategoryByName(category1).addToCartByCategoryPage(symbol1).viewMainPage().viewCategoryByName(category2).
                addToCartByCategoryPage(symbol2).viewMainPage().viewCategoryByName(category3).addToCartByCategoryPage(symbol3);

        numberOfProductsDisplay1 = mainCategoryPage.viewCartPage().deleteFromCartPage(symbol1).getNumberOfProductsDisplay();
        assertEquals(numberOfProductsDisplay1, number1, "Cyfra przy koszyku po usunieciu 1 produktu nie zgadza sie");

        numberOfProductsDisplay2 = mainCategoryPage.viewCartPage().deleteFromCartPage(symbol2).getNumberOfProductsDisplay();
        assertEquals(numberOfProductsDisplay2, number2, "Cyfra przy koszyku po usunieciu 2 produktow nie zgadza sie");

        numberOfProductsDisplay3 = mainCategoryPage.viewCartPage().deleteFromCartPage(symbol3).getNumberOfProductsDisplay();
        assertEquals(numberOfProductsDisplay3, number3, "Cyfra przy koszyku po usunieciu 3 produktow nie zgadza sie");

    }



}