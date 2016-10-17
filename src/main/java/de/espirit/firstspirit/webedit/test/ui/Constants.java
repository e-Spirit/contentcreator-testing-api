package de.espirit.firstspirit.webedit.test.ui;

import java.util.Locale;

public abstract class Constants {

    /**
     * Time in seconds the webdriver should be wait
     */
    public static final int WEBDRIVER_WAIT = 10;

    /**
     * Environment parameter for the path to error files
     */
    public static final String PARAM_ERROR_FILE_PATH = "errorFilePath";
    public static final String DEFAULT_ERROR_FILE_PATH = null;

    /**
     * Environment parameter for the path to error files
     */
    public static final String PARAM_ERROR_SUBDIRECTORY = "errorSubdirectory";
    public static final String DEFAULT_ERROR_SUBDIRECTORY = String.valueOf(System.currentTimeMillis());

    /**
     * Environment parameter for the locale
     */
    public static final String PARAM_LOCALE = "locale";
    public static final String DEFAULT_LOCALE = Locale.ENGLISH.getLanguage();
}
