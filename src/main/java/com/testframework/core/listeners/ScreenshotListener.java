package com.testframework.core.listeners;

import com.testframework.core.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * On failure: saves a PNG under {@code target/screenshots/} and attaches screenshot, URL, and stack trace to Allure.
 */
public class ScreenshotListener implements ITestListener {

    private static final String SCREENSHOT_DIR = "target/screenshots/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BaseTest.driverThread.get();
        if (driver == null) {
            return;
        }

        attachThrowable(result);

        try {
            String currentUrl = driver.getCurrentUrl();
            Allure.addAttachment("Current URL", "text/plain",
                    new ByteArrayInputStream(currentUrl.getBytes(StandardCharsets.UTF_8)), ".txt");
        } catch (Exception ignored) {
            // Session may already be invalid.
        }

        if (!(driver instanceof TakesScreenshot takesScreenshot)) {
            return;
        }

        byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        String fileName = result.getName() + "_" + LocalDateTime.now().format(FORMATTER) + ".png";

        Allure.addAttachment("Screenshot on failure", "image/png",
                new ByteArrayInputStream(screenshot), ".png");

        try {
            Path dir = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dir);
            Files.write(dir.resolve(fileName), screenshot);
            System.out.println("Screenshot saved: " + SCREENSHOT_DIR + fileName);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    private static void attachThrowable(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable == null) {
            return;
        }
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        Allure.addAttachment("Failure stack trace", "text/plain",
                new ByteArrayInputStream(writer.toString().getBytes(StandardCharsets.UTF_8)), ".txt");
    }
}
