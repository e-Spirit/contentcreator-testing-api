package de.espirit.firstspirit.webedit.test.ui.util;

import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButtonImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputCheckboxImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComboBoxImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputDomImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputRadioImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputTextAreaImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputTextImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.parameter.InputCheckBoxParameter;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.parameter.InputComboBoxParameter;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.parameter.InputTextParameter;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

public class ComponentUtils {

	public static boolean hasElement(final WebElement webElement, final WebDriver webDriver, final By by) {
		webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			webElement.findElement(by);
			return true;
		} catch (final NoSuchElementException exception) {
			return false;
		} finally {
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
	}


	public static boolean hasElement(final WebDriver webDriver, final By by) {
		webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			webDriver.findElement(by);
			return true;
		} catch (final NoSuchElementException exception) {
			return false;
		} finally {
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
	}


	/**
	 * Match webElement to an input component
	 * 
	 * @param webElement The web element to match
	 * @param webDriver The webdriver to use
	 * @return Returns the matched component, otherwise null
	 */
	public static CCInputComponent matchComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) throws CCAPIException {
		if (CCInputTextImpl.isComponent(webElement, webDriver)) {
			return new CCInputTextImpl(webElement, webDriver);
		} else if (CCInputCheckboxImpl.isComponent(webElement, webDriver)) {
			return new CCInputCheckboxImpl(webElement, webDriver);
		} else if (CCInputRadioImpl.isComponent(webElement, webDriver)) {
			return new CCInputRadioImpl(webDriver, webElement);
		} else if (CCInputComboBoxImpl.isComponent(webElement, webDriver)) {
			return new CCInputComboBoxImpl(webElement, webDriver);
		} else if (CCInputTextAreaImpl.isComponent(webElement, webDriver)) {
			return new CCInputTextAreaImpl(webDriver, webElement);
		} else if (CCInputDomImpl.isComponent(webElement, webDriver)) {
			return new CCInputDomImpl(webElement, webDriver);
		} else if (CCInputButtonImpl.isComponent(webElement, webDriver)) {
			return new CCInputButtonImpl(webDriver, webElement);
		}

		return null;
	}


	/**
	 * Match webElement to a parameter component
	 * 
	 * @param webElement The web element to match
	 * @param webDriver The webdriver to use
	 * @return Returns the matched component, otherwise null
	 */
	public static CCInputComponent matchParameter(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
		if (CCInputTextImpl.isComponent(webElement, webDriver)) {
			return new InputTextParameter(webElement, webDriver);
		} else if (CCInputCheckboxImpl.isComponent(webElement, webDriver)) {
			return new InputCheckBoxParameter(webElement, webDriver);
		} else if (CCInputComboBoxImpl.isComponent(webElement, webDriver)) {
			return new InputComboBoxParameter(webElement, webDriver);
		}

		return null;
	}
}
