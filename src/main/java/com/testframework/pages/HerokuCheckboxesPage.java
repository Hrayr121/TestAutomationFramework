package com.testframework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Public Heroku demo {@code /checkboxes}. Two checkboxes (first unchecked, second checked by default).
 */
public class HerokuCheckboxesPage extends BasePage {

    @FindBy(css = "#checkboxes input[type='checkbox']")
    private List<WebElement> checkboxes;

    public HerokuCheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public int count() {
        return checkboxes.size();
    }

    public boolean isChecked(int index) {
        return checkboxes.get(index).isSelected();
    }

    public HerokuCheckboxesPage toggle(int index) {
        click(checkboxes.get(index));
        return this;
    }
}
