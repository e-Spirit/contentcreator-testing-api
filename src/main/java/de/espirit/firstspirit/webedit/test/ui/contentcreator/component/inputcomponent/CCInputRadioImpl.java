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

public class CCInputRadioImpl implements CCInputRadio {

	private final WebDriver webDriver;
	private final WebElement webElement;
	private final List<WebElement> items;


	public CCInputRadioImpl(final WebDriver webDriver, @NotNull final WebElement webElement) throws CCAPIException {
		this.webDriver = webDriver;
		this.webElement = webElement;
		this.items = Utils.findMultipleItemsInElement(webDriver, webElement, By.className("fs-radiobutton"));

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
	public List<CCInputRadioItem> items() {
		final List<CCInputRadioItem> resultList = new ArrayList<>();
		for (final WebElement element : this.items) {
			resultList.add(new CCInputRadioItemImpl(element));
		}
		return resultList;
	}


	@Override
	public CCInputRadioItem itemByName(final String displayName) throws CCAPIException {
		for (final WebElement element : this.items) {
			if (Utils.findItemInElement(this.webDriver, element, By.className("fs-radiobutton-label")).getText().equals(displayName)) {
				return new CCInputRadioItemImpl(element);
			}
		}

		throw new CCAPIException("Can't find item with the displayname: '" + displayName + "'", this.webDriver);
	}


	@Override
	public CCInputRadioItem selectedItem() {
		for (final WebElement item : this.items) {
			if (item.getAttribute("class").contains("fs-radiobutton-selected")) {
				return new CCInputRadioItemImpl(item);
			}
		}

		return null;
	}


	public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
		return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-radiobutton"));
	}

	private class CCInputRadioItemImpl implements CCInputRadioItem {

		private final WebElement element;


		public CCInputRadioItemImpl(@NotNull final WebElement element) {
			this.element = element;
		}


		@NotNull
		@Override
		public WebElement html() {
			return this.element;
		}


		@Override
		public void select() {
			this.element.click();
		}


		@Override
		public String label() throws CCAPIException {
			return Utils.findItemInElement(CCInputRadioImpl.this.webDriver, this.element, By.className("fs-radiobutton-label")).getText();
		}


		@Override
		public boolean checked() {
			return this.element.getAttribute("class").contains("fs-radiobutton-selected");
		}
	}
}
