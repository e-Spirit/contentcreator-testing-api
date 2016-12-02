package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog;

import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class CCWizardDialogImpl implements CCWizardDialog {

	private final WebElement dialogElement;
	private final WebDriver webDriver;


	public CCWizardDialogImpl(@NotNull final WebElement dialogElement, final WebDriver webDriver) {
		this.dialogElement = dialogElement;
		this.webDriver = webDriver;
	}


	@NotNull
	@Override
	public WebElement html() {
		return this.dialogElement;
	}


	@Override
	public CCWizardStep stepByName(final String displayName) throws CCAPIException {
		final WebElement stepsElement = Utils.findItemInElement(this.webDriver, this.dialogElement, By.className("fs-WizardDialogBox-Side"));
		final List<WebElement> elements = Utils.findMultipleItemsInElement(this.webDriver, stepsElement, By.className("fs-WizardDialogBox-Step"));

		final WebElement result = elements.stream().filter(element -> element.findElement(By.className("fs-WizardDialogBox-StepTitle")).getText().equals(displayName)).findFirst().orElse(null);

		if (result != null) {
			return new CCWizardStepImpl(this, result, this.webDriver);
		}

		throw new CCAPIException("Can't find step with the displayname: '" + displayName + "'", this.webDriver);
	}


	@Override
	public List<CCInputButton> buttons() throws CCAPIException {
		final WebElement sideButtons = Utils.findItemInElement(this.webDriver, this.dialogElement, By.className("fs-WizardDialogBox-Side-Buttons"));
		final List<WebElement> elements = Utils.findMultipleItemsInElement(this.webDriver, sideButtons, By.className("fs-button"));

		return elements.stream().map((webElement) -> new CCInputButtonImpl(this.webDriver, webElement)).collect(Collectors.toList());
	}


	@Override
	public CCInputButton buttonByName(final String displayName) throws CCAPIException {
		for (final CCInputButton button : this.buttons()) {
			if (button.label().equals(displayName)) {
				return button;
			}
		}

		throw new CCAPIException("Can't find button with the displayname: '" + displayName + "'", this.webDriver);
	}
}
