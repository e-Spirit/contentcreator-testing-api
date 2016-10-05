package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.*;
import org.apache.commons.lang.NotImplementedException;
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

    /**
     * Match webElement to an input component
     * @param webElement The web element to match
     * @return Returns
     */
    public CCInputComponent match(@NotNull final WebElement webElement) {
        if(CCInputTextImpl.isComponent(webElement, webDriver)){
            return new CCInputTextImpl(webElement);
        }else if(CCInputCheckboxImpl.isComponent(webElement,webDriver)){
            return new CCInputCheckboxImpl(webElement);
        } else if(CCInputRadioImpl.isComponent(webElement,webDriver)){
            return new CCInputRadioImpl(webElement);
        } else if(CCInputComboBoxImpl.isComponent(webElement,webDriver)){
            return new CCInputComboBoxImpl(webElement, webDriver);
        } else if(CCInputTextAreaImpl.isComponent(webElement,webDriver)){
            return new CCInputTextAreaImpl(webElement);
        } else if(CCInputDomImpl.isComponent(webElement,webDriver)){
            return new CCInputDomImpl(webElement, webDriver);
        } else if(CCInputButtonImpl.isComponent(webElement,webDriver)){
            return new CCInputButtonImpl(webElement);
        }

        return null;
    }

    @Override
    public List<CCInputComponent> inputComponents() {
        List<WebElement> elements = dialogElement.findElements(By.className("fs-gadget"));
        List<CCInputComponent> ccInputComponents = new ArrayList<>();

        for (WebElement element : elements) {
            CCInputComponent ccInputComponent = match(element);

            if(ccInputComponent != null)
                ccInputComponents.add(ccInputComponent);
        }

        return ccInputComponents;
    }

    @Override
    public CCInputComponent inputComponentByName(@NotNull String displayName) {
        throw new NotImplementedException();
    }

    @Override
    public void ok() {
        clickButton(0);
    }

    @Override
    public void cancel() {
        clickButton(1);
    }

    private void clickButton(final int buttonIndex) {
        WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-DialogBox-Footer"));
        List<WebElement> elements = dialogFooterElement.findElements(By.className("fs-button"));

        if(elements.size()>buttonIndex)
            elements.get(buttonIndex).click();
    }

    @Override
    public List<CCInputButton> buttons() {
        WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-DialogBox-Footer"));
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
