package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;

import java.util.List;

public interface CCInputCheckbox extends CCInputComponent {

    List<CCInputCheckboxItem> values();

    interface CCInputCheckboxItem extends Web{

        String value();
        boolean checked();
        void toggle();
    }
}
