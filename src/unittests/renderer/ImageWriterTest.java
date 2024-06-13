package unittests.renderer;

import primitives.Color;
import renderer.ImageWriter;
import org.junit.jupiter.api.Test;
import static java.awt.Color.*;

/**
 * A class to test the functionality of the {@link ImageWriter} class.
 */
class ImageWriterTest {
	/**
	 * Test method to verify the functionality of writing an image with a grid
	 * pattern.
	 */
	@Test
	public void testWriteImage() {
		int imageWidth = 800; // Image width
		int imageHeight = 500; // Image height
		String imageName = "test_image"; // Image file name

		// Create ImageWriter object
		ImageWriter imageWriter = new ImageWriter(imageName, imageWidth, imageHeight);

		// Define colors for background and grid
		Color backgroundColor = Color.YELLOW; // Background color (in this example, black)
		Color gridColor = Color.RED; // Grid color (in this example, white)

		// Calculate grid size
		int squareWidth = imageWidth / 16; // Width of each grid square
		int squareHeight = imageHeight / 10; // Height of each grid square

		// Write background color to the entire image
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				imageWriter.writePixel(i, j, backgroundColor);
			}
		}

		// Write vertical grid lines
		for (int i = 0; i < imageWidth; i++) {
			if (i % squareWidth == 0) {
				for (int j = 0; j < imageHeight; j++) {
					imageWriter.writePixel(i, j, gridColor);
				}
			}
		}

		// Write horizontal grid lines
		for (int j = 0; j < imageHeight; j++) {
			if (j % squareHeight == 0) {
				for (int i = 0; i < imageWidth; i++) {
					imageWriter.writePixel(i, j, gridColor);
				}
			}
		}

		// Write the image to a file
		imageWriter.writeToImage();
	}
}
