package de.espirit.firstspirit.webedit.test.ui.exception;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import de.espirit.common.UncheckedException;
import de.espirit.firstspirit.webedit.test.ui.util.ErrorHandler;

public class CCAPIException extends UncheckedException {

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
