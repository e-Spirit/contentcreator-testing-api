package de.espirit.firstspirit.webedit.test.ui.loginhook;


import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;



/**
 * A LoginHook establishes the connection to the FirstSpirit server. It takes over the task of establishing a
 * connection on instantiation and create an {@link FS} object.
 */
public interface LoginHook {
	
	// --- -D parameter names ---//
	String PARAM_PROJECT  = "project";
	String PARAM_HOST     = "host";
	String PARAM_PORT     = "port";
	String PARAM_USER     = "user";
	String PARAM_PASSWORD = "password";
	String PARAM_USEHTTPS = "useHttps";
	
	// --- default values ---//
	String DEFAULT_PROJECT_NAME = "Mithras Energy";
	String DEFAULT_HOST         = "localhost";
	String DEFAULT_PORT			= "8000";
	String DEFAULT_USERNAME     = "Admin";
	String DEFAULT_PASSWORD     = "Admin";
	String DEFAULT_USEHTTPS     = "false";
	
	
	/**
	 * Creates and returns the {@link CC} object.
	 *
	 * @param uiTestRunner
	 * @param browserRunner
	 * @return {@link CC} object.
	 */
	CC createCC(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner);
	
	
	/**
	 * Creates and returns the connected {@link FS} object.
	 *
	 * @param uiTestRunner
	 * @param browserRunner
	 * @return {@link FS} object.
	 */
	FS createFS(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner);
	
	
	/**
	 * Returns the {@link CC} object.
	 *
	 * @return {@link CC} object.
	 */
	CC getCC();
	
	
	/**
	 * Returns the connected {@link FS} object.
	 *
	 * @return {@link FS} object.
	 */
	FS getFS();
	
	
	/**
	 * Closes the FirstSpirit connection.
	 */
	void tearDownFS();
	
	
	/**
	 * Closes the ContentCreator connection.
	 */
	void tearDownCC();
}
