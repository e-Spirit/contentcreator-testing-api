package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public class CCInputTextImpl implements CCInputText {

	protected final WebElement inputElement;
	private final WebDriver webDriver;
	private final WebElement webElement;


	public CCInputTextImpl(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) throws CCAPIException {
		this.webDriver = webDriver;
		this.webElement = webElement;
		this.inputElement = Utils.findItemInElement(webDriver, webElement, By.className("gwt-TextBox"));
	}


	@Override
	public String text() {
		return this.inputElement.getAttribute("value");
	}


	@Override
	public void setText(final String text) {
		this.inputElement.clear();
		this.inputElement.sendKeys(text);
	}


	@Override
	public String errorMessage() throws CCAPIException {
		return Utils.findItemInElement(this.webDriver, this.webElement, By.className("gwt-HTML")).getText();
	}


	@Override
	public String label() throws CCAPIException {
		return Utils.findItemInElement(this.webDriver, this.webElement, By.className("gwt-Label")).getText();
	}


	public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
		return ComponentUtils.hasElement(webElement, webDriver, By.className("gwt-TextBox"));
	}


	@NotNull
	@Override
	public WebElement html() {
		return this.webElement;
	}

}
