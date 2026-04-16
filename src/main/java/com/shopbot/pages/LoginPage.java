package com.shopbot.pages;

import org.openqa.selenium.By;

/**
 * LoginPage – https://www.saucedemo.com (the landing page IS the login page)
 */
public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    public void enterUsername(String username) { waitAndType(usernameInput, username); }
    public void enterPassword(String password) { waitAndType(passwordInput, password); }
    public void clickLogin()                   { waitAndClick(loginButton); }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isErrorDisplayed() {
        try { return waitForElement(errorMessage).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public String getErrorMessage() {
        return waitForElement(errorMessage).getText();
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().equals(
                com.shopbot.config.ConfigReader.getInstance().getBaseUrl() + "/");
    }
}
