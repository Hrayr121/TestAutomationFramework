package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "[name='login'][type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public DashboardPage submitLogin() {
        click(loginButton); // click is from BasePage, which includes waiting logic
        return new DashboardPage(driver);
    }
}
