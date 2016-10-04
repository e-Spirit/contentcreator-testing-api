package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.access.store.IDProvider;
import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.common.FileUtilities;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.Random;

public class UiTestMediaUpload extends AbstractUiTest {

    private static final String TOOLBAR_MEDIA = "toolbar_menu_media.png";
    private static final String WIZARD_TARGET = "wizard-step-folderdestination.png";
    private static final String WIZARD_SAVE = "button_ok.png";

    @Test
    @UiTestRunner.BrowserLocale("de")
    public void fileUpload() throws Exception {
        final PageRef element = getStartNode();
        final String url = toPreviewUrl(element);
        we().preview().setUrl(url);

        final WebElement toolbar = we().driver().findElement(By.cssSelector(".icon-text[style*=\"" + TOOLBAR_MEDIA + "\"]"));
        ((JavascriptExecutor) we().driver()).executeScript(FileUtilities.inputStreamToString(UiTestInlineEdit.class.getResourceAsStream("FireEvent.js"), "UTF-8"), "mouseover", toolbar);

        final WebElement flyout = we().driver().findElement(By.className("fs-toolbar-flyout"));
        final WebElement upload = flyout.findElement(By.tagName("li"));
        ((JavascriptExecutor) we().driver()).executeScript(FileUtilities.inputStreamToString(UiTestInlineEdit.class.getResourceAsStream("FireEvent.js"), "UTF-8"), "click", upload); // in IE11 a simple "upload.click()" causes an exception because the flyout is closed before the event is fired

        final Store mediastore = we().project().getUserService().getStore(Store.Type.MEDIASTORE, false);
        String uid;
        while (true) {
            uid = "we_ui_test_upload_" + randomString();
            final IDProvider media = mediastore.getStoreElement(uid, IDProvider.UidType.MEDIASTORE_LEAF);
            if (media == null) {
                break;
            }
        }

        final WebElement dropTarget = we().driver().findElement(By.className("fs-MediaManagement-MediaContent-Mask"));
        ((JavascriptExecutor) we().driver()).executeScript(FileUtilities.inputStreamToString(UiTestMediaUpload.class.getResourceAsStream("UiTestMediaUpload.js"), "UTF-8"), dropTarget, uid);

        final WebElement target = we().driver().findElement(By.cssSelector(".fs-WizardDialogBox-Step-Icon[src*=\"" + WIZARD_TARGET + "\"]"));
        target.click();

        final WebElement root = we().driver().findElement(By.className("tree-label"));
        root.click();

        final WebElement save = we().driver().findElement(By.cssSelector(".fs-WizardDialogBox-Side-Buttons .gwt-Image[src*=\"" + WIZARD_SAVE + "\"]"));
        save.click();

        Thread.sleep(500);

        mediastore.refresh();
        final IDProvider media = mediastore.getStoreElement(uid, IDProvider.UidType.MEDIASTORE_LEAF);

        assertNotNull("medium should be created but couldn't be found: " + uid, media);
        media.setLock(true);
        media.delete();
    }

    private String randomString() {
        return Integer.toString(Math.abs(new Random().nextInt()), Character.MAX_RADIX);
    }
}
