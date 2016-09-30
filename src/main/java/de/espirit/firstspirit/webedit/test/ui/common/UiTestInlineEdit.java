package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.pagestore.Page;
import de.espirit.firstspirit.access.store.pagestore.Section;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.access.store.templatestore.gom.GomFormElement;
import de.espirit.firstspirit.access.store.templatestore.gom.GomText;
import de.espirit.firstspirit.client.EditorIdentifier;
import de.espirit.firstspirit.common.FileUtilities;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Date;


public class UiTestInlineEdit extends AbstractUiTest {

	@Test
	public void inlineTextEditing() throws Exception {
		final PageRef element = getStartNode();
		final String url = toPreviewUrl(element);

		// PRE-CONDITION: go to page-ref
		we().preview().setUrl(url);

		final Page page = element.getPage();
		assertNotNull("[pre-condition] no page available!", page);

		final Section<?> section = page.getChildren(Section.class, true).getFirst();
		assertNotNull("[pre-condition] page has no sections!", section);


		String textEditor = null;
		for (final GomFormElement next : section.getTemplate().getGomProvider().forms()) {
			if (next instanceof GomText) {
				textEditor = next.name();
				break;
			}
		}
		assertNotNull("failed to determine text editor", textEditor);
		final EditorIdentifier identifier = EditorIdentifier.on(section.getId(), Store.Type.PAGESTORE).editor(textEditor).build();
		try {
			we().driver().switchTo().frame(we().preview().html());
			final WebElement previewElement = we().driver().findElement(By.cssSelector("[data-fs-id=\"" + identifier.getId() + "\"]"));
			new Actions(we().driver()).moveToElement(previewElement, 4, 4).build().perform();
			((JavascriptExecutor) we().driver()).executeScript(
					FileUtilities.inputStreamToString(UiTestInlineEdit.class.getResourceAsStream("UiTestInlineEdit_Edit.js"), "UTF-8"),
					previewElement
			);
		} finally {
			we().driver().switchTo().defaultContent();
		}

		final WebElement container = we().driver().findElement(By.cssSelector(".fs-InlineEdit-container"));
		final WebElement textBox = container.findElement(By.cssSelector(".gwt-TextBox"));
		final String newValue = "Modified text at " + new Date().toString();
		((JavascriptExecutor) we().driver()).executeScript("arguments[0].value = arguments[1];", textBox, newValue);
		((JavascriptExecutor) we().driver()).executeScript(
				FileUtilities.inputStreamToString(UiTestInlineEdit.class.getResourceAsStream("FireEvent.js"), "UTF-8"),
				"change", textBox
		);
		final WebElement save = container.findElement(By.cssSelector(".fs-action-toolbar-highlight img"));
		save.click();
		Thread.sleep(1000);
		section.refresh();
		assertEquals(newValue, section.getFormData().get(element.getProject().getMasterLanguage(), textEditor).get());
	}
}
