package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.input.CCInputEntry;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.exception.CCInputNotPresentException;

public class CustomReport extends AbstractReport {

	public CustomReport(@NotNull final WebDriver webDriver, @NotNull final WebElement reportButton) {
		super(webDriver, reportButton);
	}


	/*
	 * (non-Javadoc)
	 * @see de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.Report#query(java.util.List)
	 */
	@Override
	public void query(final List<CCInputEntry<?>> inputs) throws CCAPIException, CCInputNotPresentException {
		// TODO Auto-generated method stub

	}
}
