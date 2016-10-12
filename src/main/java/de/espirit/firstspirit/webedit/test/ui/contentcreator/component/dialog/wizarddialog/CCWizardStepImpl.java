package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CCWizardStepImpl implements CCWizardStep {
    private CCWizardDialog dialog;
    private WebElement stepButton;
    private WebDriver webDriver;

    public CCWizardStepImpl(@NotNull final CCWizardDialog dialog, @NotNull final WebElement stepButton, WebDriver webDriver) {
        this.dialog = dialog;
        this.stepButton = stepButton;
        this.webDriver = webDriver;
    }

    @NotNull
    @Override
    public WebElement content() throws CCAPIException {
        stepButton.click();
        return Utils.findItemInElement(webDriver, dialog.html(), By.className("fs-WizardDialogBox-Content"));
    }
}
