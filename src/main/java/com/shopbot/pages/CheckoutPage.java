package com.shopbot.pages;

import org.openqa.selenium.By;

/**
 * CheckoutPage – covers all 3 steps:
 *   Step 1: /checkout-step-one.html  – customer info
 *   Step 2: /checkout-step-two.html  – order summary / overview
 *   Step 3: /checkout-complete.html  – order confirmation
 */
public class CheckoutPage extends BasePage {

    // Step 1 – Customer info
    private final By firstNameInput  = By.id("first-name");
    private final By lastNameInput   = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton  = By.id("continue");
    private final By errorMessage    = By.cssSelector("[data-test='error']");

    // Step 2 – Order summary
    private final By summaryItems    = By.cssSelector(".cart_item");
    private final By summaryTotal    = By.cssSelector(".summary_total_label");
    private final By itemName        = By.cssSelector(".inventory_item_name");
    private final By finishButton    = By.id("finish");

    // Step 3 – Confirmation
    private final By confirmHeader   = By.cssSelector(".complete-header");
    private final By confirmText     = By.cssSelector(".complete-text");

    // ── Step 1 ────────────────────────────────────────────────────────────────

    public void fillCustomerInfo(String firstName, String lastName, String postalCode) {
        waitAndType(firstNameInput,  firstName);
        waitAndType(lastNameInput,   lastName);
        waitAndType(postalCodeInput, postalCode);
    }

    public void clickContinue() { waitAndClick(continueButton); }

    public String getCheckoutError() {
        return waitForElement(errorMessage).getText();
    }

    // ── Step 2 ────────────────────────────────────────────────────────────────

    public boolean isOnOverviewPage() {
        return getCurrentUrl().contains("checkout-step-two");
    }

    public int getSummaryItemCount() {
        return driver.findElements(summaryItems).size();
    }

    public String getSummaryItemName() {
        return waitForElement(itemName).getText();
    }

    public String getSummaryTotal() {
        return fluentWait(summaryTotal).getText();
    }

    public void clickFinish() { waitAndClick(finishButton); }

    // ── Step 3 ────────────────────────────────────────────────────────────────

    public boolean isOrderConfirmed() {
        try { return waitForElement(confirmHeader).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public String getConfirmationHeader() {
        return waitForElement(confirmHeader).getText();
    }
}
