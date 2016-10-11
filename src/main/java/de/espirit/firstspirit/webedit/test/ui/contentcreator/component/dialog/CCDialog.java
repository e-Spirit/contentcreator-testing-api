package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides access to the current dialog
 */
public interface CCDialog extends Web {
    /**
     * Returns all input components within the dialog
     * @return all input components
     */
    List<CCInputComponent> inputComponents() throws CCAPIException;

    /**
     * Returns a specific input component by the given display name
     * @param displayName display name of the input component
     * @return input component if found, otherwise null
     */
    CCInputComponent inputComponentByName(@NotNull final String displayName) throws CCAPIException;

    /**
     * Clicks ok button
     */
    void ok() throws CCAPIException;

    /**
     * Clicks the cancel button
     */
    void cancel() throws CCAPIException;

    /**
     * Returns all buttons within the dialog footer
     * @return all buttons
     */
    List<CCInputButton> buttons() throws CCAPIException;
}
