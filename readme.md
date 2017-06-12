# FirstSpirit ContentCreator UI-Testing Framework

This is a testing framework for the FirstSpirit ContentCreator. It supports the Chrome and PhantomJS browsers.

## Environments variable

* errorFilePath= Path to the error files
* webdriver.chrome.executable= Path to the chrome executable
* webdriver.chrome.verbose= Verbosity level
* webdriver.phantomjs.executable= Path to the phantomjs executable
* webdriver.ie.executable= Path to the ie executable
* locale= The locale to use (default: en)
* host= Host address (default: localhost)
* port= Host port (default: 8000)
* user= The user (default: Admin)
* password= The password (default: Admin)
* projectName= Name of the projectName
* useHttps= Use https for connection to FS-Server (default: false)
* loginhook= Full qualified classname of implemented LoginHook (optional, overrides annotation)

## Annotations

Additionally test classes and methods can be parameterized with the following annotations.


* @UiTestRunner.WebDriver() - Specifies which WebDrivers will be used
* @UiTestRunner.UseLoginHook() - Specifies which LoginHook implementations will be used (optional, overrides by environment variable)
* @BrowserLocale("en") - Specifies the locale to use within the test method/class (overwrites the environment variable)
* @ClassPattern("de.espirit.firstspirit.webedit.*.UiTest*") - Defines which UI tests should be executed, by specifying a classname pattern
* @Classes(value = {Test1.class, Test2.class}) - Defines which UI tests should be executed, by specifying concrete classes
