package com.example;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;

public class App {


      public static void construct(URI uri, List<PixelElement> elements) {
        try {
            // Create file if it does not exist
            File file = new File(uri);
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created) {
                    throw new RuntimeException("Could not create file at " + uri);
                }
            }

            // Calculate max width and max height
            int maxWidth = -1;
            int maxHeight = -1;
            for (PixelElement pElement : elements) {
                if (maxWidth < pElement.position.x) {
                    maxWidth = pElement.position.x;
                }
                if (maxHeight < pElement.position.y) {
                    maxHeight = pElement.position.y;
                }
            }

            // Start creating the image
            int IMAGE_DEPTH = 32;
            ImageData imageData = new ImageData(maxWidth + 1, maxHeight + 1, IMAGE_DEPTH, new PaletteData(255, 255, 255));

            for (PixelElement pElement : elements) {
                imageData.setAlpha(pElement.position.x, pElement.position.y, pElement.alpha);
                imageData.setPixel(pElement.position.x, pElement.position.y, pElement.color.hashCode());
            }

            // Save it
            Image image = new Image(null, imageData);
            ImageLoader imageLoader = new ImageLoader();
            imageLoader.data = new ImageData[] {image.getImageData()};
            imageLoader.save(file.getAbsolutePath(), SWT.IMAGE_PNG);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ImageAdapter imageAdapter = new ImageAdapter();
            String[] images = {"1.png", "2.png", "3.png", "4.png"};

            HashMap<PixelElement, Integer> pixelMap = new HashMap<>();

            for (String imagePath : images) {
                List<PixelElement> elements = imageAdapter.adapt(new URI(imagePath));
                for (PixelElement pixel : elements) {
                    pixelMap.put(pixel, pixelMap.getOrDefault(pixel, 0) + 1);
                }
            }

            // print the pixels for each image
            for (String imagePath : images) {
                System.out.println("Image: " + imagePath);
                List<PixelElement> elements = imageAdapter.adapt(new URI(imagePath));
                for (PixelElement pixel : elements) {
                    // System.out.println("Position: " + pixel.position + ", Color: " + pixel.color + ", Alpha: " + pixel.alpha);
                    System.out.println("" + pixel.position + ", " + pixel.color + ", " + pixel.alpha);
                }
                break;
            }
            System.out.println("====================================");
            if (true)
                return;
            

            List<PixelElement> commonPixels = new ArrayList<>();
            List<PixelElement> varyingPixels = new ArrayList<>();

            for (Map.Entry<PixelElement, Integer> entry : pixelMap.entrySet()) {
                if (entry.getValue() == images.length) {
                    commonPixels.add(entry.getKey());
                } else if (entry.getValue() < images.length) {
                    varyingPixels.add(entry.getKey());
                }
            }

            System.out.println("Common pixels:");
            for (PixelElement pixel : commonPixels) {
                System.out.println("Position: " + pixel.position + ", Color: " + pixel.color + ", Alpha: " + pixel.alpha);
            }

            System.out.println("Varying pixels:");
            for (PixelElement pixel : varyingPixels) {
                System.out.println("Position: " + pixel.position + ", Color: " + pixel.color + ", Alpha: " + pixel.alpha);
            }

            construct(new URI("file:/home/mathieuacher/Downloads/common.png"), commonPixels);
            construct(new URI("file:/home/mathieuacher/Downloads/varyingPixel.png"), varyingPixels);
       

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PixelElement {
        public RGB color;
        public int alpha;
        public Point position;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PixelElement that = (PixelElement) o;
            return alpha == that.alpha &&
                    color.equals(that.color) &&
                    position.equals(that.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color, alpha, position);
        }
    }

    public static class ImageAdapter {


  

        public List<PixelElement> adapt(URI uri) {
            List<PixelElement> elements = new ArrayList<>();
            File file = new File(uri);
            Image image = new Image(null, file.getPath());
            ImageData imageData = image.getImageData();

            for (int y = 0; y < imageData.height; y++) {
                for (int x = 0; x < imageData.width; x++) {
                    PixelElement pixel = new PixelElement();
                    int paletteInt = imageData.getPixel(x, y);
                    RGB rgb = imageData.palette.getRGB(paletteInt);
                    pixel.color = rgb;

                    if (imageData.getTransparencyType() == SWT.TRANSPARENCY_PIXEL
                            && imageData.transparentPixel == paletteInt) {
                        pixel.alpha = 0;
                    } else {
                        pixel.alpha = imageData.getAlpha(x, y);
                    }

                    if (pixel.alpha != 0) {
                        pixel.position = new Point(x, y);
                        elements.add(pixel);
                    }
                }
            }
            return elements;
        }
    }
}
