package de.espirit.firstspirit.webedit.test.ui.firstspirit;

import de.espirit.firstspirit.io.ServerConnection;

/**
 * Implementation of the {@link FS FirstSpirit server adapter}.
 */
public class FSImpl implements FS {

    private final ServerConnection _connection;

    public FSImpl(final ServerConnection connection) {
        _connection = connection;
    }

    @Override
    public ServerConnection connection() {
        return _connection;
    }

}
