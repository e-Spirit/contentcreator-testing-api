package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.access.store.ContentProducer;
import de.espirit.firstspirit.access.store.IDProvider;
import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.access.store.sitestore.SiteStoreFolder;
import de.espirit.firstspirit.store.access.AccessImplUtil;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.Preview;
import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;

import static de.espirit.firstspirit.access.store.Previewable.PREVIEWMODE_FULL_QUALIFIED;
import static de.espirit.firstspirit.access.store.Previewable.PREVIEWMODE_WEBEDIT;

/**
 * Abstract super class for <b>all</b> WebEdit UI tests. The {@link UiTestRunner UI test runner} ensures that the connection to
 * the FirstSpirit server {@link #fs()} and WebEdit client {@link #cc()} are properly initialized.
 *
 * @see FS
 * @see CC
 */
@SuppressWarnings("AbstractClassExtendsConcreteClass")
@RunWith(UiTestRunner.class)
public abstract class AbstractUiTest extends Assert {

    private FS _fs;
    private CC _CC;

    //--- public methods ---//

    /**
     * Returns the connection to the FirstSpirit server.
     * @return FS
     */
    @NotNull
    public FS fs() {
        return _fs;
    }

    /**
     * Returns the connection to the WebEdit client.
     * @return CC
     */
    @NotNull
    public CC cc() {
        return _CC;
    }

    /**
     * Provides a {@link Preview#setUrl(String) preview-url} of the given {@code element}.
     *
     * @param element to view in the preview frame.
     * @return preview-url of the given {@code element}.
     * @see Preview#setUrl(String)
     */
    public String toPreviewUrl(final IDProvider element) {
        final Project project = element.getProject();
        return AccessImplUtil.getPreviewUrl(project.getUserService(), (ContentProducer) element, project.getMasterLanguage(), project.getWebEditTemplateSet(), false, PREVIEWMODE_FULL_QUALIFIED | PREVIEWMODE_WEBEDIT, null);
    }

    /**
     * Returns sitestore start node.
     *
     * @return start node.
     */
    @NotNull
    public PageRef getStartNode() {
        final PageRef startNode = (PageRef) ((SiteStoreFolder) cc().project().getUserService().getStore(Store.Type.SITESTORE, false)).findStartNode();
        assertNotNull("pre-condition: no start-node found!", startNode);
        return startNode;
    }

    //--- package protected methods ---//

    void setFS(final FS fs) {
        _fs = fs;
    }

    void setCC(final CC CC) {
        _CC = CC;
    }

}
