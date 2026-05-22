package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Dropdown page on the Heroku “the-internet” demo ({@code /dropdown}).
 */
public class HerokuDropdownPage extends BasePage {

    @FindBy(id = "dropdown")
    private WebElement dropdown;

    public HerokuDropdownPage(WebDriver driver) {
        super(driver);
    }

    public HerokuDropdownPage selectByVisibleText(String text) {
        waitForVisibility(dropdown);
        new Select(dropdown).selectByVisibleText(text);
        return this;
    }

    public String getSelectedText() {
        waitForVisibility(dropdown);
        return new Select(dropdown).getFirstSelectedOption().getText();
    }
}
