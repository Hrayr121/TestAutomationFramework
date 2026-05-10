package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Example post-login page. Replace or delete with your own flows.
 */
public class DashboardPage extends BasePage {

    @FindBy(css = ".welcome-message")
    private WebElement welcomeMessage;

    @FindBy(css = "[data-testid='logout-btn']")
    private WebElement logoutButton;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    public boolean isLoaded() {
        return getCurrentUrl().contains("/dashboard");
    }

    public MyAccountPage clickLogout() {
        click(logoutButton);
        return new MyAccountPage(driver);
    }
}
