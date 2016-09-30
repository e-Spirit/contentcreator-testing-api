package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.firstspirit.access.Connection;
import de.espirit.firstspirit.io.ServerConnection;


/**
 * Provides access to the appropriate FirstSpirit server, see {@link #connection()}.
 */
public interface FS {

	/**
	 * Returns the FirstSpirit connection.
	 *
	 * @return FirstSpirit connection.
	 */
	ServerConnection connection();

}
