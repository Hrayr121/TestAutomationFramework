package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        return new HerokuSecureAreaPage(driver);
    }
}
