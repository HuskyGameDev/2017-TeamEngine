package oasis.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import oasis.core.GameLogger;

/**
 * Loads and manages textures from the file system 
 * 
 * @author Nicholas Hamilton
 *
 */
public class TextureLoader {

	private static final GameLogger log = new GameLogger(TextureLoader.class); 
	
	private GraphicsDevice gd;
	private Map<String, Texture2D> loaded; 
	
	/**
	 * Constructor 
	 * 
	 * @param gd The engine's graphics device 
	 */
	public TextureLoader(GraphicsDevice gd) {
		this.gd = gd; 
		this.loaded = new HashMap<>(); 
	}
	
	/**
	 * Checks if the image at [filename] has been loaded. If 
	 * it has, it is returned. Otherwise it is loaded into memory, 
	 * stored, and returned. If the file cannot be found, a
	 * 1 x 1 pink image is returned. 
	 * 
	 * @param filename
	 * @return
	 */
	public Texture2D get(String filename) {
		Texture2D tex = loaded.get(filename); 
		if (tex == null || tex.isDisposed()) {
			tex = load(filename); 
			loaded.put(filename, tex); 
		}
		
		return tex; 
	}
	
	// load and convert from BufferedImage to Texture2D
	private Texture2D load(String filename) {
		try {
			BufferedImage image = ImageIO.read(new File(filename));
			Texture2D tex = gd.createTexture2D(Texture.Format.RGBA8, image.getWidth(), image.getHeight()); 
			
			int width = image.getWidth(); 
			int height = image.getHeight(); 
			
			// get ARGB pixels of image
			int[] data = new int[width * height]; 
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth()); 
			
			// set pixels of texture
			tex.setPixelsArgb(data);
			
			return tex; 
		} catch (Exception e) {
			log.warning("Could not find image at location \"" + filename + "\", returning default image");
			return createDefaultTexture(); 
		} 
	}
	
	// create a new Texture2D each time since the contents are mutable
	private Texture2D createDefaultTexture() {
		Texture2D tex = gd.createTexture2D(Texture.Format.RGBA8, 1, 1); 
		tex.setPixelsRgba(new int[] { 0xFF00FFFF });
		return tex; 
	}
	
}
