package oasis.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import oasis.core.Logger;
import oasis.graphics.Texture;
import oasis.graphics.Texture2D;

/**
 * Loads and manages textures from the file system 
 * 
 * @author Nicholas Hamilton
 *
 */
public class TextureLoader {

	private static final Logger log = new Logger(TextureLoader.class); 
	
	private static final Map<String, Texture2D> loaded = new HashMap<>(); 
	
	private TextureLoader() {} 
	
	/**
	 * Checks if the image at [filename] has been loaded. If 
	 * it has, it is returned. Otherwise it is loaded into memory, 
	 * stored, and returned. If the file cannot be found, a
	 * 1 x 1 pink image is returned. 
	 * 
	 * @param filename
	 * @return
	 */
	public static Texture2D get(String filename) {
		Texture2D tex = loaded.get(PathList.DEFAULT.find(filename)); 
		if (tex == null) {
			tex = load(filename); 
			loaded.put(filename, tex); 
		}
		
		return tex; 
	}
	
	// load and convert from BufferedImage to Texture2D
	private static Texture2D load(String filename) {
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
			log.warning("Could not find image at location \"" + filename + "\", returning default image");
			return createDefaultTexture(); 
		} 
	}
	
	// create a new Texture2D each time since the contents are mutable
	private static Texture2D createDefaultTexture() {
		Texture2D tex = new Texture2D(Texture.Format.RGBA8, 1, 1); 
		tex.setPixels(0, 0, 1, 1, new int[] { 0xFF00FFFF }, 0);
		tex.upload(); 
		return tex; 
	}
	
}