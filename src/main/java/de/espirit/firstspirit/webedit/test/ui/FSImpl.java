package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.firstspirit.access.Connection;
import de.espirit.firstspirit.io.ServerConnection;


/**
* Implementation of the {@link FS FirstSpirit server adapter}.
*/
class FSImpl implements FS {

	private final ServerConnection _connection;

	FSImpl(final ServerConnection connection) {
		_connection = connection;
	}


	@Override
	public ServerConnection connection() {
		return _connection;
	}

}
