package com.testframework.examples.failuredemos;

import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.pages.HerokuLoginPage;
import com.testframework.pages.HerokuSecureAreaPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Intentionally failing tests for exploring Allure
 * This is not scanned by default testng.xml : instead, run "mvn -Dsurefire.suiteXmlFiles=testng-failure-demo.xml test".
 */
public class AllureFailureDemoTest extends BaseTest {

    @Test(groups = {"failure-demo"})
    public void wrongTitleOnExampleDomain() {
        getDriver().get("https://example.com");
        Assert.assertTrue(getDriver().getTitle().contains("This title does not exist"),
                "Demo failure: title was " + getDriver().getTitle());
    }

    @Test(groups = {"failure-demo"})
    public void wrongFlashMessageAfterValidLogin() {
        String loginUrl = ConfigReader.get("demos.heroku.base.url") + "/login";
        getDriver().get(loginUrl);

        HerokuSecureAreaPage secure = new HerokuLoginPage(getDriver())
                .enterUsername("tomsmith")
                .enterPassword("SuperSecretPassword!")
                .submitLogin();

        Assert.assertTrue(secure.getFlashMessage().contains("Access denied"),
                "Demo failure: flash was " + secure.getFlashMessage());
    }

    @Test(groups = {"failure-demo"})
    public void loginWithWrongPasswordExpectsSuccess() {
        String loginUrl = ConfigReader.get("demos.heroku.base.url") + "/login";
        getDriver().get(loginUrl);

        HerokuSecureAreaPage secure = new HerokuLoginPage(getDriver())
                .enterUsername("tomsmith")
                .enterPassword("wrong-password")
                .submitLogin();

        Assert.assertTrue(secure.isLoaded(),
                "Demo failure: login should not reach secure area with bad password");
    }
}
