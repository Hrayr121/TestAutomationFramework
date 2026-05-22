package com.testframework.examples.tests;

import com.google.gson.JsonObject;
import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.core.data.JsonReader;
import com.testframework.pages.HerokuLoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Data-driven negative login cases against the public Heroku demo. Reads rows from
 * {@code testdata/heroku-login-negative.json} via {@link JsonReader}.
 */
public class HerokuLoginNegativeTest extends BaseTest {

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() throws IOException {
        return JsonReader.readAsDataProvider("testdata/heroku-login-negative.json");
    }

    @Test(dataProvider = "invalidLogins", groups = {"regression", "login"})
    public void invalidCredentialsStayOnLoginPage(JsonObject data) {
        String username = data.get("username").getAsString();
        String password = data.get("password").getAsString();
        String expectedFlash = data.get("expectedFlashContains").getAsString();

        String loginUrl = ConfigReader.get("demos.heroku.base.url") + "/login";
        getDriver().get(loginUrl);

        HerokuLoginPage login = new HerokuLoginPage(getDriver())
                .enterUsername(username)
                .enterPassword(password)
                .submitExpectingFailure();

        Assert.assertTrue(login.isOnLoginPage(),
                "Expected to stay on /login, was: " + getDriver().getCurrentUrl());
        Assert.assertTrue(login.getFlashMessage().contains(expectedFlash),
                "Expected flash to contain: " + expectedFlash + ", was: " + login.getFlashMessage());
    }
}
