package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class MenuImpl implements Menu {

	private final WebDriver webDriver;
	private final By selector;


	public MenuImpl(@NotNull final WebDriver webDriver, @NotNull final By selector) {
		this.webDriver = webDriver;
		this.selector = selector;
	}


	@Override
	public MenuItem menuItem(final String displayName) throws CCAPIException {
		final WebElement menuElement = this.open();

		try {
			new WebDriverWait(this.webDriver, Constants.WEBDRIVER_WAIT).until((ExpectedCondition<Boolean>) d -> {
				final List<WebElement> li = Utils.findMultipleItemsInElement(MenuImpl.this.webDriver, menuElement, By.tagName("li"));
				for (final WebElement element : li) {
					if (element.getText().equals(displayName)) {
						return true;
					}
				}
				return false;
			});
		} catch (final RuntimeException e) {
			throw new CCAPIException(e.getMessage(), this.webDriver, e);
		}

		final List<WebElement> items = Utils.findMultipleItemsInElement(this.webDriver, menuElement, By.tagName("li"));
		final WebElement item = items.stream().filter(i -> i.getText().equals(displayName)).findFirst().orElse(null);

		if (item != null) {
			return new MenuItemImpl(this, item.getText(), this.webDriver);
		} else {
			throw new CCAPIException("Can't find menu item with the displayname: '" + displayName + "'", this.webDriver);
		}
	}


	@Override
	public List<MenuItem> menuItems() throws CCAPIException {
		final WebElement menuElement = this.open();
		final List<WebElement> menuWebElements = Utils.findMultipleItemsInElement(this.webDriver, menuElement, By.tagName("li"));

		final List<MenuItem> menuItems = new ArrayList<>();

		for (final WebElement menuItem : menuWebElements) {
			menuItems.add(new MenuItemImpl(this, menuItem.getText(), this.webDriver));
		}

		return menuItems;
	}

	/**
	 * TODO: Move this in order to make use of this elsewhere. See: PSCCT-15
	 */
	public static final String JS_MOUSEOVER = "var evObj = document.createEvent('MouseEvents');" + "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" + "arguments[0].dispatchEvent(evObj);";


	@Override
	public WebElement open() throws CCAPIException {
		final WebElement button = Utils.find(this.webDriver, ExpectedConditions.elementToBeClickable(this.selector));
		((JavascriptExecutor) this.webDriver).executeScript(MenuImpl.JS_MOUSEOVER, button);
		return Utils.find(this.webDriver, ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.gwt-PopupPanel.fs-toolbar-flyout")));
	}


	@Override
	public WebElement html() throws CCAPIException {
		return this.open();
	}
}