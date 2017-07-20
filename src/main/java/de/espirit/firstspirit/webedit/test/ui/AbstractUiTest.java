package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.CCImpl;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FSImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.access.store.Previewable;
import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.access.store.sitestore.SiteStoreFolder;
import de.espirit.firstspirit.agency.ClientUrlAgent;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;

/**
 * Abstract super class for <b>all</b> WebEdit UI tests. The {@link UiTestRunner UI test runner}
 * ensures that the connection to the FirstSpirit server {@link #fs()} and WebEdit client
 * {@link #cc()} are properly initialized.
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


  // --- public methods ---//

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
   * Returns the connection to the WebEdit client.
   *
   * @return CC
   */
  @NotNull
  public CC cc() {
    return this.cc;
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

    return element.getPreviewUrl(project.getMasterLanguage(), project.getWebEditTemplateSet(),
        false, Previewable.PREVIEWMODE_FULL_QUALIFIED | Previewable.PREVIEWMODE_WEBEDIT, null);
  }


  /**
   * Returns sitestore start node.
   *
   * @return start node.
   */
  @NotNull
  public PageRef getStartNode() {
    final PageRef startNode = (PageRef) ((SiteStoreFolder) this.cc().project().getUserService()
        .getStore(Store.Type.SITESTORE, false)).findStartNode();
    Assert.assertNotNull("pre-condition: no start-node found!", startNode);
    return startNode;
  }


  /**
   * Navigates to a page
   *
   * @param pageRef The page ref to navigate to
   */
  public void navigateTo(@NotNull final PageRef pageRef) {

    String url = this.fs.connection().getBroker().requireSpecialist(ClientUrlAgent.TYPE)
        .getBuilder(ClientUrlAgent.ClientType.WEBEDIT).project(this.cc.project()).element(pageRef)
        .createUrl();
    if (url.contains("&locale=")) {
      url = url.replaceAll("&locale=\\w+", "&locale=" + this.locale);
    } else {
      url += "&locale=" + this.locale;
    }
    this.cc().driver().get(url);
    Utils.waitForCC(this.cc().driver());
  }

  public void switchProject(@NotNull final String projectName) {
    FSImpl fsImpl = (FSImpl) fs;
    CCImpl ccImpl = (CCImpl) cc;

    fsImpl.setProjectName(projectName);
    final Project project = fs.project().get();
    ccImpl.setProject(project);

    String url = this.fs.connection().getBroker()
            .requireSpecialist(ClientUrlAgent.TYPE)
            .getBuilder(ClientUrlAgent.ClientType.WEBEDIT)
            .project(project)
            .createUrl();
    if (url.contains("&locale=")) {
      url = url.replaceAll("&locale=\\w+", "&locale=" + this.locale);
    } else {
      url += "&locale=" + this.locale;
    }
    this.cc().driver().get(url);
    Utils.waitForCC(this.cc().driver());
  }


  // --- package protected methods ---//

  void setFS(final FS fs) {
    this.fs = fs;
  }


  void setCC(final CC CC) {
    this.cc = CC;
  }


  public void setLocale(final String locale) {
    this.locale = locale;
  }

}
