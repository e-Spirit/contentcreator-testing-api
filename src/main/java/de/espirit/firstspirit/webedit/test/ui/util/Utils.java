package de.espirit.firstspirit.webedit.test.ui.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

public class Utils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class);
	/**
	 * After processing idle time for a lot of HTTP operations.
	 */
	private static final int WAIT = 250;


	public static String env(final String name, @Nullable final String defValue) {
		final String env = System.getenv(name);
		if (env != null) {
			return env;
		}

		return System.getProperty(name, defValue);
	}


	/**
	 * Finds element by the given condition
	 *
	 * @param webDriver The webdriver instance
	 * @param by The condition
	 * @return Returns the web element if found
	 */
	public static WebElement findElement(@NotNull final WebDriver webDriver, @NotNull final By by) throws CCAPIException {
		try {
			return webDriver.findElement(by);
		} catch (final WebDriverException exception) {
			throw new CCAPIException(exception.getMessage(), webDriver, exception);
		}
	}


	/**
	 * Finds a number of elements by the given condition
	 *
	 * @param webDriver The webdriver instance
	 * @param by The condition
	 * @return Returns a number of web elements if found
	 */
	public static List<WebElement> findElements(@NotNull final WebDriver webDriver, @NotNull final By by) throws CCAPIException {
		try {
			return webDriver.findElements(by);
		} catch (final WebDriverException exception) {
			throw new CCAPIException(exception.getMessage(), webDriver, exception);
		}
	}


	/**
	 * Finds a web element by the given condition
	 *
	 * @param webDriver The webdriver instance
	 * @param expectedCondition The condition
	 * @return Returns the web element if found
	 */
	public static WebElement find(@NotNull final WebDriver webDriver, @NotNull final ExpectedCondition<WebElement> expectedCondition) throws CCAPIException {
		try {
			return new WebDriverWait(webDriver, Constants.WEBDRIVER_WAIT).until(expectedCondition);
		} catch (final WebDriverException exception) {
			throw new CCAPIException(exception.getMessage(), webDriver, exception);
		}
	}


	/**
	 * Finds a element in the web element by the given condition
	 *
	 * @param webDriver The webdriver instance
	 * @param webElement The webelement
	 * @param by The condition
	 * @return Returns the web element if found
	 */
	public static WebElement findItemInElement(@NotNull final WebDriver webDriver, @NotNull final WebElement webElement, @NotNull final By by) throws CCAPIException {
		try {
			return webElement.findElement(by);
		} catch (final WebDriverException exception) {
			throw new CCAPIException(exception.getMessage(), webDriver, exception);
		}
	}


	/**
	 * Finds a number of elements in the web element by the given condition
	 *
	 * @param webDriver The webdriver instance
	 * @param webElement The webelement
	 * @param by The condition
	 * @return Returns a number of web elements if found
	 */
	public static List<WebElement> findMultipleItemsInElement(@NotNull final WebDriver webDriver, @NotNull final WebElement webElement, @NotNull final By by) throws CCAPIException {
		try {
			return webElement.findElements(by);
		} catch (final WebDriverException exception) {
			throw new CCAPIException(exception.getMessage(), webDriver, exception);
		}
	}


	/**
	 * Sleeps {@link #WAIT} milliseconds.
	 */
	public static void idle() {
		try {
			Thread.sleep(Utils.WAIT); // let it load
		} catch (final InterruptedException e) {
			throw new RuntimeException("waiting interrupted!", e);
		}
	}


	/**
	 * Waits for the CC to be fully loaded
	 *
	 * @param webDriver the webdriver to use
	 */
	public static void waitForCC(final WebDriver webDriver) {
		new WebDriverWait(webDriver, 20).until(CustomConditions.waitForCC());
	}


	/**
	 * Waits for the CC to load a specified page
	 *
	 * @param webDriver the webdriver to use
	 */
	public static void waitForPage(@NotNull final WebDriver webDriver, final long idToWaitFor) {
		new WebDriverWait(webDriver, 20).until((ExpectedCondition<Boolean>) d -> {

			final boolean weApiAvailable = ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API !== 'undefined'").equals(Boolean.TRUE);

			if (weApiAvailable && ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API.Common.getPreviewElement() !== 'undefined'").equals(Boolean.TRUE)) {
				if (d != null) {
					final WebElement previewFrame = d.findElement(By.id("previewContent"));
					webDriver.switchTo().frame(previewFrame);
					final boolean previewState = ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete");
					webDriver.switchTo().defaultContent();

					if (previewState && Objects.equals(((JavascriptExecutor) d).executeScript("return WE_API.Common.getPreviewElement().getId()"), idToWaitFor)) {
						return true;
					}
				}
			}

			return false;
		});
	}


	/**
	 * Takes a screenshot of the whole page and stores it below the error-file-path
	 *
	 * @param webDriver the webdriver to use
	 * @param myfilename the filename to use, extension will be automatically added
	 */
	@Nullable
	public static File takeScreenshot(final WebDriver webDriver, final String myfilename) {
		if ((Utils.getErrorFilePath() != null) && (webDriver instanceof PhantomJSDriver)) {
			java.io.File screenshot = null;
			if (webDriver instanceof PhantomJSDriver) {
				screenshot = ((PhantomJSDriver) webDriver).getScreenshotAs(OutputType.FILE);
			} else if (webDriver instanceof ChromeDriver) {
				screenshot = ((ChromeDriver) webDriver).getScreenshotAs(OutputType.FILE);
			}
			if (screenshot == null) {
				throw new IllegalArgumentException("webDriver don't support screenshots");
			} else {
				try {
					final String fileName = ErrorHandler.getErrorFilePath() + "/" + myfilename + ".png ";
					FileUtils.copyFile(screenshot, new File(fileName));
					Utils.LOGGER.info("Screenshot saved to " + fileName);
					return screenshot;
				} catch (final IOException e) {
					Utils.LOGGER.error("", e);
				}
			}
		}
		return null;
	}


	/**
	 * Returns the error-file-path
	 *
	 * @return Returns the error-file-path
	 */
	public static String getErrorFilePath() {
		return Utils.env(Constants.PARAM_ERROR_FILE_PATH, Constants.DEFAULT_ERROR_FILE_PATH);
	}

}
