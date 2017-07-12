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
	private final WebElement dialogElement;
	private final WebDriver webDriver;

	public CCMessageDialogImpl(@NotNull final WebElement dialogElement, final WebDriver webDriver) {
		this.dialogElement = dialogElement;
		this.webDriver = webDriver;
	}

	@Override
	public String message() throws CCAPIException {
		final WebElement messageBox = Utils.findItemInElement(this.webDriver, this.dialogElement, By.className("fs-MessageDialogBox-Message"));
		return messageBox.getText();
	}

	@Override
	public void ok() throws CCAPIException {
		this.clickButton(0);
	}

	private void clickButton(final int buttonIndex) throws CCAPIException {
		final List<WebElement> buttons = this.getDialogButtons();

		if (buttons.size() > buttonIndex) {
			buttons.get(buttonIndex).click();
		}
	}

	private List<WebElement> getDialogButtons() throws CCAPIException {
		final WebElement dialogFooterElement = Utils.findItemInElement(this.webDriver, this.dialogElement, By.className("fs-MessageDialogBox-Buttons"));
		return Utils.findMultipleItemsInElement(this.webDriver, dialogFooterElement, By.className("fs-button"));
	}

	@Override
	public List<CCInputButton> buttons() throws CCAPIException {
		final List<WebElement> buttons = this.getDialogButtons();
		final List<CCInputButton> ccInputButtons = new ArrayList<>();
		for (final WebElement element : buttons) {
			ccInputButtons.add(new CCInputButtonImpl(this.webDriver, element));
		}
		return ccInputButtons;
	}

	@Override
	public CCInputButton buttonByName(@NotNull final String displayName) throws CCAPIException {
		for (final CCInputButton button : this.buttons()) {
			if (button.label().equals(displayName)) {
				return button;
			}
		}

		throw new CCAPIException("Can't find button with the displayname: '" + displayName + "'", this.webDriver);
	}

	@NotNull
	@Override
	public WebElement html() {
		return this.dialogElement;
	}
}
