package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.input;

/**
 * @author Benjamin Nagel &lt;nagel@e-spirit.com&gt;
 * @param <T>
 */
public class CCInputEntry<T> {

	private final String componentName;
	private final T value;


	public CCInputEntry(final String componentName, final T value) {
		this.componentName = componentName;
		this.value = value;
	}


	public String getComponentName() {
		return this.componentName;
	}


	public T getValue() {
		return this.value;
	}

}
