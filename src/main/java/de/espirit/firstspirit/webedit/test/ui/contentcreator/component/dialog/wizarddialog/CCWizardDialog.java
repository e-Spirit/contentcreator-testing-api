package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides access to the wizard dialog
 */
public interface CCWizardDialog extends Web {

    /**
     * Returns the wizard step identified by the given display name
     * @param displayName the display name of the step
     * @return CCWizardStep
     */
    CCWizardStep stepByName(@NotNull final String displayName);

    /**
     * Returns all buttons within the dialog footer
     * @return all buttons
     */
    List<CCInputButton> buttons();

    /**
     * Returns the button identified by the given display name
     * @param displayName the display name of the button
     * @return button if found, otherwise null
     */
    CCInputButton buttonByName(@NotNull final String displayName);
}
