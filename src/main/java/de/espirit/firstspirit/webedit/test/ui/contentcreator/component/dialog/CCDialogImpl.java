package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CCDialogImpl implements CCDialog {
    private WebElement dialogElement;
    private WebDriver webDriver;

    public CCDialogImpl(@NotNull final WebElement dialogElement, @NotNull final WebDriver webDriver) {
        this.dialogElement = dialogElement;
        this.webDriver = webDriver;
    }

    @Override
    public List<CCInputComponent> inputComponents() throws CCAPIException {
        List<WebElement> elements = Utils.findMultipleItemsInElement(webDriver, dialogElement, By.className("fs-gadget"));
        List<CCInputComponent> ccInputComponents = new ArrayList<>();

        for (WebElement element : elements) {
            CCInputComponent ccInputComponent = ComponentUtils.matchComponent(element, webDriver);

            if (ccInputComponent != null)
                ccInputComponents.add(ccInputComponent);
        }

        return ccInputComponents;
    }

    @Override
    public CCInputComponent inputComponentByName(@NotNull String displayName) throws CCAPIException {
        List<WebElement> elements = Utils.findMultipleItemsInElement(webDriver, dialogElement, By.className("fs-gadget"));
        for (WebElement element : elements) {
            CCInputComponent ccInputComponent = ComponentUtils.matchComponent(element, webDriver);

            if (ccInputComponent != null) {
                if (ccInputComponent.label().equals(displayName))
                    return ccInputComponent;
            }
        }
        return null;
    }

    @Override
    public void ok() throws CCAPIException {
        clickButton(0);
    }

    @Override
    public void cancel() throws CCAPIException {
        clickButton(1);
    }

    private void clickButton(final int buttonIndex) throws CCAPIException {
        List<WebElement> elements = getDialogButtons();

        if (elements.size() > buttonIndex)
            elements.get(buttonIndex).click();
    }

    private List<WebElement> getDialogButtons() throws CCAPIException {
        WebElement dialogFooterElement = Utils.findItemInElement(webDriver, dialogElement, By.className("fs-DialogBox-Footer"));
        return Utils.findMultipleItemsInElement(webDriver, dialogFooterElement, By.className("fs-button"));
    }

    @Override
    public List<CCInputButton> buttons() throws CCAPIException {
        List<WebElement> elements = getDialogButtons();
        List<CCInputButton> ccInputButtons = new ArrayList<>();

        for (WebElement element : elements) {
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
