package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.WE;

import org.junit.Test;


/**
 * Tests all elements of the UI {@link WE HTML adapter api}.
 */
public class UiTestConnections extends AbstractUiTest {

	/**
	 * Help to provoke memory problems, see <a href="http://ts/185829">TS#185829</a>.
	 */
	@Test
	public void connections() throws Exception {
		final int COUNT = 500; // x times logout/login

		for (int i = 0; i < COUNT; i++) {
			final String url = we().driver().getCurrentUrl();
			we().logout();
			we().driver().navigate().to(url.replaceAll("/s=....", ""));
			assertNotNull("preview not found!", we().preview().html());	// TEST: successful logged in
		}
	}
}
