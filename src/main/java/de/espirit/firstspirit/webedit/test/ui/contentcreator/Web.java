package de.espirit.firstspirit.webedit.test.ui.contentcreator;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;

/**
 * Common super interface for all UI elements.
 */
public interface Web {

    /**
     * Provides a low level access to the html of this UI element.
     *
     * @return low level html access.
     */
    @NotNull
    WebElement html();

}
