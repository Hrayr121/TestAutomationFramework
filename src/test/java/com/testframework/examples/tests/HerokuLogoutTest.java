package com.testframework.examples.tests;

import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.pages.HerokuLoginPage;
import com.testframework.pages.HerokuSecureAreaPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login then logout flow on the public Heroku demo — verifies the post-logout flash.
 */
public class HerokuLogoutTest extends BaseTest {

    @Test(groups = {"regression", "login"})
    public void logoutReturnsToLoginWithFlash() {
        String loginUrl = ConfigReader.get("demos.heroku.base.url") + "/login";
        getDriver().get(loginUrl);

        HerokuSecureAreaPage secure = new HerokuLoginPage(getDriver())
                .enterUsername("tomsmith")
                .enterPassword("SuperSecretPassword!")
                .submitLogin();
        Assert.assertTrue(secure.isLoaded(), "Login did not reach /secure");

        HerokuLoginPage login = secure.clickLogout();

        Assert.assertTrue(login.isOnLoginPage(),
                "Expected /login after logout, was: " + getDriver().getCurrentUrl());
        Assert.assertTrue(login.getFlashMessage().contains("You logged out of the secure area!"),
                "Unexpected logout flash: " + login.getFlashMessage());
    }
}
