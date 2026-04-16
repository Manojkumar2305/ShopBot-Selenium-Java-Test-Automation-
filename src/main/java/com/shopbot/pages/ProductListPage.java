package com.shopbot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProductListPage – /inventory.html
 * Covers: product listing, sorting, add to cart, cart badge, burger menu logout.
 */
public class ProductListPage extends BasePage {

    private final By productCards    = By.cssSelector(".inventory_item");
    private final By productNames    = By.cssSelector(".inventory_item_name");
    private final By productPrices   = By.cssSelector(".inventory_item_price");
    private final By productImages   = By.cssSelector(".inventory_item_img img");
    private final By sortDropdown    = By.cssSelector(".product_sort_container");
    private final By cartBadge       = By.cssSelector(".shopping_cart_badge");
    private final By cartLink        = By.cssSelector(".shopping_cart_link");
    private final By burgerMenu      = By.id("react-burger-menu-btn");
    private final By logoutLink      = By.id("logout_sidebar_link");
    private final By addToCartBtns   = By.cssSelector("button[data-test^='add-to-cart']");

    // ── Product listing ───────────────────────────────────────────────────────

    public boolean hasProducts() {
        return !driver.findElements(productCards).isEmpty();
    }

    public int getProductCount() {
        return driver.findElements(productCards).size();
    }

    public List<String> getAllProductNames() {
        return driver.findElements(productNames)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getAllProductPrices() {
        return driver.findElements(productPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    // ── Sorting ───────────────────────────────────────────────────────────────

    public void sortBy(String optionValue) {
        new Select(fluentWait(sortDropdown)).selectByValue(optionValue);
    }

    public String getFirstProductName() {
        return fluentWait(By.cssSelector(".inventory_item_name")).getText();
    }

    public double getFirstProductPrice() {
        return Double.parseDouble(
                fluentWait(productPrices).getText().replace("$", ""));
    }

    // ── Add to cart ───────────────────────────────────────────────────────────

    /** Adds the first product's Add to Cart button */
    public void addFirstProductToCart() {
        List<WebElement> btns = driver.findElements(addToCartBtns);
        if (!btns.isEmpty()) btns.get(0).click();
    }

    /** Adds the second product (index 1) */
    public void addSecondProductToCart() {
        List<WebElement> btns = driver.findElements(addToCartBtns);
        if (btns.size() > 1) btns.get(1).click();
    }

    /** Adds product at a given index (0-based) */
    public void addProductToCart(int index) {
        List<WebElement> btns = driver.findElements(addToCartBtns);
        if (btns.size() > index) btns.get(index).click();
    }

    // ── Cart badge ────────────────────────────────────────────────────────────

    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(fluentWait(cartBadge).getText().trim());
        } catch (Exception e) { return 0; }
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    public void clickCart() { waitAndClick(cartLink); }

    public void clickFirstProductName() { waitAndClick(productNames); }

    // ── Logout via burger menu ────────────────────────────────────────────────

    public void logout() {
        waitAndClick(burgerMenu);
        waitAndClick(logoutLink);
    }

    // ── Problem user checks ───────────────────────────────────────────────────

    /** Returns src of all product images – problem_user has broken (wrong) src */
    public List<String> getAllImageSrcs() {
        return driver.findElements(productImages)
                .stream().map(e -> e.getAttribute("src")).collect(Collectors.toList());
    }

    public boolean hasBrokenImages() {
        List<String> srcs = getAllImageSrcs();
        return srcs.stream().anyMatch(src ->
                src == null || src.isEmpty() || src.contains("WithGarbageOnItToBreakIt"));
    }
}
