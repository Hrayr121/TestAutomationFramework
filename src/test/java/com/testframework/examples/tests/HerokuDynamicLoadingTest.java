package com.testframework.examples.tests;

import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.pages.HerokuDynamicLoadingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Clicks "Start" on the Heroku demo and waits for the hidden element to appear — exercises explicit waits.
 */
public class HerokuDynamicLoadingTest extends BaseTest {

    @Test(groups = {"regression", "ui"})
    public void dynamicLoadingRevealsHelloAfterStart() {
        getDriver().get(ConfigReader.get("demos.heroku.base.url") + "/dynamic_loading/1");

        String finishText = new HerokuDynamicLoadingPage(getDriver())
                .clickStart()
                .waitForFinishText();

        Assert.assertEquals(finishText, "Hello World!",
                "Unexpected finish text: " + finishText);
    }
}
