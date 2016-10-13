# FirstSpirit ContentCreator UI-Testing Framework

This is a testing framework for the FirstSpirit ContentCreator. It supports the Chrome and PhantomJS browsers.

## Environments variable

* errorFilePath= Path to the error files
* webdriver.chrome.executable= Path to the chrome executable
* webdriver.chrome.verbose= Verbosity level
* webdriver.phantomjs.executable= Path to the phantomjs executable
* locale= The locale to use (default: en)
* host= Host address
* port= Host port
* user= The user
* password= The password
* project= Name of the project

## Annotations

Additionally test classes and methods can be parameterized with the following annotations.

* @BrowserLocale("en") - Specifies the locale to use within the test method/class (overwrites the environment variable)
* @ClassPattern("de.espirit.firstspirit.webedit.*.UiTest*") - Defines which UI tests should be executed, by specifying a classname pattern
* @Classes(value = {Test1.class, Test2.class}) - Defines which UI tests should be executed, by specifying concrete classes
