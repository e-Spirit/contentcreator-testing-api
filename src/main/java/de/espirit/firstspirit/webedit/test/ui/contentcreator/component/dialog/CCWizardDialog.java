package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputButton;

import java.util.List;

/**
 * Provides access to the wizard dialog
 */
public interface CCWizardDialog extends Web {

    /**
     * Returns the wizard step by the given display name
     * @param displayName the display name of the step
     * @return CCWizardStep
     */
    CCWizardStep stepByName(String displayName);

    /**
     * Returns all buttons within the dialog footer
     * @return all buttons
     */
    List<CCInputButton> buttons();
}
