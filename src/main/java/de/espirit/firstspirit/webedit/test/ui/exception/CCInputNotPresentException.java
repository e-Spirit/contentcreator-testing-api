package de.espirit.firstspirit.webedit.test.ui.exception;

import org.openqa.selenium.WebDriver;

/**
 * @author Benjamin Nagel &lt;nagel@e-spirit.com&gt;
 */
public class CCInputNotPresentException extends CCAPIException {

	/**
	 *
	 */
	private static final long serialVersionUID = 240225105646413520L;


	/**
	 * @param message
	 * @param webDriver
	 */
	public CCInputNotPresentException(final String message, final WebDriver webDriver) {
		super(message, webDriver);
	}


	public CCInputNotPresentException(final String message, final WebDriver webDriver, final Throwable t) {
		super(message, webDriver);
		super.initCause(t);
	}

}
