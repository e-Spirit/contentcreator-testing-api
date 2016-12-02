package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class SearchReportImpl extends AbstractReport implements SearchReport {

	private final WebDriver webDriver;


	public SearchReportImpl(@NotNull final WebDriver webDriver, @NotNull final WebElement button) {
		super(webDriver, button);
		this.webDriver = webDriver;
	}


	@Override
	public void setParamMyElements(final boolean onlyMyElements) throws CCAPIException {
		final WebElement webElement = Utils.findElement(this.webDriver, By.className("fs-checkbox-label"));
		if (webElement.getAttribute("class").contains("fs-checkbox-checked") != onlyMyElements) {
			webElement.click();
		}
	}


	@Nullable
	@Override
	public String reportMessage() throws CCAPIException {
		final WebElement reportMsg = Utils.findElement(this.webDriver, By.className("report-message"));
		return reportMsg.isDisplayed() ? reportMsg.getText() : null;
	}


	@NotNull
	@SuppressWarnings("null")
	@Override
	public WebElement html() throws CCAPIException {
		if (ComponentUtils.hasElement(this.webDriver, By.className("fs-sidebar-content"))) {
			return Utils.findElement(this.webDriver, By.className("fs-sidebar-content"));
		}

		throw new CCAPIException("coudn't find the html source", this.webDriver);
	}


	@NotNull
	@Override
	public WebElement button() {
		return this.reportButton;
	}
}