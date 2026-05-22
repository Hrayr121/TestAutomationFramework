package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Dynamic loading example on the Heroku “the-internet” demo ({@code /dynamic_loading/1} or {@code /2}).
 */
public class HerokuDynamicLoadingPage extends BasePage {

    @FindBy(css = "#start button")
    private WebElement startButton;

    @FindBy(id = "finish")
    private WebElement finishLabel;

    public HerokuDynamicLoadingPage(WebDriver driver) {
        super(driver);
    }

    public HerokuDynamicLoadingPage clickStart() {
        click(startButton);
        return this;
    }

    public String waitForFinishText() {
        wait.until(ExpectedConditions.visibilityOf(finishLabel));
        return finishLabel.getText();
    }
}
