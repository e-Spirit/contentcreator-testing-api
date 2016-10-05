package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

/**
 * Provides access to an input button component
 */
public interface CCInputButton extends CCInputComponent{
    /**
     * Returns the label from the input button
     * @return label
     */
    String label();

    /**
     * Clicks the input button
     */
    void click();
}
