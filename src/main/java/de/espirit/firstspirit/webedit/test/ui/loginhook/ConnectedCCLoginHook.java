package de.espirit.firstspirit.webedit.test.ui.loginhook;


import de.espirit.firstspirit.access.AdminService;
import de.espirit.firstspirit.access.ConnectionManager;
import de.espirit.firstspirit.access.admin.ProjectStorage;
import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.agency.ClientUrlAgent;
import de.espirit.firstspirit.io.ServerConnection;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.ConnectedCC;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FSImpl;



/**
 * The ConnectedCCLoginHook connects to the FirstSpirit server. Here, assuming that this can be reached directly.
 * It also assumes the task of build a connection on instantiation and creating an FS object.
 */
public class ConnectedCCLoginHook extends AbstractLoginHook {
	
	
	@Override
	public ConnectedCC createCC(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		String projectNameOrId = super._fs.getProjectName();
		super.LOGGER.info("Connecting to _project '" + projectNameOrId + '\'');
		ProjectStorage prjStorage = super._fs.connection().getService(AdminService.class).getProjectStorage();
		Project        project;
		
		try {
			project = prjStorage.getProject(Long.parseLong(projectNameOrId));
		} catch (final Exception e) {
			project = prjStorage.getProject(projectNameOrId);
		}
		if (project == null) {
			throw new IllegalStateException("couldn't find _project '" + projectNameOrId + "' !");
		}
		
		String url = super._fs.connection().getBroker().requireSpecialist(ClientUrlAgent.TYPE).getBuilder(ClientUrlAgent.ClientType.WEBEDIT).project(project).createUrl();
		super.disableTourHints(super._fs.connection());
		super._cc = new ConnectedCC(project, browserRunner.getWebDriver(), url, super._fs.connection().createTicket());
		
		return (ConnectedCC) super._cc;
	}
	
	
	@Override
	public FS createFS(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		super.LOGGER.info("Connecting to host '" + super._host + "', port '" + super._port + "' with user '" + super._username + '\'');
		
		try {
			ServerConnection connection = (ServerConnection) ConnectionManager.getConnection(super._host, Integer.parseInt(super._port), ConnectionManager.HTTP_MODE, super._username, super._password);
			connection.connect();
			
			super._fs = new FSImpl(connection, super._project);
		} catch (final Exception e) {
			throw new RuntimeException("connecting FirstSpirit server failed (" + super._host + ':' + super._port + ") !", e);
		}
		
		return super._fs;
	}
}

