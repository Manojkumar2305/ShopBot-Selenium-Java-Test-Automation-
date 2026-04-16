package com.shopbot.tests;

import com.shopbot.pages.LoginPage;
import com.shopbot.pages.ProductDetailPage;
import com.shopbot.pages.ProductListPage;
import com.shopbot.utils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test Module 2 – Product Listing and Sorting
 *  TC_PROD_01 : Product listing page shows at least one product
 *  TC_PROD_02 : Sort A to Z – first item is "Sauce Labs Backpack"
 *  TC_PROD_03 : Sort Price low to high – cheapest item appears first
 *  TC_PROD_04 : Product detail – name and price match the listing
 */
public class ProductTest extends BaseTest {

    // Login before every test in this class
    @BeforeMethod(alwaysRun = true)
    public void loginAsStandardUser() {
        new LoginPage().login("standard_user", "secret_sauce");
    }

    // ── TC_PROD_01 ────────────────────────────────────────────────────────────
    @Test(description = "Product listing page shows at least one product after login",
          retryAnalyzer = RetryAnalyzer.class)
    public void testProductsAreDisplayed() {
        ProductListPage page = new ProductListPage();
        Assert.assertTrue(page.hasProducts(),
                "Product listing should show at least one product");
        Assert.assertTrue(page.getProductCount() > 0,
                "Product count must be greater than 0");
    }

    // ── TC_PROD_02 ────────────────────────────────────────────────────────────
    @Test(description = "Sort Name A to Z – first product is Sauce Labs Backpack",
          retryAnalyzer = RetryAnalyzer.class)
    public void testSortByNameAtoZ() {
        ProductListPage page = new ProductListPage();
        page.sortBy("az");
        String firstName = page.getFirstProductName();
        Assert.assertEquals(firstName, "Sauce Labs Backpack",
                "After A-Z sort, first product should be Sauce Labs Backpack");
    }

    // ── TC_PROD_03 ────────────────────────────────────────────────────────────
    @Test(description = "Sort Price low to high – cheapest item appears first",
          retryAnalyzer = RetryAnalyzer.class)
    public void testSortByPriceLowToHigh() {
        ProductListPage page   = new ProductListPage();
        page.sortBy("lohi");
        double firstPrice      = page.getFirstProductPrice();
        List<Double> allPrices = page.getAllProductPrices();
        double minPrice        = allPrices.stream().mapToDouble(d -> d).min().orElse(0);

        Assert.assertEquals(firstPrice, minPrice, 0.001,
                "First product price should be the lowest after sorting low-to-high");
    }

    // ── TC_PROD_04 ────────────────────────────────────────────────────────────
    @Test(description = "Product detail page shows name and price matching the listing",
          retryAnalyzer = RetryAnalyzer.class)
    public void testProductDetailMatchesListing() {
        ProductListPage   listPage   = new ProductListPage();
        ProductDetailPage detailPage = new ProductDetailPage();

        // Capture first product name and price from listing
        String listedName  = listPage.getFirstProductName();
        double listedPrice = listPage.getFirstProductPrice();

        // Navigate to detail page
        listPage.clickFirstProductName();

        String detailName  = detailPage.getName();
        double detailPrice = Double.parseDouble(detailPage.getPrice().replace("$", ""));

        Assert.assertEquals(detailName, listedName,
                "Product name on detail page must match listing");
        Assert.assertEquals(detailPrice, listedPrice, 0.001,
                "Product price on detail page must match listing");
    }
}
