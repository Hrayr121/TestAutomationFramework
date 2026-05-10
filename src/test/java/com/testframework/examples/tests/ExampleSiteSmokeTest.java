package com.testframework.examples.tests;

import com.testframework.core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Minimal check that WebDriver starts and can load a stable public page. Keeps {@code mvn test} green on a fresh clone without app-specific URLs.
 */
public class ExampleSiteSmokeTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void loadsExampleDomain() {
        getDriver().get("https://example.com");
        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("example"),
                "Unexpected title: " + getDriver().getTitle());
    }
}
