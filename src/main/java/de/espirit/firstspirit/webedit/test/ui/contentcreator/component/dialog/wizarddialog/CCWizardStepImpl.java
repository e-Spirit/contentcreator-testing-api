package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CCWizardStepImpl implements CCWizardStep {
    private CCWizardDialog dialog;
    private WebElement stepButton;

    public CCWizardStepImpl(@NotNull final CCWizardDialog dialog, @NotNull final WebElement stepButton) {
        this.dialog = dialog;
        this.stepButton = stepButton;
    }

    @NotNull
    @Override
    public WebElement content() throws CCAPIException {
        stepButton.click();

        return dialog.html().findElement(By.className("fs-WizardDialogBox-Content"));
    }
}
