package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

/**
 * Represents a basic input component
 */
public interface CCInputComponent extends Web {

    /**
     * Returns the label of the component
     * @return label
     */
    String label() throws CCAPIException;

    /**
     * Returns the displayed error message
     * @return the error message if there is any
     */
    default String errorMessage() throws CCAPIException {
        return "";
    }
}
