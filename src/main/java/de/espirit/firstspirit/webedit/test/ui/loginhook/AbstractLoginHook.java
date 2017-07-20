package de.espirit.firstspirit.webedit.test.ui.loginhook;


import de.espirit.firstspirit.access.Connection;
import de.espirit.firstspirit.access.User;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;



/**
 * Created by vaccarisi on 02.02.2017.
 */
public abstract class AbstractLoginHook implements LoginHook {
	
	protected static final Logger LOGGER = Logger.getLogger(ConnectedCCLoginHook.class);
	
	protected final String _host;
	protected final String _port;
	protected final String _username;
	protected final String _password;
	protected final String _project;
	protected final String _useHttps;
	
	protected FS                         _fs;
	protected CC                         _cc;
	protected UiTestRunner               _uiTestRunner;
	protected UiTestRunner.BrowserRunner _browserRunner;
	
	
	public AbstractLoginHook() {
		_host = Utils.env(PARAM_HOST, DEFAULT_HOST);
		_port = Utils.env(PARAM_PORT, DEFAULT_PORT);
		_username = Utils.env(PARAM_USER, DEFAULT_USERNAME);
		_password = Utils.env(PARAM_PASSWORD, DEFAULT_PASSWORD);
		_project = Utils.env(PARAM_PROJECT, DEFAULT_PROJECT_NAME);
		_useHttps = Utils.env(PARAM_USEHTTPS, DEFAULT_USEHTTPS);
	}
	
	
	@Override
	public CC createCC(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		tearDownCC();
		
		_uiTestRunner = uiTestRunner;
		_browserRunner = browserRunner;
		
		return _cc;
	}
	
	
	@Override
	public FS createFS(final UiTestRunner uiTestRunner, final UiTestRunner.BrowserRunner browserRunner) {
		tearDownCC();
		tearDownFS();
		
		_uiTestRunner = uiTestRunner;
		_browserRunner = browserRunner;
		
		return _fs;
	}
	
	
	protected void disableTourHints(@NotNull final Connection connection) {
		User                user     = connection.getUser();
		Map<String, String> bindings = user.getUserBindings();
		bindings.put("_cc.tour.disabled", String.valueOf(true));
		bindings.put("_cc.hints.disabled", String.valueOf(true));
		user.setUserBindings(bindings);
	}
	
	
	@Override
	public void tearDownCC() {
		if (_cc != null) {
			_cc.logout();
		}
	}
	
	
	@Override
	public void tearDownFS() {
		try {
			if (_fs != null && _fs.connection() != null) {
				_fs.connection().disconnect();
			}
		} catch (final IOException e) {
			throw new RuntimeException("disconnecting FirstSpirit server failed!", e);
		}
	}
	
	
	@Override
	public CC getCC() {
		return _cc;
	}
	
	
	@Override
	public FS getFS() {
		return _fs;
	}
}
