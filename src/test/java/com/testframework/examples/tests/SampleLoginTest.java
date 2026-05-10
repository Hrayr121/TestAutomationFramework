package com.testframework.examples.tests;

import com.google.gson.JsonObject;
import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.core.data.JsonReader;
import com.testframework.pages.DashboardPage;
import com.testframework.pages.MyAccountPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Demonstrates {@link BaseTest}, JSON data-driven login, and page objects.
 * <p>Ignored by default: configure {@code base.url}, then remove {@link Ignore} before relying on it in CI.</p>
 */
@Ignore("Enable after configuring base.url to your application (see README).")
public class SampleLoginTest extends BaseTest {

    private static final String LOGIN_URL = ConfigReader.get("base.url") + "/login";

    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() throws IOException {
        return JsonReader.readAsDataProvider("testdata/login.json");
    }

    @Test(dataProvider = "loginCredentials", groups = {"smoke", "login"})
    public void testLogin(JsonObject data) {
        String username = data.get("username").getAsString();
        String password = data.get("password").getAsString();
        boolean shouldSucceed = data.get("shouldSucceed").getAsBoolean();

        getDriver().get(LOGIN_URL);

        DashboardPage dashboardPage = new MyAccountPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .submitLogin();

        if (shouldSucceed) {
            Assert.assertTrue(dashboardPage.isLoaded(),
                    "Expected dashboard for credentials: " + username);
        } else {
            Assert.assertFalse(dashboardPage.isLoaded(),
                    "Login should have failed for credentials: " + username);
        }
    }
}
