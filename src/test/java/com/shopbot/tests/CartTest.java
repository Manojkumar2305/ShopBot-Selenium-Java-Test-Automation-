package com.shopbot.tests;

import com.shopbot.pages.CartPage;
import com.shopbot.pages.LoginPage;
import com.shopbot.pages.ProductListPage;
import com.shopbot.utils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test Module 3 – Shopping Cart
 *  TC_CART_01 : Add 1 product – cart badge shows 1
 *  TC_CART_02 : Add 2 products – cart badge shows 2
 *  TC_CART_03 : Remove product from cart – item disappears
 *  TC_CART_04 : Cart retains items after navigating back to product listing
 */
public class CartTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginAsStandardUser() {
        new LoginPage().login("standard_user", "secret_sauce");
    }

    // ── TC_CART_01 ────────────────────────────────────────────────────────────
    @Test(description = "Add one product – cart badge count becomes 1",
          retryAnalyzer = RetryAnalyzer.class)
    public void testAddOneProductCartBadge() {
        ProductListPage page = new ProductListPage();
        page.addFirstProductToCart();

        Assert.assertTrue(page.isCartBadgeVisible(),
                "Cart badge should appear after adding a product");
        Assert.assertEquals(page.getCartBadgeCount(), 1,
                "Cart badge count should be 1 after adding one product");
    }

    // ── TC_CART_02 ────────────────────────────────────────────────────────────
    @Test(description = "Add two products – cart badge count becomes 2",
          retryAnalyzer = RetryAnalyzer.class)
    public void testAddTwoProductsCartBadge() {
        ProductListPage page = new ProductListPage();
        page.addFirstProductToCart();
        page.addSecondProductToCart();

        Assert.assertEquals(page.getCartBadgeCount(), 2,
                "Cart badge count should be 2 after adding two products");
    }

    // ── TC_CART_03 ────────────────────────────────────────────────────────────
    @Test(description = "Remove product from cart – item no longer appears",
          retryAnalyzer = RetryAnalyzer.class)
    public void testRemoveProductFromCart() {
        ProductListPage page     = new ProductListPage();
        CartPage        cartPage = new CartPage();

        page.addFirstProductToCart();
        page.clickCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Pre-condition: cart should have 1 item");

        cartPage.removeFirstItem();

        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing the only item");
    }

    // ── TC_CART_04 ────────────────────────────────────────────────────────────
    @Test(description = "Cart retains items after navigating back to product listing",
          retryAnalyzer = RetryAnalyzer.class)
    public void testCartRetainsItemsAfterNavigation() {
        ProductListPage page     = new ProductListPage();
        CartPage        cartPage = new CartPage();

        // Add a product then navigate to cart
        page.addFirstProductToCart();
        page.clickCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should have 1 item before navigation");

        // Go back to product listing
        cartPage.clickContinueShopping();

        // Badge should still show 1
        Assert.assertEquals(page.getCartBadgeCount(), 1,
                "Cart badge should still show 1 after going back to products");

        // Go to cart again and verify item is still there
        page.clickCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should still have 1 item after navigating back");
    }
}
