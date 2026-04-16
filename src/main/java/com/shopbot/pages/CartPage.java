package com.shopbot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CartPage – /cart.html
 */
public class CartPage extends BasePage {

    private final By cartItems       = By.cssSelector(".cart_item");
    private final By cartItemNames   = By.cssSelector(".inventory_item_name");
    private final By removeButtons   = By.cssSelector("button[data-test^='remove']");
    private final By checkoutButton  = By.id("checkout");
    private final By continueShop    = By.id("continue-shopping");

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getCartItemNames() {
        return driver.findElements(cartItemNames)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isItemInCart(String name) {
        return getCartItemNames().stream()
                .anyMatch(n -> n.equalsIgnoreCase(name));
    }

    public void removeFirstItem() {
        List<WebElement> btns = driver.findElements(removeButtons);
        if (!btns.isEmpty()) btns.get(0).click();
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartItems).isEmpty();
    }

    public void clickCheckout()        { waitAndClick(checkoutButton); }
    public void clickContinueShopping(){ waitAndClick(continueShop); }
}
