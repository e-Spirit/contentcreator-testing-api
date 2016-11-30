package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog.CCMessageDialog;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog.CCMessageDialogImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog.CCWizardDialog;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog.CCWizardDialogImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class CCDialogsImpl implements CCDialogs {

	private final WebDriver webDriver;


	public CCDialogsImpl(@NotNull final WebDriver webDriver) {
		this.webDriver = webDriver;
	}


	@Override
	public CCWizardDialog wizardDialog() throws CCAPIException {
		final WebElement dialog = Utils.find(this.webDriver, ExpectedConditions.visibilityOfElementLocated(By.className("fs-WizardDialogBox")));
		return new CCWizardDialogImpl(dialog, this.webDriver);
	}


	@Override
	public CCDialog dialog() throws CCAPIException {
		final WebElement dialog = Utils.find(this.webDriver, ExpectedConditions.visibilityOfElementLocated(By.className("fs-DialogBox")));

		if (!ComponentUtils.hasElement(dialog, this.webDriver, By.className("fs-MessageDialogBox-Container"))) {
			return new CCDialogImpl(dialog, this.webDriver);
		}

		throw new CCAPIException("Can't find dialog", this.webDriver);
	}


	@Override
	public CCMessageDialog messageDialog() throws CCAPIException {
		final WebElement dialog = Utils.find(this.webDriver, ExpectedConditions.visibilityOfElementLocated(By.className("fs-DialogBox")));

		if (ComponentUtils.hasElement(dialog, this.webDriver, By.className("fs-MessageDialogBox-Container"))) {
			return new CCMessageDialogImpl(dialog, this.webDriver);
		}

		throw new CCAPIException("Can't find message dialog", this.webDriver);
	}
}
