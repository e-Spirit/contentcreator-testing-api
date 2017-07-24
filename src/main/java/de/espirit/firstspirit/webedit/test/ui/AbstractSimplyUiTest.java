package de.espirit.firstspirit.webedit.test.ui;


import de.espirit.firstspirit.webedit.test.ui.contentcreator.CC;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.runner.RunWith;



/**
 * Abstract super class for <b>all</b> WebEdit UI tests. The {@link UiTestRunner UI test runner}
 * ensures that the connection to the WebEdit client
 * {@link #cc()} are properly initialized.
 *
 * @see CC
 */
@SuppressWarnings("AbstractClassExtendsConcreteClass")
@RunWith(UiTestRunner.class)
public abstract class AbstractSimplyUiTest extends Assert {
	
	private CC cc;
	
	private String locale;
	
	
	// --- public methods ---//
	
	
	/**
	 * Returns the connection to the WebEdit client.
	 *
	 * @return CC
	 */
	@NotNull
	public CC cc() {
		return this.cc;
	}
	
	
	// --- package protected methods ---//
	
	
	public String locale() {
		return locale;
	}
	
	
	void setCC(final CC CC) {
		this.cc = CC;
	}
	
	
	public void setLocale(final String locale) {
		this.locale = locale;
	}
	
}
