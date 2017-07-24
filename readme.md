# FirstSpirit ContentCreator UI-Testing Framework

This is a testing framework for the FirstSpirit ContentCreator. It supports various browser engines (e.g. Chrome and PhantomJS).

## Environment variables

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
* **webdriver.ie.bypassProtectionMode** - Force bypassing of IE protection mode
    * default: *false*
* **loginhook** - Full qualified classname of implemented LoginHook (optional, overrides annotation)
    * default: *ConnectedCCLoginHook*
    
## Configuration

### WebDriver
In order to specify the webdrivers which should be used, the *UiTestRunner.WebDriver* annotation has to be added to the test class.

* **@UiTestRunner.WebDriver({ExampleWebDriverFactory.class,..})**

These are the default webdriver factories that are provided by the ContentCreator UI-Testing Framework:

* **LocalChromeWebDriverFactory**
* **LocalPhantomJSWebDriverFactory**
* **LocalIEWebDriverFactory**
* **RemoteChromeWebDriverFactory**
* **RemoteFirefoxWebDriverFactory**
* **RemoteIEWebDriverFactory**

**Note**: When using the **LocalIEWebDriverFactory** you might have to disable the Protection Mode of the IE. In order to do that either adjust your system settings or force a bypass of the protection mode by using the **webdriver.ie.bypassProtectionMode** environment variable described above. Using the forced bypassing is not recommended though, because it can reduce the stability of your code.

If you want to use your own webdriver factory just extend the *WebDriverFactory* class and implement the factory method.

### LoginHook

Whenever you need an alternative login method you can specify a loginhook via the **@UiTestRunner.UseLoginHook()** annotation or the environment variable **loginhook**. There are two built-in login hooks which you can use:

* *ConnectedCCLoginHook* - A login hook that uses a FirstSpirit server connection
* *SimplyCCLoginHook* - A login hook that uses the ContentCreator login web-application for authentification 

When using the **ConnectedCCLoginHook** make sure to let your test classes extend the **AbstractUiTest**.
When using the **SimplyCCLoginHook** make sure to let your test classes extend the **AbstractSimplyUiTest**.

### Annotations

Additionally test classes and methods can be parameterized with the following annotations.

* **@UiTestRunner.UseLoginHook(ExampleLoginHook.class)** - Specifies which LoginHook implementation will be used (optional)
* **@BrowserLocale("en")** - Specifies the locale to use within the test method/class (overwrites the environment variable)
* **@ClassPattern("de.espirit.firstspirit.webedit.\*.UiTest\*")** - Defines which UI tests should be executed, by specifying a classname pattern
* **@Classes(value = {Test1.class, Test2.class})** - Defines which UI tests should be executed, by specifying concrete classes

## Example Test Class

This is an example test class that uses the chrome driver and search functionality of the ContentCreator (using the default login hook ConnectedCCLoginHook).
```java
@UiTestRunner.WebDriver({LocalChromeWebDriverFactory.class})
public class ExampleTest extends AbstractUiTest {
    private static final Logger LOGGER = Logger.getLogger(ExampleTest.class);

    @Test
    public void search() {
        System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
        ExampleTest.LOGGER.info("Test 'search for solar' started");
        this.cc().menu().search("solar");
        Assert.assertTrue("search-results expected", this.cc().reports().search().getResultCount() > 0, this.cc().driver());

        ExampleTest.LOGGER.info("Test 'search for solar' successful");
    }
}
```