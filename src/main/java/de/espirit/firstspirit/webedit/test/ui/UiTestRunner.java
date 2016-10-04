package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.common.io.FileUtilities;
import de.espirit.firstspirit.access.AdminService;
import de.espirit.firstspirit.access.Connection;
import de.espirit.firstspirit.access.ConnectionManager;
import de.espirit.firstspirit.access.User;
import de.espirit.firstspirit.access.admin.ProjectStorage;
import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.agency.ClientUrlAgent;
import de.espirit.firstspirit.common.FilenameCleaner;
import de.espirit.firstspirit.io.ServerConnection;
import de.espirit.firstspirit.manager.RepositoryManager;
import de.espirit.firstspirit.manager.SessionManager;
import de.espirit.firstspirit.server.sessionmanagement.Session;
import de.espirit.firstspirit.server.usermanagement.UserImpl;
import de.espirit.firstspirit.storage.Revision;
import de.espirit.firstspirit.storage.RevisionImpl;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.env;

/**
 * JUnit4 {@link Runner} for WebEdit {@link AbstractUiTest UI tests}. A test class can be parametrized with
 * {@link ClassPattern ClassPattern}, concrete {@link Classes Classes} and/or different
 * {@link WebDriver browsers}.
 * <p/>
 * Single test: <pre>
 * {@literal @}RunWith(UiTestRunner.class)                                 <i>// not necessary, {@link AbstractUiTest} is already annotated</i>
 * {@literal @}UiTestRunner.{@link ClassPattern Classes}({MyUiTest.class})                      <i>// not necessary, default value is the annotated class</i>
 * {@literal @}UiTestRunner.{@link WebDriver WebDriver}({@link LocalChromeWebDriverFactory}.class)   <i>// not necessary, default value is the locally installed chrome</i>
 * public class MyUiTest extends {@link AbstractUiTest} {
 *   ...
 * }
 * </pre>
 * <p>
 * Multiple tests: <pre>
 * {@literal @}RunWith(UiTestRunner.class)
 * {@literal @}UiTestRunner.{@link ClassPattern ClassPattern}("UiTest*")
 * {@literal @}UiTestRunner.{@link WebDriver WebDriver}({@link RemoteChromeWebDriverFactory}.class, {@link RemoteFirefoxWebDriverFactory}.class})
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

    //--- -D parameter names ---//
    private static final String PARAM_PROJECT = "project";
    private static final String PARAM_HOST = "host";
    private static final String PARAM_PORT = "port";
    private static final String PARAM_USER = "user";
    private static final String PARAM_PASSWORD = "password";

    //--- default values ---//
    private static final String DEFAULT_PROJECT_NAME = "Test";
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "8000";
    private static final String DEFAULT_USERNAME = "Admin";
    private static final String DEFAULT_PASSWORD = "Admin";
    private static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();

    private static final Logger LOGGER = Logger.getLogger(UiTestRunner.class);

    private final Class<?> _parentClass;

    private FS _fs;

    /**
     * The annotation defines which UI tests should be executed, by specifying a classname pattern.<br/>
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
     * This annotation defines which browsers should be used to execute the specified ui tests, by specifying
     * a set of {@link WebDriverFactory browser} factories.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface WebDriver {
        Class<? extends WebDriverFactory>[] value();
    }

    /**
     * This annotation defines which locale should be used to execute the specified ui tests, by specifying
     * the language code of a locale.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface BrowserLocale {
        @NotNull String value();
    }

    @SuppressWarnings("UnusedDeclaration")
    public UiTestRunner(final Class<?> parentClass) throws InitializationError {
        super(parentClass);
        _parentClass = parentClass;
    }

    private static Class<?>[] getTestClasses(final Class<?> testClass) {
        final ClassPattern allClassPattern = testClass.getAnnotation(ClassPattern.class);
        if (allClassPattern != null) {
            final File base = new File("firstspirit/webedit/src/test");
            String value = allClassPattern.value();
            value = value.replaceAll("\\.", "\\.");
            value = value.replaceAll("\\*", ".*");
            final Pattern pattern = Pattern.compile('^' + value + '$');
            final List<Class<?>> collection = collectUiTests(base, pattern);
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
        return collectUiTests(base, base, pattern);
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
                    result.addAll(collectUiTests(base, child, pattern));
                } else if (pattern.matcher(className).find()) {
                    try {
                        final Class<?> type = UiTestRunner.class.getClassLoader().loadClass(className);
                        if (AbstractUiTest.class.isAssignableFrom(type) && (type.getModifiers() & Modifier.ABSTRACT) == 0) {
                            result.add(type);
                        }
                    } catch (final ClassNotFoundException ignored) {
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

    //--- ParentRunner ---//

    @Override
    protected List<BrowserRunner> getChildren() {
        try {
            final Class<?>[] testClasses = getTestClasses(_parentClass);
            final List<BrowserRunner> browserRunner = new LinkedList<BrowserRunner>();
            for (final WebDriverFactory browser : getBrowsers(_parentClass)) {
                browserRunner.add(new BrowserRunner(browser, testClasses));
            }
            return browserRunner;
        } catch (final InitializationError error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public Description getDescription() {
        final Description description = Description.createSuiteDescription(_parentClass);
        for (final BrowserRunner browser : getChildren()) {
            description.addChild(describeChild(browser));
        }
        return description;
    }

    @Override
    protected Description describeChild(final BrowserRunner runner) {
        return runner.getDescription();
    }

    @Override
    protected void runChild(final BrowserRunner runner, final RunNotifier runNotifier) {
        try {
            setupFS();
            runner.run(runNotifier);
        } finally {
            tearDownFS();
        }
    }

    //--- private methods ---//

    /**
     * Establishes a connection to a FirstSpirit server.
     */
    private void setupFS() {
        final String host = env(PARAM_HOST, DEFAULT_HOST);
        final String port = env(PARAM_PORT, DEFAULT_PORT);
        final String username = env(PARAM_USER, DEFAULT_USERNAME);
        final String password = env(PARAM_PASSWORD, DEFAULT_PASSWORD);
        try {
            final ServerConnection connection = (ServerConnection) ConnectionManager.getConnection(host, Integer.parseInt(port), ConnectionManager.HTTP_MODE, username, password);
            connection.connect();
            _fs = new FSImpl(connection);
        } catch (final Exception e) {
            throw new RuntimeException("connecting FirstSpirit server failed (" + host + ':' + port + ") !", e);
        }
    }

    /**
     * Closes the FirstSpirit connection.
     */
    private void tearDownFS() {
        try {
            if (_fs != null) {
                closeBoundProjectSessions(_fs.connection().getSessionId());
                _fs.connection().disconnect();
            }
        } catch (final IOException e) {
            throw new RuntimeException("disconnecting FirstSpirit server failed!");
        }

    }

    /**
     * Closes all other sessions that are bound to a project.
     *
     * @param mySessionId session which provides the bound project.
     */
    private void closeBoundProjectSessions(final long mySessionId) {
        final SessionManager sessionMgr = _fs.connection().getManager(SessionManager.class);
        final Session mySession = sessionMgr.getSession(mySessionId);
        if (mySession != null) {
            for (final Session session : sessionMgr.getSessions()) {
                if (session.isBoundToProject() && session.getID() != mySessionId) {
                    LOGGER.info("closes other session, id: " + session.getID());
                    closeSession(session);
                }
            }
        }
    }

    /**
     * Safely closes the given sesion.
     *
     * @param session to close.
     */
    private void closeSession(final Session session) {
        try {
            _fs.connection().getManager(SessionManager.class).logout(session.getID());
        } catch (final Exception e) {
           LOGGER.debug("Closing session failed: " + e);
        }
    }

    /**
     * JUnit4 {@link Runner} for WebEdit UI tests that run inside a single browser instance.
     * <p/>
     * {@link #withBeforeClasses(Statement) Before} and {@link #withAfterClasses(Statement) after} a test class the
     * browser will be {@link #setUpBrowser() opened} and {@link #tearDownBrowser() closed}. For every test
     * method the {@link SingleUiTestRunner SingleUiTestRunner} is used.
     */
    public class BrowserRunner extends ParentRunner<UiTestRunner.BrowserRunner.SingleUiTestRunner> {

        private final WebDriverFactory _browser;
        private final Class<?>[] _testClasses;

        private CC _CC;

        private BrowserRunner(final WebDriverFactory browser, final Class<?>[] testClasses) throws InitializationError {
            super(browser.getClass());
            _browser = browser;
            _testClasses = testClasses;
        }

        @Override
        public void run(final RunNotifier notifier) {
            try {
                setUpBrowser();
                super.run(notifier);
            } finally {
                tearDownBrowser();
            }
        }

        /**
         * Creates the specified {@link org.openqa.selenium.WebDriver WebDriver} instance.
         */
        private void setUpBrowser() {
            try {
                final String projectNameOrId = env(PARAM_PROJECT, DEFAULT_PROJECT_NAME);
                final ProjectStorage prjStorage = _fs.connection().getService(AdminService.class).getProjectStorage();
                Project project;
                try {
                    project = prjStorage.getProject(Long.parseLong(projectNameOrId));
                } catch (final Exception e) {
                    project = prjStorage.getProject(projectNameOrId);
                }
                if (project == null) {
                    throw new IllegalStateException("couldn't find project '" + projectNameOrId + "' !");
                }

                final RemoteWebDriver webDriver = _browser.createWebDriver();
                webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
                webDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
                webDriver.manage().window().setSize(new Dimension(1200, 800));

                final String url = _fs.connection().getBroker().requireSpecialist(ClientUrlAgent.TYPE).getBuilder(ClientUrlAgent.ClientType.WEBEDIT).project(project).createUrl();

                disableTourHints(_fs.connection());

                _CC = new CCImpl(project, webDriver, url, _fs.connection().createTicket());
            } catch (final IOException e) {
                throw new RuntimeException("IO error occurred!", e);
            }
        }

        /**
         * Disables hints and tour for the user of the given {@code connection} because they overlay the views.
         *
         * @param connection for which tour/hints should be disabled.
         */
        public void disableTourHints(@NotNull final Connection connection) {
            final User user = connection.getUser();
            final Map<String, String> bindings = user.getUserBindings();
            bindings.put(UserImpl.CC_TOUR_DISABLED, String.valueOf(true));
            bindings.put(UserImpl.CC_HINTS_DISABLED, String.valueOf(true));
            user.setUserBindings(bindings);
        }

        /**
         * {@link org.openqa.selenium.WebDriver#quit() Quits} the {@code WebDriver} instance.
         */
        private void tearDownBrowser() {
            if (_CC != null) {
                _CC.logout();
                _CC.driver().quit();
            }
        }

        @Override
        protected List<SingleUiTestRunner> getChildren() {
            final List<SingleUiTestRunner> testRunner = new LinkedList<SingleUiTestRunner>();
            for (final Class<?> testClass : _testClasses) {
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
            final Description description = Description.createSuiteDescription(_browser.getClass());
            for (final SingleUiTestRunner runner : getChildren()) {
                description.addChild(describeChild(runner));
            }
            return description;
        }

        @Override
        protected Description describeChild(final SingleUiTestRunner runner) {
            return runner.getDescription();
        }

        @Override
        protected void runChild(final SingleUiTestRunner runner, final RunNotifier runNotifier) {
            runner.run(runNotifier);
        }

        /**
         * JUnit4 {@link Runner} for a single UI test method. Before every test method the browser will be refreshed with the
         * initial url.
         */
        public class SingleUiTestRunner extends BlockJUnit4ClassRunner {

            private final Class<?> _testClass;

            private SingleUiTestRunner(final Class<?> testClass) throws InitializationError {
                super(testClass);
                _testClass = testClass;
            }

            @Override
            protected Object createTest() throws Exception {
                final Object test = super.createTest();
                if (test instanceof AbstractUiTest) {
                    ((AbstractUiTest) test).setFS(_fs);
                    ((AbstractUiTest) test).setWE(_CC);
                }
                return test;
            }

            /**
             * Reloads the browser url, saves a screenshot if an exception occurs during the test
             * and restores original revision after the execution is finished.
             */
            @Override
            protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
                final Statement s = super.methodInvoker(method, test);
                return new Statement() {
                    @Override
                    public void evaluate() throws Throwable {
                        final long projectId = _CC.project().getId();
                        Revision oldRevision = null;
                        try {
                            oldRevision = _fs.connection().getManager(RepositoryManager.class).getLatestRevision(projectId);    // after the test restore this revision
                            String locale = DEFAULT_LANGUAGE;
                            BrowserLocale annotation = method.getMethod().getAnnotation(BrowserLocale.class);
                            if (annotation == null) {
                                annotation = _testClass.getAnnotation(BrowserLocale.class);
                            }
                            if (annotation != null) {
                                locale = annotation.value();
                            }
                            String url = _CC.driver().getCurrentUrl();
                            if (url.contains("&locale=")) {
                                url = url.replaceAll("&locale=\\w+", "&locale=" + locale);
                            } else {
                                url += "&locale=" + locale;
                            }
                            _CC.driver().navigate().to(url);
                            s.evaluate();                                                                                       // execute test method
                        } catch (final Throwable throwable) {
                            final File screenshot = ((RemoteWebDriver) _CC.driver()).getScreenshotAs(OutputType.FILE);
                            FileUtilities.move(screenshot, new File("FAIL-" + System.currentTimeMillis() + '-' + FilenameCleaner.makeCleanWithoutCaseChange(throwable.getClass().getSimpleName() + '-' + throwable.getMessage()) + ".png"));
                            throw throwable;
                        } finally {
                            final RevisionImpl currentRevision = _fs.connection().getManager(RepositoryManager.class).getLatestRevision(projectId);// after the test restore this revision
                            if (oldRevision != null && !oldRevision.equals(currentRevision)) {
//								_fs.connection().getManager(ArchiveManager.class).revertProject(projectId, oldRevision.getId(), false); // discard every change, the test made
                            }
                        }
                    }
                };
            }

            @Override
            protected String testName(final FrameworkMethod method) {
                return method.getName() + " (" + _browser.getName() + ')';
            }

            @Override
            public Description getDescription() {
                final Description description = Description.createSuiteDescription(_testClass);
                for (final FrameworkMethod method : getChildren()) {
                    description.addChild(describeChild(method));
                }
                return description;
            }

            @Override
            protected Description describeChild(final FrameworkMethod method) {
                return Description.createTestDescription(_testClass, testName(method), method.getAnnotations());
            }
        }
    }

}
