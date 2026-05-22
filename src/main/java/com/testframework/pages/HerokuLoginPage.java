package com.testframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Login page for the public Heroku “the-internet” demo ({@code /login}).
 */
public class HerokuLoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement flashBanner;

    public HerokuLoginPage(WebDriver driver) {
        super(driver);
    }

    public HerokuLoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public HerokuLoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public HerokuSecureAreaPage submitLogin() {
        click(loginButton);
        // Wait for the navigation triggered by the form POST to complete before returning the next page.
        wait.until(ExpectedConditions.and(
                ExpectedConditions.urlContains("/secure"),
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        ));
        return new HerokuSecureAreaPage(driver);
    }

    /** Use when a failure is expected — stays on {@code /login} with an error flash. */
    public HerokuLoginPage submitExpectingFailure() {
        click(loginButton);
        // Stay on /login and show an error flash banner.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        return this;
    }

    public String getFlashMessage() {
        return getText(flashBanner);
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("/login");
    }
}
