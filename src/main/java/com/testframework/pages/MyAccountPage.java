package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Example login page. Replace with your own page classes in this package, or delete when unused.
 */
public class MyAccountPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "[name='login'][type='submit']")
    private WebElement loginButton;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public MyAccountPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public MyAccountPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public DashboardPage submitLogin() {
        click(loginButton);
        return new DashboardPage(driver);
    }
}
