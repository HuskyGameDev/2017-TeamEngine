package oasis.core.loader;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import oasis.core.Logger;
import oasis.core.ResourceLoader;
import oasis.file.PathList;
import oasis.graphics.Texture;
import oasis.graphics.Texture2D;

/**
 * Loads and manages textures from the file system 
 * 
 * @author Nicholas Hamilton
 *
 */
public class TextureLoader implements ResourceLoader<Texture2D> {

	private static final Logger log = new Logger(TextureLoader.class); 
	
	// load and convert from BufferedImage to Texture2D
	public Texture2D load(String filename) {
		try {
			BufferedImage image = ImageIO.read(new File(PathList.DEFAULT.find(filename)));
			Texture2D tex = new Texture2D(Texture.Format.RGBA8, image.getWidth(), image.getHeight()); 
			
			int width = image.getWidth(); 
			int height = image.getHeight(); 
			
			// get ARGB pixels of image
			int[] data = new int[width * height]; 
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth()); 
			
			// convert to RGBA
			for (int i = 0; i < data.length; i++) {
			    data[i] = (data[i] << 8) | (data[i] >>> 24); 
			}
			
			for (int y = 0; y < height / 2; y++) {
			    for (int x = 0; x < width; x++) {
			        data[x + y * width] ^= data[x + (height - y - 1) * width]; 
			        data[x + (height - y - 1) * width] ^= data[x + y * width]; 
			        data[x + y * width] ^= data[x + (height - y - 1) * width]; 
			    }
			}
			
			// set pixels of texture
			tex.setPixels(0, 0, width, height, data, 0);
			tex.upload(); 
			
			return tex; 
		} catch (Exception e) {
			log.warning("Could not find image at location \"" + filename + "\"");
			return null; 
		} 
	}
	
}