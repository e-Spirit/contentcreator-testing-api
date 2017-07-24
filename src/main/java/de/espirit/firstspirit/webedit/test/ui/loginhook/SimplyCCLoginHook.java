package de.espirit.firstspirit.webedit.test.ui.loginhook;


import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.SimplyCC;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;



@SuppressWarnings("unused")
public class SimplyCCLoginHook extends AbstractLoginHook {
	
	@SuppressWarnings("static-access")
	@Override
	public CC createCC(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		super._cc = new SimplyCC(browserRunner.getWebDriver(), "http://" + this._host + ":" + this._port);
		browserRunner.getWebDriver().findElement(By.name("login.user")).sendKeys(this._username);
		browserRunner.getWebDriver().findElement(By.name("login.password")).sendKeys(this._password);
		browserRunner.getWebDriver().findElement(By.className("buttonSmallTextCell")).submit();
		waitForLoad(browserRunner.getWebDriver());
		browserRunner.getWebDriver().findElement(By.className("button_contentcreator")).click();
		waitForLoad(browserRunner.getWebDriver());
		
		boolean foundProject = false;
		
		try {
			long projectId = Long.valueOf(this._project);
			
			for (WebElement element : browserRunner.getWebDriver().findElements(By.className("report-entry"))) {
				try {
					long currentProjectId = Long.parseLong(element.getAttribute("pid"));
					
					if (currentProjectId == projectId) {
						element.click();
						foundProject = true;
						break;
					}
				} catch (NumberFormatException e) {
					continue;
				}
			}
		} catch (NumberFormatException e) {
			for (WebElement element : browserRunner.getWebDriver().findElements(By.className("report-entry-title"))) {
				if (element.getText().equals(this._project)) {
					element.click();
					foundProject = true;
					break;
				}
			}
		}
		
		if (!foundProject) {
			throw new IllegalArgumentException("Can't find project '" + this._project + "'");
		}
		
		return super._cc;
	}
	
	@Override
	public FS createFS(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		return null;
	}
	
	
	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
					}
				};
		WebDriverWait wait = new WebDriverWait(driver, 300);
		wait.until(pageLoadCondition);
	}
}