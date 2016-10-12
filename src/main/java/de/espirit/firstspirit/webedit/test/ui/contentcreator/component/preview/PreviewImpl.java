package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview;

import de.espirit.firstspirit.client.EditorIdentifier;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.idle;

public class PreviewImpl implements Preview {
    private static final Logger LOGGER = Logger.getLogger(PreviewImpl.class);

    @NotNull
    private WebDriver _webDriver;

    @NotNull
    private final WebElement _body;

    @NotNull
    private final WebElement _iframe;

    public PreviewImpl(@NotNull final WebDriver webDriver, @NotNull final WebElement body, @NotNull final WebElement iframe) {
        this._webDriver = webDriver;
        _body = body;
        _iframe = iframe;
    }

    @NotNull
    @Override
    public WebElement html() {
        return _iframe;
    }

    @Override
    public String getUrl() {
        return _iframe.getAttribute("src");
    }

    @Override
    public void setUrl(String url) throws IOException {
        final String oldUrl = getUrl();
        final int oldPos = oldUrl.indexOf("/preview/");
        final int newPos = url.indexOf("/preview/");
        if (oldPos > 0 && newPos > 0) {
            url = oldUrl.substring(0, oldPos) + url.substring(newPos); // use sub-session of existing url
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("set url: " + url);

        final JavascriptExecutor executor = (JavascriptExecutor) _webDriver;
        executor.executeScript("arguments[0].setAttribute('src', '" + url + "')", _iframe);
        idle();

        // check title for a 404
        final String title = _webDriver.getTitle();
        if (title.contains("404")) {
            throw new IOException("URL not found: " + title + " (" + url + ')');
        }
    }

    @Override
    public void reload() {
        _body.sendKeys(Keys.CONTROL + "r");
        idle();
    }

    @Override
    @Nullable
    public Collection<Action> actionsOf(@NotNull final EditorIdentifier identifier) throws CCAPIException {
        try {
            // cc have to select the iframe, first
            _webDriver.switchTo().frame(html());

            // move the mouse to the given store-element to load the actions
            final WebElement element = Utils.findElement(_webDriver, By.cssSelector("[data-fs-id=\"" + identifier.getId() + "\"]"));
            //final WebElement element = find(_webDriver, By.cssSelector("[data-fs-id=\"" + identifier.getId() + "\"]")); // eyJpZCI6MTA5NTcxOTgsInN0b3JlIjoiUEFHRVNUT1JFIn0=
            new Actions(_webDriver).moveToElement(element, 10, 10).build().perform();

            idle();

            // find toolbar and actions inside
            final WebElement toolbar = findToolbar();
            return toolbar != null ? findActions(toolbar) : null;
        } catch (final NoSuchElementException e) {
            return null;
        } finally {
            // back to outer document
            _webDriver.switchTo().defaultContent();
        }
    }

    @NotNull
    private List<Action> findActions(final WebElement toolbar) throws CCAPIException {
        final List<WebElement> actionIcons = Utils.findMultipleItemsInElement(_webDriver, toolbar, By.tagName("span"));
        //final List<WebElement> actionIcons = toolbar.findElements(By.tagName("span"));
        final List<Action> actions = new ArrayList<>(actionIcons.size());
        for (final WebElement actionIcon : actionIcons) {
            final String tooltip = actionIcon.getAttribute("fs-tooltip");
            if (tooltip != null && !tooltip.isEmpty()) {
                // only return actions with tooltip
                actions.add(new ActionImpl(actionIcon, tooltip));
            }
        }
        return actions;
    }

    @Nullable
    private WebElement findToolbar() throws CCAPIException {
        int i = 5;
        do {
            final List<WebElement> elements = Utils.findElements(_webDriver, By.cssSelector(".fs-element-toolbar-actions"));
            //final List<WebElement> elements = _webDriver.findElements(By.cssSelector(".fs-element-toolbar-actions"));
            for (final WebElement entry : elements) {
                if (entry.isDisplayed()) {
                    return entry;
                }
            }
            idle();
        } while (--i > 0);

        return null;
    }

    private class ActionImpl implements Action {

        private final WebElement _action;
        private final String _tooltip;

        private ActionImpl(final WebElement action, final String tooltip) {
            _action = action;
            _tooltip = tooltip;
        }

        @Override
        public void execute() {
            try {
                // cc have to select the iframe, first
                _webDriver.switchTo().frame(PreviewImpl.this.html());
                _action.click();
            } finally {
                // back to outer document
                _webDriver.switchTo().defaultContent();
            }
        }

        @Override
        public String tooltip() {
            return _tooltip;
        }

        @NotNull
        @Override
        public WebElement html() {
            return _action;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[tooltip=" + _tooltip + ']';
        }
    }
}