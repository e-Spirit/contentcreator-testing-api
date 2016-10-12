package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview;

import de.espirit.firstspirit.client.EditorIdentifier;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collection;

/**
 * Provides access to the preview pane and it's content.
 */
public interface Preview extends Web {

    /**
     * Returns the current url of the iframe.
     *
     * @return current iframe url.
     */
    String getUrl();

    /**
     * Loads the given {@code url} into the preview iframe. <br>
     * <b>Important: </b> Loading an external url could break the WebEdit instance.
     *
     * @param url to load.
     * @throws IOException IOException
     */
    void setUrl(final String url) throws IOException;

    /**
     * Reloads only WebEdit's preview pane (like pressing &lt;Ctrl&gt;+R).
     *
     * @see CC#reload()
     */
    void reload();

    /**
     * Returns a maybe empty list of actions of the given identifier or {@code null} if it couldn't be found.
     *
     * @param identifier FSID of a store-element.
     * @return {@code null} if the identifier couldn't be found, otherwise returns a maybe empty list of actions.
     */
    @Nullable
    Collection<Action> actionsOf(@NotNull final EditorIdentifier identifier) throws CCAPIException;

    /**
     * Action, returned by {@link #actionsOf(EditorIdentifier)}.
     */
    interface Action extends Web {

        /**
         * Executes this action.
         */
        void execute();

        /**
         * Returns the action's tooltip which can be useful to identify it.
         *
         * @return tooltip id, useful to identify this action.
         */
        String tooltip();

    }
}
