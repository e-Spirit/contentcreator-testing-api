package de.espirit.firstspirit.webedit.test.ui.util;

import de.espirit.firstspirit.webedit.test.ui.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class);

    //Prevent class instantiation
    private ErrorHandler() {}

    /**
     * Handles the error by printing logs and taking a screenshot
     *
     * @param webDriver
     */
    public static void handleError(final WebDriver webDriver) {
        if ((Utils.env(Constants.PARAM_ERROR_FILE_PATH, Constants.DEFAULT_ERROR_FILE_PATH) != null)
                && ((webDriver instanceof PhantomJSDriver) || (webDriver instanceof ChromeDriver))) {
            final long timestamp = new Date().getTime();
            ErrorHandler.takeScreenshot(timestamp, webDriver);
            ErrorHandler.printPageSource(timestamp, webDriver);
            ErrorHandler.printBrowserConsoleLog(timestamp, webDriver);
        }
    }

    /**
     * Prints the browser console log to a log file
     *
     * @param timestamp
     * @param webDriver
     */
    private static void printBrowserConsoleLog(final long timestamp, final WebDriver webDriver) {
        try {
            final String fileName = ErrorHandler.getErrorFilePath() + "/browserConsoleLog-" + timestamp + ".log";
            final File browserConsoleLogFile = new File(fileName);

            LogEntries browserConsoleLogEntries = webDriver.manage().logs().get(LogType.BROWSER);
            String browserConsoleLog = browserConsoleLogEntries.getAll().stream()
                    .map(LogEntry::toString)
                    .collect(Collectors.joining("\n"));

            FileUtils.write(browserConsoleLogFile, browserConsoleLog, "UTF-8");
            ErrorHandler.LOGGER.info("Browser console log saved to " + fileName);
        } catch (final IOException e) {
            ErrorHandler.LOGGER.error("", e);
        }
    }

    /**
     * Prints the html page source to a log file
     * @param timestamp
     * @param webDriver
     */
    private static void printPageSource(final long timestamp, final WebDriver webDriver) {
        try {
            final String fileName = ErrorHandler.getErrorFilePath() + "/pageSource-" + timestamp + ".html";
            final File pageSource = new File(fileName);
            FileUtils.write(pageSource, webDriver.getPageSource(), "UTF-8");
            ErrorHandler.LOGGER.info("Page source saved to " + fileName);
        } catch (final IOException e) {
            ErrorHandler.LOGGER.error("", e);
        }
    }

    /**
     * Takes a screenshot of the current browser view
     * This utility method only supports {@link PhantomJSDriver} and {@link ChromeDriver}
     *
     * @param timestamp
     * @param webDriver
     * @throws IllegalArgumentException thrown when the webDriver don't support screenshots
     */
    private static void takeScreenshot(final long timestamp, final WebDriver webDriver) throws IllegalArgumentException {
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
                final String fileName = ErrorHandler.getErrorFilePath() + "/screenshot-" + timestamp + ".png";
                FileUtils.copyFile(screenshot, new File(fileName));
                ErrorHandler.LOGGER.info("Screenshot saved to " + fileName);
            } catch (final IOException e) {
                ErrorHandler.LOGGER.error("", e);
            }
        }
    }

    public static String getErrorFilePath() {
        return Utils.env(Constants.PARAM_ERROR_FILE_PATH, Constants.DEFAULT_ERROR_FILE_PATH) + '/'
                + Utils.env(Constants.PARAM_ERROR_SUBDIRECTORY, Constants.DEFAULT_ERROR_SUBDIRECTORY);
    }
}
