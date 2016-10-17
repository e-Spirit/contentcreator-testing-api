package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.access.store.IDProvider;
import de.espirit.firstspirit.access.store.Previewable;
import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.access.store.sitestore.SiteStoreFolder;
import de.espirit.firstspirit.agency.ClientUrlAgent;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
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

    private FS fs;
    private CC cc;

    private String locale;

    //--- public methods ---//

    /**
     * Returns the connection to the FirstSpirit server.
     * @return FS
     */
    @NotNull
    public FS fs() {
        return fs;
    }

    /**
     * Returns the connection to the WebEdit client.
     * @return CC
     */
    @NotNull
    public CC cc() {
        return cc;
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

        if(element instanceof Previewable)
        {
            Previewable previewable = (Previewable) element;
            return previewable.getPreviewUrl(project.getMasterLanguage(), project.getWebEditTemplateSet(), false, PREVIEWMODE_FULL_QUALIFIED | PREVIEWMODE_WEBEDIT, null);
        }

        return null;
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

    /**
     * Navigates to a page
     * @param pageRef The page ref to navigate to
     */
    public void navigateTo(@NotNull PageRef pageRef) {

        String url = fs.connection().getBroker().requireSpecialist(ClientUrlAgent.TYPE).getBuilder(ClientUrlAgent.ClientType.WEBEDIT).project(cc.project()).element(pageRef).createUrl();
        if (url.contains("&locale=")) {
            url = url.replaceAll("&locale=\\w+", "&locale=" + locale);
        } else {
            url += "&locale=" + locale;
        }
        cc().driver().get(url);
        Utils.waitForCC();
    }


    //--- package protected methods ---//

    void setFS(final FS fs) {
        this.fs = fs;
    }

    void setCC(final CC CC) {
        cc = CC;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
