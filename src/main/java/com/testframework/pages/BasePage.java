package com.testframework.pages;

import com.testframework.core.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final int DEFAULT_TIMEOUT_SECONDS = ConfigReader.getInt("timeout");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
        PageFactory.initElements(driver, this);  //PageFactory scans the class for every field annotated with @FindBy and replaces each null with a proxy object — a
                                                        // placeholder that knows the locator strategy (id="username") but hasn't touched the DOM yet.
    }

    protected void navigateTo(String url) {
        driver.get(url);
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForVisibility(WebElement element, int timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }
}
