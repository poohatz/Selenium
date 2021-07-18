package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderingPage extends BasePage{

    private final By headingOrderingPageSelector = new By.ByCssSelector("h1");
    private final By headingOrderConfirmationOrderingPageSelector = new By.ByCssSelector("div[class='col-12 col-md-9'] > h3");
    private final By finalOrderButtonOrderingPageSelector = new By.ByCssSelector("input.btn.btn-warning");
    private final By dataFromSummaryOrderingPageSelector = new By.ByCssSelector("tr > td");
    private final By returnToStepOneButtonOrderingPageSelector = new By.ByLinkText("Wróć do edycji danych");
    private final By nameInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_name");
    private final By addressInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_street");
    private final By cityInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_city");
    private final By codeInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_postalCode");
    private final By telInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_phone");
    private final By emailInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_email");
    private final By commentsInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_notes");
    private final By invoiceOptionInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_hasInvoice");
    private final By nipInputInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_taxId");
    private final By continueButtonInStepOneFormOrderingPageSelector = new By.ByCssSelector("#shipping_details_save");
    private final By termsConfirmationCheckboxOrderingPageSelector = new By.ByCssSelector("#terms");
    private final By rodoConfirmationChceckboxOrderingPageSelector = new By.ByCssSelector("#personal-data");


    public OrderingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void acceptCookie() {
        super.acceptCookie();
    }

    @Override
    public MainCategoryPage viewMainPage() {
        return super.viewMainPage();
    }

    @Override
    public String getNumberOfProductsDisplay() {
        return super.getNumberOfProductsDisplay();
    }

    @Override
    public CartPage viewCartPage() {
        return super.viewCartPage();
    }

    @Override
    public String getMessageWhileCartEmpty() {
        return super.getMessageWhileCartEmpty();
    }

    public String getHeadingOrderingPage(){

        return driver.findElement(headingOrderingPageSelector).getText();

    }

    public String getFinalHeadingOrderingPage(){

        return driver.findElement(headingOrderConfirmationOrderingPageSelector).getText();

    }

    public OrderingPage fillNameInStepOneForm(String name){

        driver.findElement(nameInputInStepOneFormOrderingPageSelector).sendKeys(name);
        logger.info("Field name changed on " + name);
        return this;
    }

    public OrderingPage clearNameInStepOneForm(){

        driver.findElement(nameInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }


    public OrderingPage fillAddressInStepOneForm(String address){

        driver.findElement(addressInputInStepOneFormOrderingPageSelector).sendKeys(address);
        logger.info("Field address changed on " + address);
        return this;
    }

    public OrderingPage clearAddressInStepOneForm(){

        driver.findElement(addressInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }

    public OrderingPage fillCodeInStepOneForm(String code){

        driver.findElement(codeInputInStepOneFormOrderingPageSelector).sendKeys(code);
        logger.info("Field code changed on " + code);
        return this;
    }

    public OrderingPage clearCodeInStepOneForm(){

        driver.findElement(codeInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }

    public OrderingPage fillCityInStepOneForm(String city){

        driver.findElement(cityInputInStepOneFormOrderingPageSelector).sendKeys(city);
        logger.info("Field city changed on " + city);
        return this;
    }

    public OrderingPage clearCityInStepOneForm(){

        driver.findElement(cityInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }

    public OrderingPage fillTelInStepOneForm(String tel){

        driver.findElement(telInputInStepOneFormOrderingPageSelector).sendKeys(tel);
        logger.info("Field telephone changed on " + tel);
        return this;
    }

    public OrderingPage clearTelInStepOneForm(){

        driver.findElement(telInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }

    public OrderingPage fillEmailInStepOneForm(String email){

        driver.findElement(emailInputInStepOneFormOrderingPageSelector).sendKeys(email);
        logger.info("Field email changed on " + email);
        return this;
    }

    public OrderingPage clearEmailInStepOneForm(){

        driver.findElement(emailInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }

    public OrderingPage fillCommentsInStepOneForm(String comments){

        driver.findElement(commentsInputInStepOneFormOrderingPageSelector).sendKeys(comments);
        logger.info("Field comments changed on " + comments);
        return this;
    }

    public OrderingPage clearCommentsInStepOneForm(){

        driver.findElement(commentsInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }

    public OrderingPage isInvoiceNeededOptionSteoOneForm(Boolean invoice){

        driver.findElement(invoiceOptionInStepOneFormOrderingPageSelector).click();
        return this;
    }

    public OrderingPage clearIsInvoiceNeededOptionSteoOneForm(){

        driver.findElement(invoiceOptionInStepOneFormOrderingPageSelector).clear();
        return this;
    }

    public OrderingPage fillNipInStepOneForm(String nip){

        driver.findElement(nipInputInStepOneFormOrderingPageSelector).sendKeys(nip);
        logger.info("Field NIP changed on " + nip);
        return this;
    }

    public OrderingPage clearNipInStepOneForm(){

        driver.findElement(nipInputInStepOneFormOrderingPageSelector).clear();
        return this;
    }


    public OrderingPage saveAndContinueOrderingPage(){

        driver.findElement(continueButtonInStepOneFormOrderingPageSelector).click();
        logger.info("Save and Continue button clicked");
        return this;
    }

    public Map<String, String> getAddressDataFromStepTwoOrderingPage(){

        List <WebElement> summaryData= driver.findElements(dataFromSummaryOrderingPageSelector);
        Map<String, String> summaryDataFromOrderingPage = new HashMap<>();
        summaryDataFromOrderingPage.put("name", summaryData.get(0).getText());
        summaryDataFromOrderingPage.put("address", summaryData.get(1).getText());
        summaryDataFromOrderingPage.put("code", summaryData.get(2).getText());
        summaryDataFromOrderingPage.put("city", summaryData.get(3).getText());
        summaryDataFromOrderingPage.put("email", summaryData.get(4).getText());
        summaryDataFromOrderingPage.put("tel", summaryData.get(5).getText());
        summaryDataFromOrderingPage.put("nip", summaryData.get(6).getText());

        return summaryDataFromOrderingPage;
    }

    public OrderingPage returnToStepOneOrderingPage(){

        driver.findElement(returnToStepOneButtonOrderingPageSelector).click();
        logger.info("Return to address form button clicked");
        return this;

    }

    public OrderingPage checkTermsConfirmation(){

        driver.findElement(termsConfirmationCheckboxOrderingPageSelector).click();
        logger.info("Terms accepted");
        return this;
    }

    public OrderingPage checkRodoConfirmation(){

        driver.findElement(rodoConfirmationChceckboxOrderingPageSelector).click();
        logger.info("Rodo accepted");
        return this;
    }

    public OrderingPage finalizeAndConfirmOrder(){

        driver.findElement(finalOrderButtonOrderingPageSelector).click();
        logger.info("Finalize Order Button clicked");
        return this;
    }





}