package com.testframework.core.factory;

import com.testframework.core.enums.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Centralized driver creation. Adding a new browser only requires editing this class.
 */
public class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver create(String browserName, boolean headless) {
        return create(BrowserType.fromString(browserName), headless); //converts browserName string to BrowserType enum via BrowserType.fromString
    }

    public static WebDriver create(BrowserType browser, boolean headless) {
        return switch (browser) {
            case CHROME -> createChrome(headless);
            case FIREFOX -> createFirefox(headless);
            case EDGE -> createEdge(headless);
        };
    }

    private static WebDriver createChrome(boolean headless) {
        WebDriverManager.chromedriver().setup(); //downloads the matching ChromeDriver for the installed Chrome, and points Selenium at it., to  avoid hand-installing chromedriver on PATH.
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
            // Required on many Linux CI runners (e.g. GitHub Actions).
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
        }
        options.addArguments("--disable-blink-features=AutomationControlled");
        return new ChromeDriver(options); // constructs a real browser session: starts Chrome, returns a WebDriver handle to be used for get, findElement, etc
    }

    private static WebDriver createFirefox(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdge(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        return new EdgeDriver(options);
    }
}
