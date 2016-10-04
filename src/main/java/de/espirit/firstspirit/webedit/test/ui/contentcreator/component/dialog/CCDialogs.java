package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;


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
}
