package de.espirit.firstspirit.webedit.test.ui;


import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.loginhook.ConnectedCCLoginHook;
import de.espirit.firstspirit.webedit.test.ui.loginhook.LoginHook;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.LocalChromeWebDriverFactory;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.RemoteChromeWebDriverFactory;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.RemoteFirefoxWebDriverFactory;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.WebDriverFactory;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Dimension;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * JUnit4 {@link Runner} for WebEdit {@link AbstractUiTest UI tests}. A test class can be
 * parametrized with {@link ClassPattern ClassPattern}, concrete {@link Classes Classes} and/or
 * different {@link WebDriver browsers}.
 * <p>
 * Single test:
 * <p>
 * <pre>
 * {@literal @}RunWith(UiTestRunner.class)                                 <i>// not necessary, {@link AbstractUiTest} is already annotated</i>
 * {@literal @}UiTestRunner.{@link ClassPattern Classes}({MyUiTest.class})                      <i>// not necessary, default value is the annotated class</i>
 * {@literal @}UiTestRunner.{@link WebDriver WebDriver}({@link LocalChromeWebDriverFactory}.class)   <i>// not necessary, default value is the locally installed chrome</i>
 * public class MyUiTest extends {@link AbstractUiTest} {
 *   ...
 * }
 * </pre>
 * <p>
 * Multiple tests:
 * <p>
 * <pre>
 * {@literal @}RunWith(UiTestRunner.class)
 * {@literal @}UiTestRunner.{@link ClassPattern ClassPattern}("UiTest*")
 * {@literal @}UiTestRunner.{@link WebDriver WebDriver}({{@link RemoteChromeWebDriverFactory}.class, {@link RemoteFirefoxWebDriverFactory}.class})
 * public class AllUiTests extends {@link AbstractUiTest} {
 *   // could be empty
 * }
 * </pre>
 * <p>
 * System {@link System#getProperties() properties}:
 * <ul>
 * <li>{@code project} - project name (default: {@code Mithras Energy}).</li>
 * <li>{@code host} - host name (default: {@code localhost}).</li>
 * <li>{@code port} - socket port (default: {@code 8081}).</li>
 * <li>{@code user} - user name (default: {@code Admin}).</li>
 * <li>{@code password} - user password (default: {@code Admin}).</li>
 * </ul>
 *
 * @see ClassPattern ClassPattern
 * @see Classes Classes
 * @see WebDriver WebDriver
 */
public class UiTestRunner extends ParentRunner<UiTestRunner.BrowserRunner> {

    // --- -D parameter names ---//
    private static final String PARAM_PROJECT = "project";
    private static final String PARAM_HOST = "host";
    private static final String PARAM_PORT = "port";
    private static final String PARAM_USER = "user";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_LOGINHOOK_CLASSNAME = "loginhook";

    // --- default values ---//
    private static final String DEFAULT_PROJECT_NAME = "Mithras Energy";
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "8000";
    private static final String DEFAULT_USERNAME = "Admin";
    private static final String DEFAULT_PASSWORD = "Admin";
    private static final String DEFAULT_LOGINHOOK_CLASSNAME = "de.espirit.firstspirit.webedit.test.ui.loginhook.ConnectedCCLoginHook";

    private static final Logger LOGGER = Logger.getLogger(UiTestRunner.class);

    private final Class<?> _parentClass;

    private LoginHook _loginHook;
    private FS _fs;

    /**
     * The annotation defines which UI tests should be executed, by specifying a classname
     * pattern.<br>
     * For example: de.espirit.firstspirit.webedit.*.UiTest*
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface ClassPattern {

        String value();
    }

    /**
     * The annotation defines which UI tests should be executed, by specifying concrete classes.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface Classes {

        Class<?>[] value();
    }

    /**
     * This annotation defines which browsers should be used to execute the specified ui tests, by
     * specifying a set of {@link WebDriverFactory browser} factories.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface WebDriver {

        Class<? extends WebDriverFactory>[] value();
    }

    /**
     * This annotation defines which locale should be used to execute the specified ui tests, by
     * specifying the language code of a locale.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface BrowserLocale {

        @NotNull String value();
    }


    /**
     * The annotation defines which {@link LoginHook} should be used for connection to FirstSpirit, by specifying concrete classes.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Target(ElementType.TYPE)
    public @interface UseLoginHook {

        Class<? extends LoginHook> value() default ConnectedCCLoginHook.class;
    }

    @SuppressWarnings("UnusedDeclaration")
    public UiTestRunner(final Class<?> parentClass) throws InitializationError {
        super(parentClass);
        this._parentClass = parentClass;
    }

    private static Class<?>[] getTestClasses(final Class<?> testClass) {
        final ClassPattern allClassPattern = testClass.getAnnotation(ClassPattern.class);
        if (allClassPattern != null) {
            final File base = new File("firstspirit/webedit/src/test");
            String value = allClassPattern.value();
            value = value.replaceAll("\\.", "\\.");
            value = value.replaceAll("\\*", ".*");
            final Pattern pattern = Pattern.compile('^' + value + '$');
            final List<Class<?>> collection = UiTestRunner.collectUiTests(base, pattern);
            return collection.toArray(new Class<?>[collection.size()]);
        }

        final Classes allClasses = testClass.getAnnotation(Classes.class);
        if (allClasses != null) {
            return allClasses.value();
        }

        return new Class[]{testClass};
    }

    @NotNull
    private static List<Class<?>> collectUiTests(@NotNull final File base, @NotNull final Pattern pattern) {
        return UiTestRunner.collectUiTests(base, base, pattern);
    }

    @NotNull
    private static List<Class<?>> collectUiTests(@NotNull final File base, @NotNull final File file, @NotNull final Pattern pattern) {
        final List<Class<?>> result = new ArrayList<>();
        final File[] children = file.listFiles();
        if (children != null) {
            for (final File child : children) {
                final String path = child.getPath();
                String className = path.substring(base.getPath().length() + 1);
                className = className.replaceAll("[\\\\/]", ".");
                className = className.replaceAll("\\.java$", "");
                if (child.isDirectory()) {
                    result.addAll(UiTestRunner.collectUiTests(base, child, pattern));
                } else if (pattern.matcher(className).find()) {
                    try {
                        final Class<?> type = UiTestRunner.class.getClassLoader().loadClass(className);
                        if (AbstractUiTest.class.isAssignableFrom(type) && ((type.getModifiers() & Modifier.ABSTRACT) == 0)) {
                            result.add(type);
                        }
                    } catch (final ClassNotFoundException ignored) {
                        // this can be ignored because we know that is class is present.
                    }
                }
            }
        }
        return result;
    }

    private static WebDriverFactory[] getBrowsers(final Class<?> testClass) {
        final WebDriver browser = testClass.getAnnotation(WebDriver.class);
        if (browser != null) {
            final Class<? extends WebDriverFactory>[] value = browser.value();
            final WebDriverFactory[] factories = new WebDriverFactory[value.length];
            for (int i = 0; i < factories.length; i++) {
                try {
                    factories[i] = value[i].newInstance();
                } catch (final Exception e) {
                    throw new RuntimeException("couldn't instantiate WebDriverFactory!", e);
                }
            }
            return factories;
        }

        return new WebDriverFactory[]{new LocalChromeWebDriverFactory()};
    }

    // --- ParentRunner ---//

    @Override
    protected List<BrowserRunner> getChildren() {
        try {
            final Class<?>[] testClasses = UiTestRunner.getTestClasses(this._parentClass);
            final List<BrowserRunner> browserRunner = new LinkedList<>();
            for (final WebDriverFactory browser : UiTestRunner.getBrowsers(this._parentClass)) {
                browserRunner.add(new BrowserRunner(browser, testClasses));
            }
            return browserRunner;
        } catch (final InitializationError error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public Description getDescription() {
        final Description description = Description.createSuiteDescription(this._parentClass);
        for (final BrowserRunner browser : this.getChildren()) {
            description.addChild(this.describeChild(browser));
        }
        return description;
    }

    @Override
    protected Description describeChild(final BrowserRunner runner) {
        return runner.getDescription();
    }


    @Override
    protected void runChild(final BrowserRunner runner, final RunNotifier runNotifier) {
        this.getLoginHook(runner);
        runner.run(runNotifier);
    }

    // --- private methods ---//


    /**
     * Create and return a LoginHook for later establishment of a connection to FirstSpirit.
     *
     * @param runner
     */
    private void getLoginHook(final BrowserRunner runner) {
        try {
            Class<?> annotatedLoginHookClass = null;
            String loginHookClassName;

            for (Class<?> testClass : runner._testClasses) {
                if (testClass.isAnnotationPresent(UseLoginHook.class)) {
                    if (annotatedLoginHookClass == null) {
                        annotatedLoginHookClass = testClass.getAnnotation(UseLoginHook.class).value();
                    } else if (!annotatedLoginHookClass.equals(testClass.getAnnotation(UseLoginHook.class).value())) {
                        UiTestRunner.LOGGER.warn("Found different LoginHooks in several test classes. Annotation will be ignored and default used.");
                        annotatedLoginHookClass = null;
                        break;
                    }
                }
            }

            if (annotatedLoginHookClass == null) {
                loginHookClassName = Utils.env(UiTestRunner.PARAM_LOGINHOOK_CLASSNAME, DEFAULT_LOGINHOOK_CLASSNAME);
                _loginHook = (LoginHook) UiTestRunner.class.getClassLoader().loadClass(loginHookClassName).newInstance();
                UiTestRunner.LOGGER.info("Use LoginHook set by environment variable: " + loginHookClassName);
            } else {
                loginHookClassName = Utils.env(UiTestRunner.PARAM_LOGINHOOK_CLASSNAME, annotatedLoginHookClass.getCanonicalName());

                if (loginHookClassName.equals(annotatedLoginHookClass.getCanonicalName())) {
                    _loginHook = (LoginHook) annotatedLoginHookClass.newInstance();
                    UiTestRunner.LOGGER.info("Use UseLoginHook set by annotation: " + loginHookClassName);
                } else {
                    UiTestRunner.LOGGER.warn("Annotated LoginHook will be overridden by environment variable.");
                    _loginHook = (LoginHook) UiTestRunner.class.getClassLoader().loadClass(loginHookClassName).newInstance();
                    UiTestRunner.LOGGER.info("Use LoginHook set by environment variable: " + loginHookClassName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate a LoginHook and connect to FirstSpirit.", e);
        }
    }

    /**
     * JUnit4 {@link Runner} for WebEdit UI tests that run inside a single browser instance.
     * <p>
     * {@link #withBeforeClasses(Statement) Before} and {@link #withAfterClasses(Statement) after} a
     * test class the browser will be {@link #setUpBrowser() opened}. For every test method the
     * {@link SingleUiTestRunner SingleUiTestRunner} is used.
     */
    public class BrowserRunner extends ParentRunner<UiTestRunner.BrowserRunner.SingleUiTestRunner> {

        private final WebDriverFactory _browser;
        private final Class<?>[] _testClasses;

        private CC _cc;
        private org.openqa.selenium.WebDriver _webDriver;

        private BrowserRunner(final WebDriverFactory browser, final Class<?>[] testClasses) throws InitializationError {
            super(browser.getClass());
            this._browser = browser;
            this._testClasses = testClasses;
        }

        @Override
        public void run(final RunNotifier notifier) {
            try {
                this.setUpBrowser();
                super.run(notifier);
            } finally {
                _loginHook.tearDownCC();
                _loginHook.tearDownFS();
                try {
                    _webDriver.quit();
                } catch (Exception e) {
                }
            }
        }

        /**
         * Creates the specified {@link org.openqa.selenium.WebDriver WebDriver} instance.
         */
        private void setUpBrowser() {
            UiTestRunner.LOGGER.info("Connection established");

            try {
                _webDriver = this._browser.createWebDriver();
            } catch (final IOException e) {
                throw new RuntimeException("IO error occurred!", e);
            }

            _webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            _webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            _webDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            try {
                _webDriver.manage().window().setSize(new Dimension(1200, 800));
            } catch (Exception e) {
                UiTestRunner.LOGGER.warn("Could not resize browser window.", e);
            }

            _fs = _loginHook.createFS(UiTestRunner.this, this);
            _cc = _loginHook.createCC(UiTestRunner.this, this);

            UiTestRunner.LOGGER.info("ContentCreator loaded");
        }


        public CC getCC() {
            return _cc;
        }

        @Override
        protected List<SingleUiTestRunner> getChildren() {
            final List<SingleUiTestRunner> testRunner = new LinkedList<>();
            for (final Class<?> testClass : this._testClasses) {
                try {
                    testRunner.add(new SingleUiTestRunner(testClass));
                } catch (final InitializationError initializationError) {
                    throw new RuntimeException("couldn't initialize test-class: " + initializationError, initializationError);
                }
            }
            return testRunner;
        }

        @Override
        public Description getDescription() {
            final Description description = Description.createSuiteDescription(this._browser.getClass());
            for (final SingleUiTestRunner runner : this.getChildren()) {
                description.addChild(this.describeChild(runner));
            }
            return description;
        }

        @Override
        protected Description describeChild(final SingleUiTestRunner runner) {
            return runner.getDescription();
        }


        public org.openqa.selenium.WebDriver getWebDriver() {
            return _webDriver;
        }

        @Override
        protected void runChild(final SingleUiTestRunner runner, final RunNotifier runNotifier) {
            runner.run(runNotifier);
        }

        /**
         * JUnit4 {@link Runner} for a single UI test method. Before every test method the browser will
         * be refreshed with the initial url.
         */
        public class SingleUiTestRunner extends BlockJUnit4ClassRunner {

            private final Class<?> _testClass;

            private SingleUiTestRunner(final Class<?> testClass) throws InitializationError {
                super(testClass);
                this._testClass = testClass;
            }

            @Override
            protected Object createTest() throws Exception {
                final Object test = super.createTest();
                if (test instanceof AbstractSimplyUiTest) {
                    ((AbstractSimplyUiTest) test).setCC(BrowserRunner.this._cc);
                }
                if (test instanceof AbstractUiTest) {
                    ((AbstractUiTest) test).setFS(UiTestRunner.this._fs);
                }
                return test;
            }

            /**
             * Reloads the browser url, saves a screenshot if an exception occurs during the test and
             * restores original revision after the execution is finished.
             */
            @Override
            protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
                final Statement s = super.methodInvoker(method, test);
                return new Statement() {
                    @Override
                    public void evaluate() throws Throwable {
                        try {
                            String locale = Constants.DEFAULT_LOCALE;
                            BrowserLocale annotation = method.getMethod().getAnnotation(BrowserLocale.class);
                            if (annotation == null) {
                                annotation = SingleUiTestRunner.this._testClass.getAnnotation(BrowserLocale.class);
                            }
                            if (annotation != null) {
                                locale = annotation.value();
                            } else if (System.getenv(Constants.PARAM_LOCALE) != null) {
                                locale = System.getenv(Constants.PARAM_LOCALE);
                            }

                            ((AbstractUiTest) test).setLocale(locale);
                            ((AbstractUiTest) test).switchProject(Utils.env(PARAM_PROJECT, DEFAULT_PROJECT_NAME));

                            s.evaluate(); // execute test method
                        } catch (final Throwable throwable) {
                            throw throwable;
                        }
                    }
                };
            }

            @Override
            protected String testName(final FrameworkMethod method) {
                return method.getName() + " (" + BrowserRunner.this._browser.getName() + ')';
            }

            @Override
            public Description getDescription() {
                final Description description = Description.createSuiteDescription(this._testClass);
                for (final FrameworkMethod method : this.getChildren()) {
                    description.addChild(this.describeChild(method));
                }
                return description;
            }

            @Override
            protected Description describeChild(final FrameworkMethod method) {
                return Description.createTestDescription(this._testClass, this.testName(method), method.getAnnotations());
            }
        }
    }
}
