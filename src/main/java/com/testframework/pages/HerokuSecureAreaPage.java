package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Secure area after successful login on the Heroku “the-internet” demo ({@code /secure}).
 */
public class HerokuSecureAreaPage extends BasePage {

    @FindBy(id = "flash")
    private WebElement flashBanner;

    public HerokuSecureAreaPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return getCurrentUrl().contains("/secure");
    }

    public String getFlashMessage() {
        return getText(flashBanner);
    }
}
