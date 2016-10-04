package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.access.store.pagestore.Page;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UiTestEncoding extends AbstractUiTest {

    private static final String UMLAUTE = "\u00c4\u00d6\u00dc\u00e4\u00f6\u00fc\u00df";

    /**
     * Test for encoding issues in ContentCreator:
     * - TS#181631 CC | Falsche Darstellung von Umlauten, falsches Encoding
     * <p>
     * The resource bundle properties are encoded in ISO-8859-15, but GWT expects UTF-8.
     * We patched the resource bundle handling using a custom version of
     * "com.google.gwt.i18n.rebind.LocalizedPropertiesResource".
     * This test searches for potentially wrong encoded characters in the ContentCreator webpage.
     *
     * @throws Exception
     */
    @Test
    @UiTestRunner.BrowserLocale("de")
    public void encoding() throws Exception {

        final PageRef element = getStartNode();

        // We need at least one 'umlaut' character.
        // A modified page will result in a status label "Vernderte Seite" for language DE
        final Page page = element.getPage();
        assertNotNull(page);
        page.setLock(true, true);
        page.save("modified", true);
        page.setLock(false, true);

        final String url = toPreviewUrl(element);
        we().preview().setUrl(url);

        // Wait for the updated status label
        Thread.sleep(500);

        boolean umlautFound = false;
        final WebElement body = we().driver().findElement(By.tagName("body"));
        final String bodyText = body.getText();
        for (int i = 0; i < bodyText.length(); i++) {
            final char character = bodyText.charAt(i);
            // Test for wrong encoded character in the webpage (usually the unicode replacement character 0xFFFD)
            if (character > 0xFF00) {
                fail("wrong encoded text found: " + bodyText.substring(Math.max(i - 10, 0), Math.min(i + 10, bodyText.length())));
            } else if (UMLAUTE.indexOf(character) > -1) {
                umlautFound = true;
            }
        }
        if (!umlautFound) {
            fail("no umlaut found in the webpage. test for wrong encoded characters not possible");
        }
    }
}
