package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent.*;
import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CCDialogImpl implements CCDialog {
    private WebElement dialog;
    private WebDriver webDriver;

    public CCDialogImpl(@NotNull final WebElement dialog, @NotNull final WebDriver webDriver) {
        this.dialog = dialog;
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
        }

        return null;
    }

    @Override
    public List<CCInputComponent> inputComponents() {
        List<WebElement> elements = dialog.findElements(By.className("fs-gadget"));
        List<CCInputComponent> ccInputComponents = new ArrayList<>();

        for (WebElement element : elements) {
            CCInputComponent ccInputComponent = match(element);

            if(ccInputComponent != null)
                ccInputComponents.add(ccInputComponent);
        }

        return ccInputComponents;
    }

    @Override
    public CCInputComponent inputComponentByName(@NotNull String name) {
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public WebElement html() {
        return dialog;
    }
}
