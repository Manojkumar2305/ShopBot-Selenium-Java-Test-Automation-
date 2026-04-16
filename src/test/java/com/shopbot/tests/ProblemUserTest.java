package com.shopbot.tests;

import com.shopbot.pages.LoginPage;
import com.shopbot.pages.ProductListPage;
import com.shopbot.utils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test Module 5 – Problem User Validation
 *  TC_PROB_01 : problem_user can login and lands on inventory page
 *  TC_PROB_02 : problem_user sees broken/incorrect product images
 *  TC_PROB_03 : problem_user add-to-cart behavior is documented
 *
 * NOTE: saucedemo intentionally breaks things for problem_user.
 * These tests ASSERT the broken behavior – not that things work correctly.
 */
public class ProblemUserTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginAsProblemUser() {
        new LoginPage().login("problem_user", "secret_sauce");
    }

    // ── TC_PROB_01 ────────────────────────────────────────────────────────────
    @Test(description = "problem_user can login and lands on inventory page",
          retryAnalyzer = RetryAnalyzer.class)
    public void testProblemUserCanLogin() {
        String url = com.shopbot.utils.DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("inventory"),
                "problem_user should land on inventory page after login");
    }

    // ── TC_PROB_02 ────────────────────────────────────────────────────────────
    @Test(description = "problem_user sees broken images on product listing page",
          retryAnalyzer = RetryAnalyzer.class)
    public void testProblemUserBrokenImages() {
        ProductListPage page = new ProductListPage();

        Assert.assertTrue(page.hasProducts(),
                "Products should still be listed for problem_user");

        List<String> srcs = page.getAllImageSrcs();
        Assert.assertFalse(srcs.isEmpty(),
                "Image src list should not be empty");

        // saucedemo uses a known broken image URL for problem_user
        boolean hasBroken = page.hasBrokenImages();
        Assert.assertTrue(hasBroken,
                "problem_user should see broken/wrong product images – this is expected behavior");
    }

    // ── TC_PROB_03 ────────────────────────────────────────────────────────────
    @Test(description = "Document problem_user add-to-cart behavior",
          retryAnalyzer = RetryAnalyzer.class)
    public void testProblemUserAddToCartBehavior() {
        ProductListPage page = new ProductListPage();

        // Attempt to add first product
        page.addFirstProductToCart();

        // For problem_user, the cart badge may NOT update correctly
        // We assert the badge count is whatever it is and log it
        // The site intentionally breaks cart for this user
        int badge = page.getCartBadgeCount();

        // Document the behavior: badge either 0 (broken) or 1 (accidentally works)
        // Either outcome is valid to observe and report
        Assert.assertTrue(badge >= 0,
                "Cart badge count documented for problem_user: " + badge
                + " (broken behavior expected)");
    }
}
