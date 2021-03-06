package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides access to the current message dialog
 */
public interface CCMessageDialog extends Web {
    /**
     * Returns the message of the message dialog
     * @return message of the dialog
     */
    String message() throws CCAPIException;

    /**
     * Clicks ok button
     */
    void ok() throws CCAPIException;

    /**
     * Returns all buttons within the message dialog footer
     * @return all buttons
     */
    List<CCInputButton> buttons() throws CCAPIException;

    /**
     * Returns the button identified by the given display name
     * @param displayName the display name of the button
     * @return button if found, otherwise null
     */
    CCInputButton buttonByName(@NotNull final String displayName) throws CCAPIException;
}
