package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent.CCInputComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CCDialog extends Web {
    List<CCInputComponent> inputComponents();
    CCInputComponent inputComponentByName(@NotNull final String name);
}
