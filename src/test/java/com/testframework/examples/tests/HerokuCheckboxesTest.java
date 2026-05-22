package com.testframework.examples.tests;

import com.testframework.core.BaseTest;
import com.testframework.core.config.ConfigReader;
import com.testframework.pages.HerokuCheckboxesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demonstrates a non-login flow + a page object exposing a list of elements.
 */
public class HerokuCheckboxesTest extends BaseTest {

    @Test(groups = {"regression", "ui"})
    public void defaultStateAndTogglingChangesEachCheckbox() {
        getDriver().get(ConfigReader.get("demos.heroku.base.url") + "/checkboxes");

        HerokuCheckboxesPage page = new HerokuCheckboxesPage(getDriver());
        Assert.assertEquals(page.count(), 2, "Expected exactly 2 checkboxes");

        Assert.assertFalse(page.isChecked(0), "First checkbox should start unchecked");
        Assert.assertTrue(page.isChecked(1), "Second checkbox should start checked");

        page.toggle(0).toggle(1);

        Assert.assertTrue(page.isChecked(0), "First checkbox should be checked after toggle");
        Assert.assertFalse(page.isChecked(1), "Second checkbox should be unchecked after toggle");
    }
}
