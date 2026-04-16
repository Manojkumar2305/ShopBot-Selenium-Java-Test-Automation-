package com.shopbot.tests;

import com.shopbot.dataproviders.TestDataProvider;
import com.shopbot.pages.CartPage;
import com.shopbot.pages.CheckoutPage;
import com.shopbot.pages.LoginPage;
import com.shopbot.pages.ProductListPage;
import com.shopbot.utils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test Module 4 – Checkout Flow
 *  TC_CHK_01 : Fill valid customer info – lands on order overview page
 *  TC_CHK_02 : Order overview shows correct product name and total price
 *  TC_CHK_03 : Complete order – confirmation message displayed
 *  TC_CHK_04 : Missing first name – shows validation error
 */
public class CheckoutTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginAndAddProduct() {
        new LoginPage().login("standard_user", "secret_sauce");
        ProductListPage page = new ProductListPage();
        page.addFirstProductToCart();
        page.clickCart();
    }

    @DataProvider(name = "checkoutData", parallel = false)
    public Object[][] checkoutData() {
        return TestDataProvider.getCheckoutDataFromExcel();
    }

    // ── TC_CHK_01 ────────────────────────────────────────────────────────────
    @Test(dataProvider = "checkoutData",
          description = "Valid customer info leads to order overview page",
          retryAnalyzer = RetryAnalyzer.class)
    public void testCheckoutStepOne(String firstName, String lastName, String postalCode) {
        CartPage     cartPage     = new CartPage();
        CheckoutPage checkoutPage = new CheckoutPage();

        cartPage.clickCheckout();
        checkoutPage.fillCustomerInfo(firstName, lastName, postalCode);
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOnOverviewPage(),
                "Should be on checkout overview page after filling valid info");
    }

    // ── TC_CHK_02 ────────────────────────────────────────────────────────────
    @Test(description = "Order overview shows correct product name and total",
          retryAnalyzer = RetryAnalyzer.class)
    public void testOrderSummary() {
        CartPage     cartPage     = new CartPage();
        CheckoutPage checkoutPage = new CheckoutPage();

        // Get name before proceeding
        String expectedName = cartPage.getCartItemNames().get(0);

        cartPage.clickCheckout();
        checkoutPage.fillCustomerInfo("QA", "Tester", "12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOnOverviewPage(),
                "Should be on overview page");
        Assert.assertEquals(checkoutPage.getSummaryItemName(), expectedName,
                "Product name on summary must match what was added to cart");

        String total = checkoutPage.getSummaryTotal();
        Assert.assertNotNull(total, "Summary total must not be null");
        Assert.assertTrue(total.contains("$"),
                "Summary total must contain a dollar amount");
    }

    // ── TC_CHK_03 ────────────────────────────────────────────────────────────
    @Test(description = "Complete order – confirmation message is displayed",
          retryAnalyzer = RetryAnalyzer.class)
    public void testCompleteOrder() {
        CartPage     cartPage     = new CartPage();
        CheckoutPage checkoutPage = new CheckoutPage();

        cartPage.clickCheckout();
        checkoutPage.fillCustomerInfo("QA", "Tester", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "Order confirmation header must be displayed after finishing");
        Assert.assertTrue(
                checkoutPage.getConfirmationHeader().toLowerCase().contains("thank"),
                "Confirmation message should contain 'thank you'");
    }

    // ── TC_CHK_04 ────────────────────────────────────────────────────────────
    @Test(description = "Empty first name shows validation error on checkout step 1")
    public void testCheckoutEmptyFirstName() {
        CartPage     cartPage     = new CartPage();
        CheckoutPage checkoutPage = new CheckoutPage();

        cartPage.clickCheckout();
        checkoutPage.fillCustomerInfo("", "Tester", "12345");
        checkoutPage.clickContinue();

        String error = checkoutPage.getCheckoutError();
        Assert.assertFalse(error.isEmpty(),
                "Error message should appear when first name is empty");
        Assert.assertTrue(error.toLowerCase().contains("first name"),
                "Error should mention 'First Name'");
    }
}
