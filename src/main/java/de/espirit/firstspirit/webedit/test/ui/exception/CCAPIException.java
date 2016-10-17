package de.espirit.firstspirit.webedit.test.ui.exception;

import de.espirit.common.UncheckedException;
import de.espirit.firstspirit.webedit.test.ui.util.ErrorHandler;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CCAPIException extends UncheckedException {
    private static final Logger LOGGER = Logger.getLogger(CCAPIException.class);
    private static final long serialVersionUID = -6164462209555219805L;

    public CCAPIException(final String message, final WebDriver webDriver) {
        super(message);
        ErrorHandler.handleError(webDriver);
    }


}
