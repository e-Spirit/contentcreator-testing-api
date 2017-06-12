package de.espirit.firstspirit.webedit.test.ui.exception;

import de.espirit.firstspirit.webedit.test.ui.util.ErrorHandler;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CCAPIException extends RuntimeException { //UncheckedException {

	private static final Logger LOGGER = Logger.getLogger(CCAPIException.class);
	private static final long serialVersionUID = -6164462209555219805L;


	/**
	 * @param message
	 * @param webDriver
	 */
	public CCAPIException(final String message, final WebDriver webDriver) {
		super(message);
		ErrorHandler.handleError(webDriver);
	}


	/**
	 * @param message
	 * @param webDriver
	 * @param e
	 */
	public CCAPIException(final String message, final WebDriver webDriver, final Throwable e) {
		super(message, e);
		ErrorHandler.handleError(webDriver);
	}

}
