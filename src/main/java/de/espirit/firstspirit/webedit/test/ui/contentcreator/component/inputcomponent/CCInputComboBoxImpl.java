package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

/**
 * Notes: The combobox items are not actually present within the html.
 * To provide access to the combobox items a dynamic parsing is required.
 * This implementation should be temporary.
 * TODO: use javascript to fire the specific event
 **/
public class CCInputComboBoxImpl implements CCInputComboBox {

	protected WebElement webElement;
	protected WebDriver webDriver;


	public CCInputComboBoxImpl(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
		this.webElement = webElement;
		this.webDriver = webDriver;
	}


	@Override
	public String label() throws CCAPIException {
		return Utils.findItemInElement(this.webDriver, this.webElement, By.className("gwt-Label")).getText();
	}


	@NotNull
	@Override
	public WebElement html() {
		return this.webElement;
	}


	@Override
	public List<CCInputComboBoxItem> items() throws CCAPIException {
		final WebElement listBoxElement = Utils.findItemInElement(this.webDriver, this.webElement, By.className("fs-listbox-text"));
		listBoxElement.click();

		final List<WebElement> options = this.getOptions();

		final List<CCInputComboBox.CCInputComboBoxItem> resultList = new ArrayList<>();
		for (final WebElement option : options) {
			resultList.add(new CCInputComboBoxItemImpl(this.webDriver, this.webElement, option.getText(), option.getAttribute("value")));
		}
		listBoxElement.click();
		return resultList;
	}


	private List<WebElement> getOptions() {
		final WebElement listBoxPopup = Utils.find(this.webDriver, ExpectedConditions.presenceOfElementLocated(By.className("fs-listbox-popup")));
		return Utils.findMultipleItemsInElement(this.webDriver, listBoxPopup, By.tagName("option"));
	}


	@Override
	public CCInputComboBox.CCInputComboBoxItem itemByName(final String displayName) throws CCAPIException {
		final WebElement listBoxElement = Utils.findItemInElement(this.webDriver, this.webElement, By.className("fs-listbox-text"));
		listBoxElement.click();

		final List<WebElement> options = this.getOptions();

		for (final WebElement option : options) {
			if (option.getText().equals(displayName)) {
				final CCInputComboBoxItemImpl inputComboBoxItem = new CCInputComboBoxItemImpl(this.webDriver, listBoxElement, option.getText(), option.getAttribute("value"));
				listBoxElement.click();
				return inputComboBoxItem;
			}
		}
		listBoxElement.click();

		throw new CCAPIException("Can't find item with the displayname: '" + displayName + "'", this.webDriver);
	}


	@Override
	public CCInputComboBoxItem selectedItem() throws CCAPIException {
		final WebElement listBoxElement = Utils.findItemInElement(this.webDriver, this.webElement, By.className("fs-listbox-text"));
		listBoxElement.click();

		final List<WebElement> options = this.getOptions();

		final String value = listBoxElement.getAttribute("value");

		for (final WebElement option : options) {
			if (option.getAttribute("value").equals(value)) {
				final CCInputComboBoxItemImpl inputComboBoxItem = new CCInputComboBoxItemImpl(this.webDriver, this.webElement, option.getText(), option.getAttribute("value"));
				listBoxElement.click();
				return inputComboBoxItem;
			}
		}

		listBoxElement.click();
		return null;
	}


	public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
		return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-listbox-text"));
	}

	private class CCInputComboBoxItemImpl implements CCInputComboBox.CCInputComboBoxItem {

		private final WebDriver webDriver;
		private final WebElement listBoxElement;
		private final String label;
		private final String value;


		public CCInputComboBoxItemImpl(@NotNull final WebDriver webDriver, @NotNull final WebElement listBoxElement, @NotNull final String label, @NotNull final String value) {
			this.webDriver = webDriver;
			this.listBoxElement = listBoxElement;
			this.label = label;
			this.value = value;
		}


		@Override
		public void select() throws CCAPIException {
			this.listBoxElement.click();
			final WebElement listBoxPopup = Utils.find(this.webDriver, ExpectedConditions.presenceOfElementLocated(By.className("fs-listbox-popup")));
			final List<WebElement> options = Utils.findMultipleItemsInElement(this.webDriver, listBoxPopup, By.tagName("option"));

			for (final WebElement option : options) {
				if (option.getAttribute("value").equals(this.value)) {
					option.click();
					return;
				}
			}
		}


		@Override
		public String label() {
			return this.label;
		}
	}
}
