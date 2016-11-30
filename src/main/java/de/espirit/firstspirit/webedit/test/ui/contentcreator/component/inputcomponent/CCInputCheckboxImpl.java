package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class CCInputCheckboxImpl implements CCInputCheckbox {

	private final WebElement webElement;
	private final List<WebElement> items;
	private final WebDriver webDriver;


	public CCInputCheckboxImpl(@NotNull final WebElement webElement, final WebDriver webDriver) throws CCAPIException {
		this.webElement = webElement;
		this.items = Utils.findMultipleItemsInElement(webDriver, webElement, By.className("fs-checkbox"));
		this.webDriver = webDriver;
	}


	@Override
	public String label() throws CCAPIException {
		return Utils.findItemInElement(this.webDriver, this.webElement, By.className("gwt-Label")).getText();

	}


	@Override
	public List<CCInputCheckboxItem> items() {
		List<CCInputCheckboxItem> resultList;
		resultList = new ArrayList<>();
		for (final WebElement element : this.items) {
			resultList.add(new CCInputCheckboxItemImpl(element, this.webDriver));
		}
		return resultList;
	}


	@Override
	public CCInputCheckboxItem itemByName(@NotNull final String displayName) throws CCAPIException {
		for (final WebElement element : this.items) {
			if (Utils.findItemInElement(this.webDriver, this.webElement, By.className("fs-checkbox-label")).getText().equals(displayName)) {
				return new CCInputCheckboxItemImpl(element, this.webDriver);
			}
		}

		throw new CCAPIException("Can't find item with the displayname: '" + displayName + "'", this.webDriver);
	}


	@NotNull
	@Override
	public WebElement html() {
		return this.webElement;
	}


	public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
		return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-checkbox"));
	}
}
