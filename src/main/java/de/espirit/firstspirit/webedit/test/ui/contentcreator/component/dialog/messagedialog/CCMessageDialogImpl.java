package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CCMessageDialogImpl implements CCMessageDialog {
    private WebElement dialogElement;
    private WebDriver webDriver;

    public CCMessageDialogImpl(@NotNull final WebElement dialogElement, WebDriver webDriver) {
        this.dialogElement = dialogElement;
        this.webDriver = webDriver;
    }

    @Override
    public String message() throws CCAPIException {
        final WebElement messageBox = Utils.findItemInElement(webDriver, dialogElement, By.className("fs-MessageDialogBox-Message"));
        return messageBox.getText();
    }

    @Override
    public void ok() throws CCAPIException {
        clickButton(0);
    }

    private void clickButton(final int buttonIndex) throws CCAPIException {
        List<WebElement> buttons = getDialogButtons();

        if(buttons.size()>buttonIndex)
            buttons.get(buttonIndex).click();
    }

    private List<WebElement> getDialogButtons() throws CCAPIException {
        WebElement dialogFooterElement = Utils.findItemInElement(webDriver, dialogElement, By.className("fs-MessageDialogBox-Buttons"));
        return Utils.findMultipleItemsInElement(webDriver, dialogFooterElement, By.className("fs-button"));
    }

    @Override
    public List<CCInputButton> buttons() throws CCAPIException {
        List<WebElement> buttons = getDialogButtons();
        List<CCInputButton> ccInputButtons = new ArrayList<>();
        for (WebElement element : buttons) {
            ccInputButtons.add(new CCInputButtonImpl(webDriver, element));
        }
        return ccInputButtons;
    }

    @NotNull
    @Override
    public WebElement html() {
        return dialogElement;
    }
}
