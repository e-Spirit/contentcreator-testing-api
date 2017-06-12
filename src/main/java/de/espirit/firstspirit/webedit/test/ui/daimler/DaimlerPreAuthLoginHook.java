package de.espirit.firstspirit.webedit.test.ui.daimler;


import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.SimplyCC;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.loginhook.AbstractLoginHook;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;



@SuppressWarnings("unused")
public class DaimlerPreAuthLoginHook extends AbstractLoginHook {
	
	@SuppressWarnings("static-access")
	@Override
	public CC createCC(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		String firstSpiritSrv = Utils.env("firstSpiritUrl", null);
		String siteMinderSrv = Utils.env("siteMinderUrl", null);
		
		if (firstSpiritSrv == null || firstSpiritSrv.isEmpty()) {
			throw new IllegalArgumentException("Missing environment variable: firstSpiritUrl");
		}
		
		if (siteMinderSrv == null || siteMinderSrv.isEmpty()) {
			throw new IllegalArgumentException("Missing environment variable: siteMinderUrl");
		}
		
		browserRunner.getWebDriver().get(firstSpiritSrv);
		waitForLoad(1);
		
		if (browserRunner.getWebDriver().getCurrentUrl().contains(siteMinderSrv)) {
			// FirstSpirit is not directly available -> first login at SiteMinderWebAgent
			
			// username
			browserRunner.getWebDriver().findElement(By.id("usr")).clear();
			((JavascriptExecutor) browserRunner.getWebDriver()).executeScript("document.getElementById('usr').value='" + super._username + "'");
			
			// password
			((JavascriptExecutor) browserRunner.getWebDriver()).executeScript("document.getElementById('password').value='"+super._password+"'");
			
			//clicks on login
			((JavascriptExecutor) browserRunner.getWebDriver()).executeScript("document.getElementById('login').submit()");
			
			waitForLoad(1);
		}
		
		if (browserRunner.getWebDriver().getCurrentUrl().contains(firstSpiritSrv)) {
			String locale    = Utils.env("locale", "en");
			
			if (!locale.isEmpty()) {
				super.LOGGER.info("use locale: " + locale);
				locale = "locale=" + locale + "&";
			} else {
				super.LOGGER.info("use no locale.");
			}
			
			
			super._cc = new SimplyCC(browserRunner.getWebDriver(), browserRunner.getWebDriver().getCurrentUrl());
			
			try {
				long projectId = Long.parseLong(super._project);
				super.LOGGER.info("open project with id " + projectId);
				browserRunner.getWebDriver().get(firstSpiritSrv + "/fs5webedit/?" + locale + "project=" + projectId);
			} catch (NumberFormatException e) {
				browserRunner.getWebDriver().findElement(By.className("button_contentcreator")).click();
				waitForLoad(1);
				
				for (WebElement element : browserRunner.getWebDriver().findElements(By.className("report-entry-title"))) {
					if (element.getText().equals(super._project)) {
						super.LOGGER.info("open project: " + super._project);
						element.click();
						break;
					}
				}
			}
			
			waitForLoad(5);
			
			return super._cc;
		}
		
		throw new IllegalArgumentException("Unable to open ContentCreator.");
	}
	
	@Override
	public FS createFS(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		return null;
	}
	
	
	public void waitForLoad(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}