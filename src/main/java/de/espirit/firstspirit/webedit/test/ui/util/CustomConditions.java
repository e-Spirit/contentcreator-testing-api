package de.espirit.firstspirit.webedit.test.ui.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {

	public static ExpectedCondition<Boolean> waitForCC() {

		return driver -> {
			if (driver != null) {
				final boolean weApiAvailable = ((JavascriptExecutor) driver).executeScript("return typeof top.WE_API !== 'undefined'").equals(Boolean.TRUE);

				if (weApiAvailable && ((JavascriptExecutor) driver).executeScript("return typeof top.WE_API.Common.getPreviewElement() !== 'undefined'").equals(Boolean.TRUE)) {
					final WebElement previewFrame = driver.findElement(By.id("previewContent"));
					driver.switchTo().frame(previewFrame);
					final boolean previewState = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
					driver.switchTo().defaultContent();
					return previewState;
				}
			}

			return false;
		};
	}


	public static ExpectedCondition<Boolean> resultChanged(final WebElement element) {
		return d -> !"Results: 0".equals(element.getText());
	}
}
