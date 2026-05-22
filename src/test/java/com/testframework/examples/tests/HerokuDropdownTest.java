package com.testframework.examples.tests;

import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.pages.HerokuDropdownPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Selects an option from the native HTML dropdown on the public Heroku demo and asserts the selection.
 */
public class HerokuDropdownTest extends BaseTest {

    @Test(groups = {"regression", "ui"})
    public void dropdownSelectionIsReflected() {
        getDriver().get(ConfigReader.get("demos.heroku.base.url") + "/dropdown");

        HerokuDropdownPage page = new HerokuDropdownPage(getDriver())
                .selectByVisibleText("Option 2");

        Assert.assertEquals(page.getSelectedText(), "Option 2",
                "Selected option did not update");
    }
}
