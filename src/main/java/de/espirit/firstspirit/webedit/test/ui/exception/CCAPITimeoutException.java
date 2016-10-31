package de.espirit.firstspirit.webedit.test.ui.exception;

import de.espirit.firstspirit.webedit.test.ui.util.ErrorHandler;
import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class CCAPITimeoutException extends TimeoutException {
	private static final Logger LOGGER = Logger.getLogger(CCAPITimeoutException.class);

	public CCAPITimeoutException(final String message, final WebDriver webDriver) {
		super(message);
		ErrorHandler.handleError(webDriver);
	}

}
