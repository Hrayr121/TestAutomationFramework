package com.testframework.examples.tests;

import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.pages.HerokuLoginPage;
import com.testframework.pages.HerokuSecureAreaPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Minimal checks that WebDriver starts against stable public pages. Keeps {@code mvn test} green on a fresh clone without app-specific URLs.
 */
public class ExampleSiteSmokeTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void loadsExampleDomain() {
        getDriver().get("https://example.com");
        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("example"),
                "Unexpected title: " + getDriver().getTitle());
    }

    /**
     * Login smoke using page objects and config (credentials are documented on the Heroku demo site).
     */
    @Test(groups = {"smoke", "login"})
    public void publicDemoSiteLoginSucceeds() {
        String loginUrl = ConfigReader.get("demos.heroku.base.url") + "/login";
        getDriver().get(loginUrl);

        HerokuSecureAreaPage secure = new HerokuLoginPage(getDriver())
                .enterUsername("tomsmith")
                .enterPassword("SuperSecretPassword!")
                .submitLogin();

        Assert.assertTrue(secure.isLoaded(), "Expected URL to contain /secure");
        Assert.assertTrue(secure.getFlashMessage().contains("You logged into a secure area!"),
                "Unexpected flash message: " + secure.getFlashMessage());
    }
}
