package com.testframework.core;

import com.testframework.core.config.ConfigReader;
import com.testframework.core.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Extend this class for UI tests. The WebDriver is created per test method and stored in a {@link ThreadLocal}
 * so parallel TestNG execution stays isolated.
 */
public class BaseTest {

    public static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driverThread.get();
    }

    /**
     * {@code alwaysRun = true} so setup still runs when tests are filtered with {@code -Dgroups=...}
     * (otherwise TestNG may skip configuration methods and {@link #getDriver()} stays null).
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = ConfigReader.get("browser");
        boolean headless = ConfigReader.getBoolean("headless");

        WebDriver driver = DriverFactory.create(browser, headless);
        driver.manage().window().maximize();

        driverThread.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }
}
