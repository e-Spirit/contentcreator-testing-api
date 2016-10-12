package de.espirit.firstspirit.webedit.test.ui.exception;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CCAPIException extends Exception {
    private static final Logger LOGGER = Logger.getLogger(CCAPIException.class);
    private final WebDriver webDriver;
    private static String errorFilePath = System.getenv("errorFilePath");

    public CCAPIException(String message, WebDriver webDriver) {
        super(message);
        this.webDriver = webDriver;

        if(errorFilePath != null && webDriver instanceof PhantomJSDriver) {
            long timestamp = new Date().getTime();
            takeScreenshot(timestamp);
            printPageSource(timestamp);
        }
    }

    private void printPageSource(long timestamp) {
        File pageSource = new File(errorFilePath +"\\pageSource-"+timestamp+".html");

        try {
            FileUtils.write(pageSource, webDriver.getPageSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void takeScreenshot(long timestamp) {
        final java.io.File screenshot = ((PhantomJSDriver) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(errorFilePath +"\\screenshot-"+timestamp+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.error("Screenshot saved to " + errorFilePath);
    }
}
