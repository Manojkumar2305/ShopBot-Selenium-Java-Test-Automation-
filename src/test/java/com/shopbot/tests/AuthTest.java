package com.shopbot.tests;

import com.shopbot.dataproviders.TestDataProvider;
import com.shopbot.pages.LoginPage;
import com.shopbot.pages.ProductListPage;
import com.shopbot.utils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test Module 1 – User Authentication
 *  TC_AUTH_01 : Standard user login – success
 *  TC_AUTH_02 : Locked out user – error message shown
 *  TC_AUTH_03 : Empty credentials – error message shown
 *  TC_AUTH_04 : Invalid credentials – error message shown
 *  TC_AUTH_05 : Logout via burger menu – redirects to login page
 */
public class AuthTest extends BaseTest {

    @DataProvider(name = "loginDataExcel", parallel = false)
    public Object[][] loginDataExcel() {
        return TestDataProvider.getLoginDataFromExcel();
    }

    @DataProvider(name = "loginDataJson", parallel = false)
    public Object[][] loginDataJson() {
        return TestDataProvider.getLoginDataFromJson();
    }

    // ── TC_AUTH_01 to TC_AUTH_04 – data-driven from Excel ────────────────────
    @Test(dataProvider = "loginDataExcel",
          description = "Data-driven login – valid, locked, empty, invalid credentials",
          retryAnalyzer = RetryAnalyzer.class)
    public void testLoginScenarios(String username, String password,
                                   String expectedResult, String description) {
        LoginPage       loginPage   = new LoginPage();
        ProductListPage productPage = new ProductListPage();

        loginPage.login(username, password);

        switch (expectedResult) {
            case "pass":
                Assert.assertTrue(getCurrentUrl().contains("inventory"),
                        "Expected inventory page after valid login. Case: " + description);
                break;
            case "locked":
                Assert.assertTrue(loginPage.isErrorDisplayed(),
                        "Expected error for locked user. Case: " + description);
                Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked"),
                        "Error should mention 'locked'. Case: " + description);
                break;
            case "fail":
                Assert.assertTrue(loginPage.isErrorDisplayed(),
                        "Expected error message. Case: " + description);
                break;
        }
    }

    // ── TC_AUTH_05 – Logout ───────────────────────────────────────────────────
    @Test(description = "Logout via burger menu redirects to login page",
          retryAnalyzer = RetryAnalyzer.class)
    public void testLogout() {
        LoginPage       loginPage   = new LoginPage();
        ProductListPage productPage = new ProductListPage();

        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(getCurrentUrl().contains("inventory"),
                "Pre-condition: must be on inventory page after login");

        productPage.logout();

        Assert.assertTrue(loginPage.isOnLoginPage(),
                "After logout, should be redirected to login page");
    }

    // helper to get current URL without driver ref in test
    private String getCurrentUrl() {
        return com.shopbot.utils.DriverManager.getDriver().getCurrentUrl();
    }
}
