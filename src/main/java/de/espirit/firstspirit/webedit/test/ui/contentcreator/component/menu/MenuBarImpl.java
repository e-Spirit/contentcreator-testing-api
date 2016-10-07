package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;
import static de.espirit.firstspirit.webedit.test.ui.util.Utils.idle;

public class MenuBarImpl implements MenuBar {
    private WebDriver _webDriver;

    public MenuBarImpl(@NotNull final WebDriver webDriver) {
        this._webDriver = webDriver;
    }

    @NotNull
    @Override
    public ElementStatus getElementStatus() {
        int max = 5;
        do {
            final String cssClass = html().getAttribute("class");
            if (cssClass.contains("workflow")) {
                return ElementStatus.IN_WORKFLOW;
            }
            if (cssClass.contains("changed")) {
                return ElementStatus.CHANGED;
            }
            if (cssClass.contains("released")) {
                return ElementStatus.RELEASED;
            }
            if (cssClass.contains("deleted")) {
                return ElementStatus.DELETED;
            }
            if (cssClass.contains("archived")) {
                return ElementStatus.ARCHIVED;
            }
            idle();
        } while (--max > 0);
        return ElementStatus.UNKNOWN;
    }

    @NotNull
    @Override
    public Menu workflowMenu() {
        WebElement button = new WebDriverWait(_webDriver, 30).until(ExpectedConditions.elementToBeClickable(By.className("fs-toolbar-state")));
        return new MenuImpl(_webDriver, button);
    }

    @NotNull
    @Override
    public Menu actionMenu() {
        WebElement button = new WebDriverWait(_webDriver, 30).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#fs-toolbar > div.fs-toolbar-content.fs-toolbar-content-main > div:nth-child(5) > div > div:nth-child(3)")));
        return new MenuImpl(_webDriver, button);
    }

    @NotNull
    @Override
    public Menu contentMenu() {
        throw new NotImplementedException();
    }

    @Override
    public void search(final String query) {
        final WebElement element = find(_webDriver, By.className("fs-searchtextbox-field"));
        element.click();
        element.sendKeys(query + '\n');
    }

    @NotNull
    @Override
    public WebElement html() {
        return find(_webDriver, By.className("fs-toolbar-state"));
    }

}