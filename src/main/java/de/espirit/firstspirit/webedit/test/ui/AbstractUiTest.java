package de.espirit.firstspirit.webedit.test.ui;


import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.access.store.Previewable;
import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.access.store.sitestore.SiteStoreFolder;
import de.espirit.firstspirit.agency.ClientUrlAgent;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.ConnectedCC;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.runner.RunWith;



/**
 * Abstract super class for <b>all</b> WebEdit UI tests. The {@link UiTestRunner UI test runner}
 * ensures that the connection to the FirstSpirit server {@link #fs()} and WebEdit client
 * {@link #cc()} are properly initialized.
 *
 * @see FS
 * @see ConnectedCC
 */
@SuppressWarnings("AbstractClassExtendsConcreteClass")
@RunWith(UiTestRunner.class)
public abstract class AbstractUiTest extends AbstractSimplyUiTest {
	
	private FS fs;
	
	// --- public methods ---//
	
	
	/**
	 * Returns the connection to the WebEdit client.
	 *
	 * @return ConnectedCC
	 */
	@NotNull
	public ConnectedCC cc() {
		return (ConnectedCC) super.cc();
	}
	
	
	/**
	 * Returns the connection to the FirstSpirit server.
	 *
	 * @return FS
	 */
	@NotNull
	public FS fs() {
		return this.fs;
	}
	
	
	/**
	 * Navigates to a page
	 *
	 * @param pageRef The page ref to navigate to
	 */
	public void navigateTo(@NotNull final PageRef pageRef) {
		
		String url = fs.connection().getBroker().requireSpecialist(ClientUrlAgent.TYPE).getBuilder(ClientUrlAgent.ClientType.WEBEDIT).project(cc().project()).element(pageRef).createUrl();
		if (url.contains("&locale=")) {
			url = url.replaceAll("&locale=\\w+", "&locale=" + super.locale());
		} else {
			url += "&locale=" + super.locale();
		}
		cc().driver().get(url);
		Utils.waitForCC(cc().driver());
	}
	
	
	/**
	 * Provides a {@link Preview#setUrl(String) preview-url} of the given {@code element}.
	 *
	 * @param element to view in the preview frame.
	 * @return preview-url of the given {@code element}.
	 * @see Preview#setUrl(String)
	 */
	public String toPreviewUrl(final Previewable element) {
		final Project project = element.getProject();
		
		return element.getPreviewUrl(project.getMasterLanguage(), project.getWebEditTemplateSet(), false, Previewable.PREVIEWMODE_FULL_QUALIFIED | Previewable.PREVIEWMODE_WEBEDIT, null);
	}
	
	
	/**
	 * Returns sitestore start node.
	 *
	 * @return start node.
	 */
	@NotNull
	public PageRef getStartNode() {
		final PageRef startNode = (PageRef) ((SiteStoreFolder) this.cc().project().getUserService().getStore(Store.Type.SITESTORE, false)).findStartNode();
		Assert.assertNotNull("pre-condition: no start-node found!", startNode);
		return startNode;
	}
	
	
	// --- package protected methods ---//
	
	
	void setFS(final FS fs) {
		this.fs = fs;
	}
}
