package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent.CCInputButton;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent.CCInputComponent;
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
    List<CCInputComponent> inputComponents();

    /**
     * Returns a specific input component by the given display name
     * @param displayName
     * @return
     */
    CCInputComponent inputComponentByName(@NotNull final String displayName);

    /**
     * Clicks ok button
     */
    void ok();

    /**
     * Clicks the cancel button
     */
    void cancel();

    /**
     * Returns all buttons within the dialog footer
     * @return all buttons
     */
    List<CCInputButton> buttons();
}
