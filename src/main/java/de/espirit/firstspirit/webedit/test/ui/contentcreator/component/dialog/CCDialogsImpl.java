package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog.CCMessageDialog;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog.CCMessageDialogImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog.CCWizardDialog;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog.CCWizardDialogImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CCDialogsImpl implements CCDialogs {
    private WebDriver webDriver;

    public CCDialogsImpl(@NotNull final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public CCWizardDialog wizardDialog() throws CCAPIException {
        WebElement dialog;

        try {
            dialog = new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("fs-WizardDialogBox")));
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }

        return new CCWizardDialogImpl(dialog);
    }

    @Override
    public CCDialog dialog() {
        WebElement dialog = new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("fs-DialogBox")));

        if(!ComponentUtils.hasElement(dialog, webDriver, By.className("fs-MessageDialogBox-Container")))
            return new CCDialogImpl(dialog, webDriver);

        return null;
    }

    @Override
    public CCMessageDialog messageDialog() {
        WebElement dialog = new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("fs-DialogBox")));

        if(ComponentUtils.hasElement(dialog, webDriver, By.className("fs-MessageDialogBox-Container")))
            return new CCMessageDialogImpl(dialog);

        return null;
    }
}
