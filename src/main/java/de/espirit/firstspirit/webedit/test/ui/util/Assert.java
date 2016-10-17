package de.espirit.firstspirit.webedit.test.ui.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Assert {
	private static final Logger LOGGER = Logger.getLogger(Assert.class);

	public static void assertTrue(final String message, final boolean condition, final WebDriver webDriver){
		try {
			org.junit.Assert.assertTrue(message, condition);
		} catch (AssertionError e) {
			ErrorHandler.handleError(webDriver);
			LOGGER.error("", e);
		}
	}

}
