package de.espirit.firstspirit.webedit.test.ui.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * @author Benjamin Nagel &lt;nagel@e-spirit.com&gt;
 */
public class ImageUtils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class);


	/**
	 * Takes a screenshot of the whole page and stores it below the error-file-path
	 *
	 * @param webDriver the webdriver to use
	 * @param myfilename the filename to use, extension will be automatically added
	 */
	@Nullable
	public static File takeScreenshot(final WebDriver webDriver, final String myfilename) {
		if ((Utils.getErrorFilePath() != null) && (webDriver instanceof PhantomJSDriver)) {
			java.io.File screenshot = null;
			if (webDriver instanceof PhantomJSDriver) {
				screenshot = ((PhantomJSDriver) webDriver).getScreenshotAs(OutputType.FILE);
			} else if (webDriver instanceof ChromeDriver) {
				screenshot = ((ChromeDriver) webDriver).getScreenshotAs(OutputType.FILE);
			}
			if (screenshot == null) {
				throw new IllegalArgumentException("webDriver don't support screenshots");
			} else {
				try {
					final String fileName = ErrorHandler.getErrorFilePath() + "/" + myfilename + ".png ";
					FileUtils.copyFile(screenshot, new File(fileName));
					ImageUtils.LOGGER.info("Screenshot saved to " + fileName);
					return screenshot;
				} catch (final IOException e) {
					ImageUtils.LOGGER.error("", e);
				}
			}
		}
		return null;
	}


	/**
	 * Calculate the difference between two given images and create a new image with the difference informations. When there are no difference the resulting
	 * image is black. Otherwise it will show the informations of the <code>img1</code> image.
	 *
	 * @param img1 the original image
	 * @param img2 the potentially modified image
	 * @return the result image or img2 when the dimension are not equal
	 */
	@NotNull
	public static BufferedImage getDifferenceImage(@NotNull final BufferedImage img1, @NotNull final BufferedImage img2) {
		final int width1 = img1.getWidth();
		final int width2 = img2.getWidth();
		final int height1 = img1.getHeight();
		final int height2 = img2.getHeight();

		if ((width1 != width2) || (height1 != height2)) {
			return img2;
		} else {
			final BufferedImage outImg = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < height1; i++) {
				for (int j = 0; j < width1; j++) {
					final int rgb1 = img1.getRGB(j, i);
					final int rgb2 = img2.getRGB(j, i);
					final int r1 = (rgb1 >> 16) & 0xff;
					final int g1 = (rgb1 >> 8) & 0xff;
					final int b1 = (rgb1) & 0xff;
					final int r2 = (rgb2 >> 16) & 0xff;
					final int g2 = (rgb2 >> 8) & 0xff;
					final int b2 = (rgb2) & 0xff;
					int diff = Math.abs(r1 - r2);
					diff += Math.abs(g1 - g2);
					diff += Math.abs(b1 - b2);
					diff /= 3; // Change - Ensure result is between 0 - 255

					// Make the difference image gray scale
					// The RGB components are all the same
					final int result = (diff << 16) | (diff << 8) | diff;
					outImg.setRGB(j, i, result);
				}
			}
			outImg.flush();

			// Now return
			return outImg;
		}
	}


	/**
	 * Checks if there is somewhere a not black pixel in the image. That indicates that {@link ImageUtils#getDifferenceImage(BufferedImage, BufferedImage)}
	 * returned a image with differences.
	 *
	 * @param img the generated image.
	 * @return true if the images are not equal or the dimension are not equal.
	 */
	public static boolean hasDifference(@NotNull final BufferedImage img) {
		final int width = img.getWidth();
		final int height = img.getHeight();
		if ((width <= 0) || (height <= 0)) {
			return true;
		} else {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					final int rgb = img.getRGB(j, i);
					final Color mycolor = new Color(rgb);
					if (!mycolor.equals(Color.BLACK)) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
