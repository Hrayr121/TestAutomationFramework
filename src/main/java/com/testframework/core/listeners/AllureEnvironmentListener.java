package com.testframework.core.listeners;

import com.testframework.core.config.ConfigReader;
import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Adds run context to each Allure test (environment, browser, headless, base URL).
 */
public class AllureEnvironmentListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Allure.label("env", ConfigReader.getEnvironmentName());
        Allure.label("browser", ConfigReader.get("browser"));
        Allure.parameter("headless", String.valueOf(ConfigReader.getBoolean("headless")));
        Allure.parameter("base.url", ConfigReader.get("base.url"));
    }
}
