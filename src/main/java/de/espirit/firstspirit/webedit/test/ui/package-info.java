/**
 * UI test framework for FirstSpirit ContentCreator.
 *
 * <p>
 * <b>Write an UI test</b>
 * <ul>
 *     <li>Create a sub-class of {@link de.espirit.firstspirit.webedit.test.ui.AbstractUiTest}.</li>
 *     <li>Use {@link de.espirit.firstspirit.webedit.test.ui.WE} to access ContentCreator UI elements (html).</li>
 *     <li>Use {@link de.espirit.firstspirit.webedit.test.ui.FS} to access FirstSpirit server.</li>
 * </ul>
 *
 * <b>Extend accessible UI elements</b>
 * <ul>
 *     <li>Create a new or extend an existing UI interfaces like {@link de.espirit.firstspirit.webedit.test.ui.MenuBar},
 *         {@link de.espirit.firstspirit.webedit.test.ui.Preview}, {@link de.espirit.firstspirit.webedit.test.ui.Reports}, ...</li>
 *     <li>Access the html by using Selenium's {@link org.openqa.selenium.WebElement WebElement} interface.</li>
 *     <li>Extend {@link de.espirit.firstspirit.webedit.test.ui.WE} to access the new interface.</li>
 * </ul>
 *
 *
 * <p>
 * <b>See also:</b>
 * <ul>
 *     <li><a href="https://docs.google.com/document/d/1_Nt6QWvFPa4FCNbNUIOdJ2qj428LimDvEtc3461A0qI/edit">Google Drive Document</a></li>
 * </ul>
 */
package de.espirit.firstspirit.webedit.test.ui;