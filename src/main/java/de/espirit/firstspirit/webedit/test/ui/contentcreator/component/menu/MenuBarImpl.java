package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Predicate;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.CustomConditions;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class MenuBarImpl implements MenuBar {

	private final WebDriver webDriver;


	public MenuBarImpl(@NotNull final WebDriver webDriver) {
		this.webDriver = webDriver;
	}


	@NotNull
	@Override
	public ElementStatus getElementStatus() throws CCAPIException {
		int max = 5;
		do {
			final String cssClass = this.html().getAttribute("class");
			if (cssClass.contains("workflow")) {
				return ElementStatus.IN_WORKFLOW;
			}
			if (cssClass.contains("changed")) {
				return ElementStatus.CHANGED;
			}
			if (cssClass.contains("released")) {
				return ElementStatus.RELEASED;
			}
			if (cssClass.contains("deleted")) {
				return ElementStatus.DELETED;
			}
			if (cssClass.contains("archived")) {
				return ElementStatus.ARCHIVED;
			}
			Utils.idle();
		} while (--max > 0);
		return ElementStatus.UNKNOWN;
	}


	@Override
	public void waitForElementStatus(final MenuBar.ElementStatus elementStatus) {
		new WebDriverWait(this.webDriver, 30).until((Predicate<WebDriver>) webDriver1 -> this.getElementStatus().equals(elementStatus));
	}


	@NotNull
	@Override
	public Menu workflowMenu() {
		return new MenuImpl(this.webDriver, By.className("fs-toolbar-state"));
	}


	@NotNull
	@Override
	public Menu actionMenu() {
		return new MenuImpl(this.webDriver, By.cssSelector("#fs-toolbar > div.fs-toolbar-content.fs-toolbar-content-main > div:nth-child(5) > div > div:nth-child(3)"));
	}


	@NotNull
	@Override
	public Menu contentMenu() {
		return new MenuImpl(this.webDriver, By.cssSelector("#fs-toolbar > div.fs-toolbar-content.fs-toolbar-content-main > div:nth-child(5) > div > div:nth-child(1)"));
	}


	@Override
	public void search(final String query) throws CCAPIException {
		final WebElement element = Utils.find(this.webDriver, ExpectedConditions.presenceOfElementLocated(By.className("fs-searchtextbox-field")));
		element.click();
		element.sendKeys(query + '\n');
	}


	@NotNull
	@Override
	public WebElement html() throws CCAPIException {
		return Utils.find(this.webDriver, ExpectedConditions.presenceOfElementLocated(By.className("fs-toolbar-state")));
	}


	/*
	 * (non-Javadoc)
	 * @see de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu.MenuBar#getSearchResultCount()
	 */
	@Override
	public int getSearchResultCount() {
		final WebElement element = Utils.find(this.webDriver, ExpectedConditions.presenceOfElementLocated(By.className("fs-sidebar-report-status-count")));
		new WebDriverWait(this.webDriver, Constants.WEBDRIVER_WAIT).until(CustomConditions.resultChanged(element));
		final String text = element.getText(); // text = "Ergebnisse: 47"
		if (!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1))) {
			return Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1));
		}
		return -1;
	}

}