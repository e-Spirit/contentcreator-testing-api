package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CCWizardDialogImpl implements CCWizardDialog {
    private WebElement dialogElement;

    public CCWizardDialogImpl(@NotNull final WebElement dialogElement) {
        this.dialogElement = dialogElement;
    }

    @NotNull
    @Override
    public WebElement html() {
        return dialogElement;
    }

    @Override
    public CCWizardStep stepByName(String displayName) {
        WebElement stepsElement = dialogElement.findElement(By.className("fs-WizardDialogBox-Side"));
        List<WebElement> elements = stepsElement.findElements(By.className("fs-WizardDialogBox-Step"));

        WebElement result = elements.stream()
                .filter(element -> element.findElement(By.className("fs-WizardDialogBox-StepTitle")).getText().equals(displayName))
                .findFirst()
                .orElse(null);

        if(result != null)
            return new CCWizardStepImpl(this, result);

        return null;
    }

    @Override
    public List<CCInputButton> buttons() {
        WebElement sideButtons = dialogElement.findElement(By.className("fs-WizardDialogBox-Side-Buttons"));
        List<WebElement> elements = sideButtons.findElements(By.className("fs-button"));

        return elements.stream()
                .map(CCInputButtonImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public CCInputButton buttonByName(@NotNull String displayName) {
        for (CCInputButton button : buttons()) {
            if(button.label().equals(displayName))
                return button;
        }

        return null;
    }
}
