package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
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
        try {
            return dialog.html().findElement(By.className("fs-WizardDialogBox-Content"));
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }

    }
}
