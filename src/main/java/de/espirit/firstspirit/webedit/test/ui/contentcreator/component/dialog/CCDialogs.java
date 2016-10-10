package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.messagedialog.CCMessageDialog;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.wizarddialog.CCWizardDialog;

/**
 * Provides access to the different dialogs
 */
public interface CCDialogs {

    /**
     * Gets the current wizard dialog if available
     * @return Returns the current wizard dialog if available, otherwise null
     */
    CCWizardDialog wizardDialog();

    /**
     * Gets the current dialog if available
     * @return Returns the current dialog if available, otherwise null
     */
    CCDialog dialog();

    /**
     * Gets the current message dialog if available
     * @return Returns the current message dialog if available, otherwise null
     */
    CCMessageDialog messageDialog();
}
