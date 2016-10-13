package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog.CCMessageDialog;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog.CCMessageDialogImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog.CCWizardDialog;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog.CCWizardDialogImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;

public class CCDialogsImpl implements CCDialogs {
    private WebDriver webDriver;

    public CCDialogsImpl(@NotNull final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public CCWizardDialog wizardDialog() throws CCAPIException {
        WebElement dialog = find(webDriver, ExpectedConditions.visibilityOfElementLocated(By.className("fs-WizardDialogBox")));
        return new CCWizardDialogImpl(dialog, webDriver);
    }

    @Override
    public CCDialog dialog() throws CCAPIException {
        WebElement dialog = Utils.find(webDriver,ExpectedConditions.visibilityOfElementLocated(By.className("fs-DialogBox")));

        if(!ComponentUtils.hasElement(dialog, webDriver, By.className("fs-MessageDialogBox-Container")))
            return new CCDialogImpl(dialog, webDriver);

       throw new CCAPIException("Can't find dialog", webDriver);
    }

    @Override
    public CCMessageDialog messageDialog() throws CCAPIException {
        WebElement dialog = Utils.find(webDriver, ExpectedConditions.visibilityOfElementLocated(By.className("fs-DialogBox")));

        if(ComponentUtils.hasElement(dialog, webDriver, By.className("fs-MessageDialogBox-Container")))
            return new CCMessageDialogImpl(dialog, webDriver);

        throw new CCAPIException("Can't find message dialog", webDriver);
    }
}
