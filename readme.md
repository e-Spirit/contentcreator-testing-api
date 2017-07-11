# FirstSpirit ContentCreator UI-Testing Framework

This is a testing framework for the FirstSpirit ContentCreator. It supports the Chrome and PhantomJS browsers.

## Environments variable

* **locale** - The locale to use
    * default: en
* **host** - Host address
    * default: *localhost*
* **port** - Host port
    * default: *8000*
* **user** - The user
    * default: *Admin*
* **password** - The password
    * default: *Admin*
* **project** - Name of the project
    * default: *Mithras Energey*
* **errorFilePath** - Path to the error/log files
    * default: *no error logging*
* **webdriver.chrome.executable** - Path to the chrome executable
    * default: *D:\Entwicklung\Chromedriver\chromedriver.exe*
* **webdriver.chrome.verbose** - Chrome verbosity true/false
    * default: *false*
* **webdriver.phantomjs.executable** - Path to the phantomjs executable
    * default: *D:\Entwicklung\phantomjs\bin\phantomjs.exe*
* **webdriver.ie.executable** - Path to the internet explorer executable
    * default: *D:\Entwicklung\IEDriverServer\IEDriverServer.exe*
* **useHttps** - Use https for connection to FS-Server
    * default: *false*
* **loginhook** - Full qualified classname of implemented LoginHook (optional, overrides annotation)
    
## Annotations

In order to specify the webdriver which should be used, the *UiTestRunner.WebDriver* annoation has to be added to the test class.

* **@UiTestRunner.WebDriver({...WebDriverFactory.class})**

These are the default webdriver factories that are provided by the ContentCreator UI-Testing Framework:

* **LocalChromeWebDriverFactory**
* **LocalPhantomJSWebDriverFactory**
* **LocalIEWebDriverFactory**
* **RemoteChromeWebDriverFactory**
* **RemoteFirefoxWebDriverFactory**
* **RemoteIEWebDriverFactory**

If you want to use your own webdriver factory just extend the *WebDriverFactory* class and implement the factory method.

Additionally test classes and methods can be parameterized with the following annotations.

* @UiTestRunner.WebDriver() - Specifies which WebDrivers will be used
* @UiTestRunner.UseLoginHook() - Specifies which LoginHook implementations will be used (optional, overrides by environment variable)
* @BrowserLocale("en") - Specifies the locale to use within the test method/class (overwrites the environment variable)
* @ClassPattern("de.espirit.firstspirit.webedit.*.UiTest*") - Defines which UI tests should be executed, by specifying a classname pattern
* @Classes(value = {Test1.class, Test2.class}) - Defines which UI tests should be executed, by specifying concrete classes
