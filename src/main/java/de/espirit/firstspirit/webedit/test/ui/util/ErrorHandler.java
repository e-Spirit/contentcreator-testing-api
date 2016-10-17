package de.espirit.firstspirit.webedit.test.ui.util;

import de.espirit.firstspirit.webedit.test.ui.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.env;

public class ErrorHandler {
	private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class);

	private ErrorHandler() {

	}

	public static void handleError(final WebDriver webDriver) {
		if(env(Constants.PARAM_ERROR_FILE_PATH, Constants.DEFAULT_ERROR_FILE_PATH) != null && webDriver instanceof PhantomJSDriver) {
			final long timestamp = new Date().getTime();
			takeScreenshot(timestamp, webDriver);
			printPageSource(timestamp, webDriver);
		}
	}



	private static void printPageSource(final long timestamp, final WebDriver webDriver) {
		try {
			final String fileName = getErrorFilePath() +"/pageSource-"+timestamp+".html";
			final File pageSource = new File(fileName);
			FileUtils.write(pageSource, webDriver.getPageSource(), "UTF-8");
			LOGGER.info("Page source saved to " + fileName);
		} catch (final IOException e) {
			LOGGER.error("", e);
		}
	}

	private static void takeScreenshot(final long timestamp, final WebDriver webDriver) {
		final java.io.File screenshot = ((PhantomJSDriver) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			final String fileName = getErrorFilePath() +"/screenshot-"+timestamp+".png";
			FileUtils.copyFile(screenshot, new File(fileName));
			LOGGER.info("Screenshot saved to " + fileName);
		} catch (final IOException e) {
			LOGGER.error("", e);
		}
	}

	private static String getErrorFilePath(){
		return env(Constants.PARAM_ERROR_FILE_PATH, Constants.DEFAULT_ERROR_FILE_PATH) + '/' + env(Constants.PARAM_ERROR_SUBDIRECTORY, Constants.DEFAULT_ERROR_SUBDIRECTORY);
	}
}
