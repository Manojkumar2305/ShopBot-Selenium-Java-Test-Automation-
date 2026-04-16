package com.shopbot.pages;

import org.openqa.selenium.By;

/**
 * ProductDetailPage – /inventory-item.html
 */
public class ProductDetailPage extends BasePage {

    private final By productName  = By.cssSelector(".inventory_details_name");
    private final By productPrice = By.cssSelector(".inventory_details_price");
    private final By backButton   = By.id("back-to-products");
    private final By addToCart    = By.cssSelector("button[data-test^='add-to-cart']");

    public String getName()  { return waitForElement(productName).getText(); }
    public String getPrice() { return waitForElement(productPrice).getText(); }

    public void clickAddToCart() { waitAndClick(addToCart); }
    public void clickBack()      { waitAndClick(backButton); }
}
