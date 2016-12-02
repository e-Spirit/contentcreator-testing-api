package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class CCDialogImpl implements CCDialog {

	private final WebElement dialogElement;
	private final WebDriver webDriver;


	public CCDialogImpl(@NotNull final WebElement dialogElement, @NotNull final WebDriver webDriver) {
		this.dialogElement = dialogElement;
		this.webDriver = webDriver;
	}


	@Override
	public List<CCInputComponent> inputComponents() throws CCAPIException {
		final List<WebElement> elements = Utils.findMultipleItemsInElement(this.webDriver, this.dialogElement, By.className("fs-gadget"));
		final List<CCInputComponent> ccInputComponents = new ArrayList<>();

		for (final WebElement element : elements) {
			final CCInputComponent ccInputComponent = ComponentUtils.matchComponent(element, this.webDriver);

			if (ccInputComponent != null) {
				ccInputComponents.add(ccInputComponent);
			}
		}

		return ccInputComponents;
	}


	@Override
	public CCInputComponent inputComponentByName(final String displayName) throws CCAPIException {
		final List<WebElement> elements = Utils.findMultipleItemsInElement(this.webDriver, this.dialogElement, By.className("fs-gadget"));
		for (final WebElement element : elements) {
			final CCInputComponent ccInputComponent = ComponentUtils.matchComponent(element, this.webDriver);

			if (ccInputComponent != null) {
				if (ccInputComponent.label().equals(displayName)) {
					return ccInputComponent;
				}
			}
		}

		throw new CCAPIException("Can't find input component with the displayname: '" + displayName + "'", this.webDriver);
	}


	@Override
	public void ok() throws CCAPIException {
		this.clickButton(0);
	}


	@Override
	public void cancel() throws CCAPIException {
		this.clickButton(1);
	}


	private void clickButton(final int buttonIndex) throws CCAPIException {
		final List<WebElement> elements = this.getDialogButtons();

		if (elements.size() > buttonIndex) {
			elements.get(buttonIndex).click();
		}
	}


	private List<WebElement> getDialogButtons() throws CCAPIException {
		final WebElement dialogFooterElement = Utils.findItemInElement(this.webDriver, this.dialogElement, By.className("fs-DialogBox-Footer"));
		return Utils.findMultipleItemsInElement(this.webDriver, dialogFooterElement, By.className("fs-button"));
	}


	@Override
	public List<CCInputButton> buttons() throws CCAPIException {
		final List<WebElement> elements = this.getDialogButtons();
		final List<CCInputButton> ccInputButtons = new ArrayList<>();

		for (final WebElement element : elements) {
			ccInputButtons.add(new CCInputButtonImpl(this.webDriver, element));
		}

		return ccInputButtons;
	}


	@NotNull
	@Override
	public WebElement html() {
		return this.dialogElement;
	}
}
