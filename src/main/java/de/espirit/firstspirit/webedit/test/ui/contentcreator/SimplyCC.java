package de.espirit.firstspirit.webedit.test.ui.contentcreator;


import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.CCDialogs;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.CCDialogsImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu.MenuBar;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu.MenuBarImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.PreviewImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.Reports;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.ReportsImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.waitForCC;



/**
 * Implementation of the {@link CC WebEdit-UI-adapter} and all it's depending interfaces.
 */
public class SimplyCC implements CC {
	
	private static final Logger LOGGER = Logger.getLogger(SimplyCC.class);
	
	private final WebDriver webDriver;
	
	
	public SimplyCC(final WebDriver driver, final String url, final String ssoTicket) {
		this(driver, url + "&login.ticket=" + ssoTicket);
	}
	
	
	public SimplyCC(final WebDriver driver, final String webEditUrl) {
		this.webDriver = driver;
		this.webDriver.get(webEditUrl);
	}
	
	
	@Override
	public CCDialogs dialogs() {
		return new CCDialogsImpl(webDriver);
	}
	
	
	@Override
	public WebDriver driver() {
		return webDriver;
	}
	
	
	@NotNull
	@Override
	public WebElement html() throws CCAPIException {
		return Utils.findElement(webDriver, By.tagName("html"));
	}
	
	
	@Override
	public void logout() {
		try {
			// TODO unsafe logout by GUI
			List<WebElement> buttons = ((RemoteWebDriver) driver()).findElementsByClassName("fs-sidebar-buttons");
			boolean stop = false;
			
			for (WebElement button : buttons) {
				if (stop) {
					break;
				}
				
				if (button.getAttribute("class").contains("bottom")) {
					List<WebElement> divs = button.findElements(By.tagName("div"));
					
					for (WebElement div : divs) {
						if (stop) {
							break;
						}
						
						if (div.isDisplayed()) {
							try {
								div.click();
								
								WebElement dialogBox = ((RemoteWebDriver) driver()).findElement(By.className("fs-DialogBox"));
								
								if (dialogBox != null) {
									List<WebElement> innerButtons = dialogBox.findElements(By.className("fs-button-inner"));
									
									for (WebElement innerButton : innerButtons) {
										if (stop) {
											break;
										}
										
										WebElement textDiv = innerButton.findElement(By.className("fs-button-text"));
										
										if (textDiv.getText().equalsIgnoreCase("ok")) {
											textDiv.click();
											stop = true;
										}
									}
								}
							} catch (Exception e) {}
						}
					}
				}
			}
			
//			((RemoteWebDriver) driver()).executeScript("location.href='logout.jsp';");
		} catch (final Exception e) {
			LOGGER.warn("exception during logout", e);
		}
	}
	
	
	@Override
	public MenuBar menu() {
		return new MenuBarImpl(webDriver);
	}
	
	
	@Override
	public Preview preview() throws CCAPIException {
		final WebElement body   = Utils.findElement(webDriver, By.tagName("body"));
		final WebElement iframe = Utils.findElement(webDriver, By.id("previewContent"));
		return new PreviewImpl(webDriver, body, iframe);
	}
	
	
	@Override
	public long previewElementId() {
		waitForCC(webDriver);
		Long id = (Long) ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API.Common.getPreviewElement().getId()");
		
		return id;
	}
	
	
	@Override
	public void reload() {
		driver().navigate().refresh();
	}
	
	
	@Override
	public Reports reports() {
		return new ReportsImpl(webDriver);
	}
}
