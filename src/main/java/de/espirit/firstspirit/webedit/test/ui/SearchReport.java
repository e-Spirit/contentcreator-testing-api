package de.espirit.firstspirit.webedit.test.ui;

import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.WebElement;


/**
 * Access to the search report.
 */
public interface SearchReport extends Report {

	/**
	 * Sets the flag {@code myElements}.
	 *
	 * @param onlyMyElements {@code true} to search only elements that I've created or changed, {@code false} to search all elements.
	 */
	void setParamMyElements(final boolean onlyMyElements);


	/**
	 * Returns the text-content of the report-message element or {@code null} if it doesn't exist or isn't {@link WebElement#isDisplayed() displayed}.
	 *
	 * @return report-message text or {@code null}.
	 */
	@Nullable
	String reportMessage();

}
