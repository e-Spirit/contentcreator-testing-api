package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
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
        List<CCInputComponent> ccInputComponents;

        try {
            List<WebElement> elements = dialogElement.findElements(By.className("fs-gadget"));
            ccInputComponents = new ArrayList<>();

            for (WebElement element : elements) {
                CCInputComponent ccInputComponent = ComponentUtils.matchComponent(element, webDriver);

                if(ccInputComponent != null)
                    ccInputComponents.add(ccInputComponent);
            }
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }


        return ccInputComponents;
    }

    @Override
    public CCInputComponent inputComponentByName(@NotNull String displayName) throws CCAPIException {
        try {
            List<WebElement> elements = dialogElement.findElements(By.className("fs-gadget"));
            for (WebElement element : elements) {
                CCInputComponent ccInputComponent = ComponentUtils.matchComponent(element, webDriver);

                if(ccInputComponent != null)
                {
                    if(ccInputComponent.label().equals(displayName))
                        return ccInputComponent;
                }
            }
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
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
        try {
            WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-DialogBox-Footer"));
            List<WebElement> elements = dialogFooterElement.findElements(By.className("fs-button"));

            if(elements.size()>buttonIndex)
                elements.get(buttonIndex).click();
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }

    }

    @Override
    public List<CCInputButton> buttons() throws CCAPIException {
        List<CCInputButton> ccInputButtons;

        try {
            WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-DialogBox-Footer"));
            List<WebElement> elements = dialogFooterElement.findElements(By.className("fs-button"));
            ccInputButtons = new ArrayList<>();

            for (WebElement element : elements) {
                ccInputButtons.add(new CCInputButtonImpl(webDriver, element));
            }
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }


        return ccInputButtons;
    }

    @NotNull
    @Override
    public WebElement html() {
        return dialogElement;
    }
}
