package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.common.base.Logging;
import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.client.EditorIdentifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
* Implementation of the {@link WE WebEdit-UI-adapter} and all it's depending interfaces.
*/
class WEImpl implements WE {

	/** After processing idle time for a lot of HTTP operations. */
	private static final int WAIT = 250;

	private final org.openqa.selenium.WebDriver _driver;
	private final Project _project;


	WEImpl(final Project project, final org.openqa.selenium.WebDriver driver, final String url, final String ssoTicket) {
		this(project, driver, url + "&login.ticket=" + ssoTicket);
	}


	WEImpl(final Project project, final org.openqa.selenium.WebDriver driver, final String webEditUrl) {
		_project = project;
		_driver = driver;
		_driver.get(webEditUrl);
	}


	@NotNull
	@Override
	public WebElement html() {
		return find(By.tagName("html"));
	}


	@Override
	public Preview preview() {
		final WebElement body = find(By.tagName("body"));
		final WebElement iframe = find(By.id("previewContent"));
		return new PreviewImpl(body, iframe);
	}


	@Override
	public MenuBar menu() {
		return new MenuBarImpl();
	}


	@Override
	public Reports reports() {
		return new ReportsImpl();
	}


	@Override
	public void reload() {
		driver().navigate().refresh();
	}


	@Override
	public org.openqa.selenium.WebDriver driver() {
		return _driver;
	}


	@Override
	public Project project() {
		return _project;
	}


	@Override
	public void logout() {
		try {
			((RemoteWebDriver) driver()).executeScript("location.href='logout.jsp';");
		} catch (final Exception e) {
			Logging.logWarning("exception during logout", e, WEImpl.class);
		}
	}


	@NotNull
	private WebElement find(@NotNull final By by) {
		return find(null, by);
	}


	@NotNull
	private WebElement find(@Nullable final SearchContext webElement, @NotNull final By by) {
		idle();
		return webElement != null ? webElement.findElement(by) : _driver.findElement(by);
	}


	private class ReportsImpl implements Reports {

		@NotNull
		@Override
		public SearchReport search() {
			return new SearchReportImpl();
		}

		@NotNull
		@Override
		public Report bookmarks() {
			return custom(-5);
		}


		@NotNull
		@Override
		public Report workflows() {
			return custom(-4);
		}


		@NotNull
		@Override
		public Report history() {
			return custom(-3);
		}


		@NotNull
		@Override
		public Report relations() {
			return custom(-2);
		}


		@NotNull
		@Override
		public Report messages() {
			return custom(-1);
		}


		@NotNull
		@Override
		public Report custom(final int no) {
			return new AbstractReport() {
				@NotNull
				@Override
				public WebElement html() {
					return find(By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(" + (7 + no) + ')'));
				}


				@NotNull
				@Override
				public WebElement button() {
					return find(By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(" + (7 + no) + ')'));
				}
			};
		}


		@NotNull
		@Override
		public WebElement html() {
			return find(By.cssSelector(".fs-sidebar"));
		}


		private abstract class AbstractReport implements Report {

			@Override
			public int getResultCount() {
				final WebElement element = find(By.className("fs-sidebar-report-status-count"));
				final String text = element.getText(); // text = "Ergebnisse: 47"
				if (!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1))) {
					return Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1));
				}
				return -1;
			}


			@Override
			public WebElement getEntry(final int pos) {
				return find(By.cssSelector("div.report-entry-container > div > div > div:nth-child(1) > div:nth-child(" + (pos + 1) + ") > div > a"));
			}


			@Override
			public void toggle() {
				button().click();
			}


			@Override
			public void reload() {
				find(By.className("fs-sidebar-report-status-refresh")).click();
			}


			/**
			 * Returns the report's button.
			 *
			 * @return report's button.
			 */
			@NotNull
			protected abstract WebElement button();
		}


		private class SearchReportImpl extends AbstractReport implements SearchReport {

			@Override
			public void setParamMyElements(final boolean onlyMyElements) {
				final WebElement webElement = find(By.className("fs-checkbox-label"));
				if (webElement.getAttribute("class").contains("fs-checkbox-checked") != onlyMyElements) {
					webElement.click();
				}
			}


			@Nullable
			@Override
			public String reportMessage() {
				final WebElement reportMsg = find(By.className("report-message"));
				return reportMsg.isDisplayed() ? reportMsg.getText() : null;
			}


			@NotNull
			@Override
			public WebElement html() {
				return find(By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(1)"));
			}


			@NotNull
			@Override
			public WebElement button() {
				return find(By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(1)"));
			}
		}

	}


	private class MenuBarImpl implements MenuBar {

		@NotNull
		@Override
		public ElementStatus getElementStatus() {
			int max = 5;
			do {
				final String cssClass = html().getAttribute("class");
				if (cssClass.contains("workflow")) {
					return ElementStatus.IN_WORKFLOW;
				}
				if (cssClass.contains("changed")) {
					return ElementStatus.CHANGED;
				}
				if (cssClass.contains("released")) {
					return ElementStatus.RELEASED;
				}
				if (cssClass.contains("deleted")) {
					return ElementStatus.DELETED;
				}
				if (cssClass.contains("archived")) {
					return ElementStatus.ARCHIVED;
				}
				idle();
			} while (--max > 0);
			return ElementStatus.UNKNOWN;
		}


		@Override
		public void search(final String query) {
			final WebElement element = find(By.className("fs-searchtextbox-field"));
			element.click();
			element.sendKeys(query + '\n');
		}


		@NotNull
		@Override
		public WebElement html() {
			return find(By.className("fs-toolbar-state"));
		}
	}


	private class PreviewImpl implements Preview {

		@NotNull private final WebElement _body;
		@NotNull private final WebElement _iframe;


		private PreviewImpl(@NotNull final WebElement body, @NotNull final WebElement iframe) {
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

			if (Logging.isDebugEnabled(WEImpl.class)) Logging.logDebug("set url: " + url, WEImpl.class);

			final JavascriptExecutor executor = (JavascriptExecutor) driver();
			executor.executeScript("arguments[0].setAttribute('src', '" + url + "')", _iframe);
			idle();

			// check title for a 404
			final String title = driver().getTitle();
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
		public Collection<Action> actionsOf(@NotNull final EditorIdentifier identifier) {
			try {
				// we have to select the iframe, first
				driver().switchTo().frame(html());

				// move the mouse to the given store-element to load the actions
				final WebElement element = find(By.cssSelector("[data-fs-id=\"" + identifier.getId() + "\"]")); // eyJpZCI6MTA5NTcxOTgsInN0b3JlIjoiUEFHRVNUT1JFIn0=
				new Actions(driver()).moveToElement(element, 10, 10).build().perform();

				idle();

				// find toolbar and actions inside
				final WebElement toolbar = findToolbar();
				return toolbar != null ? findActions(toolbar) : null;
			} catch (final NoSuchElementException e) {
				return null;
			} finally {
				// back to outer document
				driver().switchTo().defaultContent();
			}
		}


		@NotNull
		private List<Action> findActions(final WebElement toolbar) {
			final List<WebElement> actionIcons = toolbar.findElements(By.tagName("span"));
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
		private WebElement findToolbar() {
			int i = 5;
			do {
				final List<WebElement> elements = driver().findElements(By.cssSelector(".fs-element-toolbar-actions"));
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
					// we have to select the iframe, first
					driver().switchTo().frame(PreviewImpl.this.html());
					_action.click();
				} finally {
					// back to outer document
					driver().switchTo().defaultContent();
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


	/**
	 * Sleeps {@link #WAIT} milliseconds.
	 */
	private void idle() {
		try {
			Thread.sleep(WAIT); // let it load
		} catch (final InterruptedException e) {
			throw new RuntimeException("waiting interrupted!", e);
		}
	}

}
