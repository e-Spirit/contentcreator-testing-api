package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CCMessageDialogImpl implements CCMessageDialog {
    private WebElement dialogElement;

    public CCMessageDialogImpl(@NotNull final WebElement dialogElement) {
        this.dialogElement = dialogElement;
    }

    @Override
    public String message() {
        return dialogElement.findElement(By.className("fs-MessageDialogBox-Message")).getText();
    }

    @Override
    public void ok() {
        clickButton(0);
    }

    private void clickButton(final int buttonIndex) {
        WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-MessageDialogBox-Buttons"));
        List<WebElement> elements = dialogFooterElement.findElements(By.className("fs-button"));

        if(elements.size()>buttonIndex)
            elements.get(buttonIndex).click();
    }

    @Override
    public List<CCInputButton> buttons() {
        WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-MessageDialogBox-Buttons"));
        List<WebElement> elements = dialogFooterElement.findElements(By.className("fs-button"));
        List<CCInputButton> ccInputButtons = new ArrayList<>();

        for (WebElement element : elements) {
            ccInputButtons.add(new CCInputButtonImpl(element));
        }

        return ccInputButtons;
    }

    @NotNull
    @Override
    public WebElement html() {
        return dialogElement;
    }
}
