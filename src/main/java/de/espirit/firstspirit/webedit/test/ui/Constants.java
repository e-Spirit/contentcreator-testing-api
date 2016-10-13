package de.espirit.firstspirit.webedit.test.ui;

import java.util.Locale;

public interface Constants {

    /**
     * Time in seconds the webdriver should be wait
     */
    int WEBDRIVER_WAIT = 10;

    /**
     * Environment parameter for the path to error files
     */
    String PARAM_ERROR_FILE_PATH = "errorFilePath";
    String DEFAULT_ERROR_FILE_PATH = null;

    /**
     * Environment parameter for the locale
     */
    String PARAM_LOCALE = "locale";
    String DEFAULT_LOCALE = Locale.ENGLISH.getLanguage();
}
