package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.input.CCInputEntry;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.exception.CCInputNotPresentException;
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
	@Override
	public WebElement html() throws CCAPIException {
		if (ComponentUtils.hasElement(this.webDriver, By.className("fs-sidebar-content"))) {
			return Utils.findElement(this.webDriver, By.className("fs-sidebar-content"));
		}

		return null;
	}


	@NotNull
	@Override
	public WebElement button() {
		return this.reportButton;
	}


	/*
	 * (non-Javadoc)
	 * @see de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.Report#query(java.util.List)
	 */
	@Override
	public void query(final List<CCInputEntry<?>> inputs) throws CCAPIException, CCInputNotPresentException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}
}