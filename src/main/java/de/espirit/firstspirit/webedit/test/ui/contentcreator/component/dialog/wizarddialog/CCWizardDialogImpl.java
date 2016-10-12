package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CCWizardDialogImpl implements CCWizardDialog {
    private WebElement dialogElement;
    private WebDriver webDriver;

    public CCWizardDialogImpl(@NotNull final WebElement dialogElement, WebDriver webDriver) {
        this.dialogElement = dialogElement;
        this.webDriver = webDriver;
    }

    @NotNull
    @Override
    public WebElement html() {
        return dialogElement;
    }

    @Override
    public CCWizardStep stepByName(@NotNull final String displayName) throws CCAPIException {
        WebElement stepsElement = Utils.findItemInElement(webDriver, dialogElement, By.className("fs-WizardDialogBox-Side"));
        List<WebElement> elements = Utils.findMultipleItemsInElement(webDriver, stepsElement, By.className("fs-WizardDialogBox-Step"));

        WebElement result = elements.stream()
                .filter(element -> element.findElement(By.className("fs-WizardDialogBox-StepTitle")).getText().equals(displayName))
                .findFirst()
                .orElse(null);

        if(result != null)
            return new CCWizardStepImpl(this, result, webDriver);

        return null;
    }

    @Override
    public List<CCInputButton> buttons() throws CCAPIException {
        WebElement sideButtons = Utils.findItemInElement(webDriver, dialogElement, By.className("fs-WizardDialogBox-Side-Buttons"));
        List<WebElement> elements = Utils.findMultipleItemsInElement(webDriver, sideButtons, By.className("fs-button"));

        return elements.stream()
                .map((webElement) -> new CCInputButtonImpl(webDriver, webElement))
                .collect(Collectors.toList());
    }

    @Override
    public CCInputButton buttonByName(@NotNull String displayName) throws CCAPIException {
        for (CCInputButton button : buttons()) {
            if(button.label().equals(displayName))
                return button;
        }

        return null;
    }
}
