package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import de.espirit.firstspirit.client.EditorIdentifier;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class PreviewImpl implements Preview {

	private static final Logger LOGGER = Logger.getLogger(PreviewImpl.class);

	@NotNull
	private final WebDriver webDriver;

	@NotNull
	private final WebElement body;

	@NotNull
	private final WebElement iframe;


	public PreviewImpl(@NotNull final WebDriver webDriver, @NotNull final WebElement body, @NotNull final WebElement iframe) {
		this.webDriver = webDriver;
		this.body = body;
		this.iframe = iframe;
	}


	@NotNull
	@Override
	public WebElement html() {
		return this.iframe;
	}


	@Override
	public String getUrl() {
		Utils.waitForCC(this.webDriver);
		return this.iframe.getAttribute("src");
	}


	@Override
	public void setUrl(String url) throws IOException {
		final String oldUrl = this.getUrl();
		final int oldPos = oldUrl.indexOf("/preview/");
		final int newPos = url.indexOf("/preview/");
		if ((oldPos > 0) && (newPos > 0)) {
			url = oldUrl.substring(0, oldPos) + url.substring(newPos); // use sub-session of existing url
		}

		if (PreviewImpl.LOGGER.isDebugEnabled()) {
			PreviewImpl.LOGGER.debug("set url: " + url);
		}

		final JavascriptExecutor executor = (JavascriptExecutor) this.webDriver;
		executor.executeScript("arguments[0].setAttribute('src', '" + url + "')", this.iframe);
		Utils.idle();

		// check title for a 404
		final String title = this.webDriver.getTitle();
		if (title.contains("404")) {
			throw new IOException("URL not found: " + title + " (" + url + ')');
		}
	}


	@Override
	public void reload() {
		this.body.sendKeys(Keys.CONTROL + "r");
		Utils.idle();
	}


	@Override
	@Nullable
	public Collection<Action> actionsOf(@NotNull final EditorIdentifier identifier) throws CCAPIException {
		try {
			// cc have to select the iframe, first
			this.webDriver.switchTo().frame(this.html());

			// move the mouse to the given store-element to load the actions
			final WebElement element = Utils.findElement(this.webDriver, By.cssSelector("[data-fs-id=\"" + identifier.getId() + "\"]"));
			new Actions(this.webDriver).moveToElement(element, 10, 10).build().perform();

			Utils.idle();

			// find toolbar and actions inside
			final WebElement toolbar = this.findToolbar();
			return toolbar != null ? this.findActions(toolbar) : null;
		} catch (final NoSuchElementException e) {
			return null;
		} finally {
			// back to outer document
			this.webDriver.switchTo().defaultContent();
		}
	}


	@NotNull
	private List<Action> findActions(final WebElement toolbar) throws CCAPIException {
		final List<WebElement> actionIcons = Utils.findMultipleItemsInElement(this.webDriver, toolbar, By.tagName("span"));
		final List<Action> actions = new ArrayList<>(actionIcons.size());
		for (final WebElement actionIcon : actionIcons) {
			final String tooltip = actionIcon.getAttribute("fs-tooltip");
			if ((tooltip != null) && !tooltip.isEmpty()) {
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
			final List<WebElement> elements = Utils.findElements(this.webDriver, By.cssSelector(".fs-element-toolbar-actions"));
			for (final WebElement entry : elements) {
				if (entry.isDisplayed()) {
					return entry;
				}
			}
			Utils.idle();
		} while (--i > 0);

		return null;
	}

	private class ActionImpl implements Action {

		private final WebElement _action;
		private final String _tooltip;


		private ActionImpl(final WebElement action, final String tooltip) {
			this._action = action;
			this._tooltip = tooltip;
		}


		@Override
		public void execute() {
			try {
				// cc have to select the iframe, first
				PreviewImpl.this.webDriver.switchTo().frame(PreviewImpl.this.html());
				this._action.click();
			} finally {
				// back to outer document
				PreviewImpl.this.webDriver.switchTo().defaultContent();
			}
		}


		@Override
		public String tooltip() {
			return this._tooltip;
		}


		@NotNull
		@Override
		public WebElement html() {
			return this._action;
		}


		@Override
		public String toString() {
			return this.getClass().getSimpleName() + "[tooltip=" + this._tooltip + ']';
		}
	}
}