package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;

/**
 * Provides access to a wizard step
 */
public interface CCWizardStep {

    /**
     * Returns the content of the current step
     * @return WebElement
     */
    @NotNull
    WebElement content();
}
