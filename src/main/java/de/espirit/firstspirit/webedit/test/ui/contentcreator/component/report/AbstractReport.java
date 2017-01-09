package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.CustomConditions;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

public abstract class AbstractReport implements Report {

  protected final WebDriver webDriver;
  protected WebElement reportButton;


  public AbstractReport(@NotNull final WebDriver webDriver,
      @NotNull final WebElement reportButton) {
    this.webDriver = webDriver;
    this.reportButton = reportButton;
  }


  @Override
  public int getResultCount() throws CCAPIException {
    if (this.isVisible()) {
      final WebElement element = Utils.find(this.webDriver, ExpectedConditions
          .presenceOfElementLocated(By.className("fs-sidebar-report-status-count")));
      new WebDriverWait(this.webDriver, Constants.WEBDRIVER_WAIT)
          .until(CustomConditions.resultChanged(element));
      final String text = element.getText();
      if (!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1))) {
        return Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1));
      }
    }
    return -1;
  }


  @Override
  public WebElement getEntry(final int pos) throws CCAPIException {
    if (this.isVisible()) {
      return Utils.findElement(this.webDriver,
          By.cssSelector(
              "div.report-entry-container > div > div > div:nth-child(1) > div:nth-child("
                  + (pos + 1) + ") > div"));
    }

    return null;
  }


  @Override
  public void toggle() {
    final WebElement button =
        Utils.find(this.webDriver, ExpectedConditions.elementToBeClickable(this.reportButton));
    button.click();
  }


  @Override
  public void reload() throws CCAPIException {
    if (this.isVisible()) {
      Utils.findElement(this.webDriver, By.className("fs-sidebar-report-status-refresh")).click();
    }
  }


  @Override
  public List<CCInputComponent> parameters() throws CCAPIException {
    if (!this.isVisible()) {
      this.toggle();
    }

    final WebElement reportContent = Utils.find(this.webDriver,
        ExpectedConditions.visibilityOfElementLocated(By.className("fs-sidebar-content")));
    final WebElement parametersDiv = Utils.findItemInElement(this.webDriver, reportContent,
        By.className("fs-sidebar-report-parameter"));
    final List<WebElement> elements = Utils.findMultipleItemsInElement(this.webDriver,
        parametersDiv, By.cssSelector("div > table > tbody > tr"));
    final List<CCInputComponent> result = new ArrayList<>();

    for (final WebElement element : elements) {
      final CCInputComponent ccInputComponent =
          ComponentUtils.matchParameter(element, this.webDriver);

      if (ccInputComponent != null) {
        result.add(ccInputComponent);
      }
    }

    return result;
  }


  @NotNull
  @Override
  public WebElement html() throws CCAPIException {
    if (!this.isVisible()) {
      this.toggle();
    }

    return Utils.findElement(this.webDriver, By.className("fs-sidebar-content"));
  }


  /**
   * Returns the report's reportButton.
   *
   * @return report's reportButton.
   */
  @NotNull
  public WebElement button() {
    return this.reportButton;
  }


  public boolean isVisible() {
    return this.reportButton.getAttribute("class").contains("pressed");
  }
}
