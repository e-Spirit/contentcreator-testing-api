package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class ReportsImpl implements Reports {

	private final WebDriver webDriver;


	public ReportsImpl(@NotNull final WebDriver webDriver) {
		this.webDriver = webDriver;
	}


	@NotNull
	@Override
	public SearchReport search() throws CCAPIException {
		final WebElement searchReportButton = Utils.findElement(this.webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(1)"));
		return new SearchReportImpl(this.webDriver, searchReportButton);
	}


	@NotNull
	@Override
	public Report bookmarks() throws CCAPIException {
		return this.custom(-5);
	}


	@NotNull
	@Override
	public Report workflows() throws CCAPIException {
		return this.custom(-4);
	}


	@NotNull
	@Override
	public Report history() throws CCAPIException {
		return this.custom(-3);
	}


	@NotNull
	@Override
	public Report relations() throws CCAPIException {
		return this.custom(-2);
	}


	@NotNull
	@Override
	public Report messages() throws CCAPIException {
		return this.custom(-1);
	}


	@NotNull
	@Override
	public Report custom(final int no) throws CCAPIException {
		final WebElement button = Utils.findElement(this.webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(" + (7 + no) + ')'));
		return new CustomReport(this.webDriver, button);
	}


	@NotNull
	@Override
	public Report customByName(final String displayName) throws CCAPIException {
		final List<WebElement> reportElements = Utils.findElements(this.webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div"));

		for (final WebElement reportElement : reportElements) {
			final WebElement displayTextElement = Utils.findItemInElement(this.webDriver, reportElement, By.cssSelector("div.text"));
			if (displayTextElement.getAttribute("textContent").equals(displayName)) {
				return new CustomReport(this.webDriver, reportElement);
			}
		}

		throw new CCAPIException("Can't find report with the displayname: '" + displayName + "'", this.webDriver);
	}


	@NotNull
	@Override
	public WebElement html() throws CCAPIException {
		return Utils.findElement(this.webDriver, By.cssSelector(".fs-sidebar"));
	}
}