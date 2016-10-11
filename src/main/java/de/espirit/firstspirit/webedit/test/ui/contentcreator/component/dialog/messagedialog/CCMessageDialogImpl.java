package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
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
    public String message() {
        return dialogElement.findElement(By.className("fs-MessageDialogBox-Message")).getText();
    }

    @Override
    public void ok() throws CCAPIException {
        clickButton(0);
    }

    private void clickButton(final int buttonIndex) throws CCAPIException {
        try {
            WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-MessageDialogBox-Buttons"));
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
            WebElement dialogFooterElement = dialogElement.findElement(By.className("fs-MessageDialogBox-Buttons"));
            List<WebElement> elements = dialogFooterElement.findElements(By.className("fs-button"));
            ccInputButtons = new ArrayList<>();

            for (WebElement element : elements) {
                ccInputButtons.add(new CCInputButtonImpl(webDriver, element));
            }
        }  catch (WebDriverException exception) {
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
