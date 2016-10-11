package de.espirit.firstspirit.webedit.test.ui.exception;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CCAPIException extends Exception {
    private final WebDriver webDriver;

    public CCAPIException(String message, WebDriver webDriver) {
        super(message);
        this.webDriver = webDriver;

        takeScreenshot();
    }

    private void takeScreenshot() {
        final java.io.File screenshot = ((PhantomJSDriver) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("D:\\screenshot_"+new Date().getTime()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
