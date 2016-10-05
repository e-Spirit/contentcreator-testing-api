package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CCDialogsImpl implements CCDialogs {
    private WebDriver webDriver;

    public CCDialogsImpl(@NotNull final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public CCWizardDialog wizardDialog() {
        throw new NotImplementedException();
    }

    @Override
    public CCDialog dialog() {
        WebElement dialog = new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("fs-DialogBox")));

        return new CCDialogImpl(dialog, webDriver);
    }
}
