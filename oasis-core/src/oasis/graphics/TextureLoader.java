package oasis.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import oasis.core.GameLogger;

public class TextureLoader {

	private static final GameLogger log = new GameLogger(TextureLoader.class); 
	
	private GraphicsDevice gd;
	private Map<String, Texture2D> loaded; 
	
	public TextureLoader(GraphicsDevice gd) {
		this.gd = gd; 
		this.loaded = new HashMap<>(); 
	}
	
	public Texture2D get(String filename) {
		Texture2D tex = loaded.get(filename); 
		if (tex == null || tex.isDisposed()) {
			tex = load(filename); 
			loaded.put(filename, tex); 
		}
		
		return tex; 
	}
	
	private Texture2D load(String filename) {
		try {
			BufferedImage image = ImageIO.read(new File(filename));
			Texture2D tex = gd.createTexture2D(Texture.Format.RGBA8, image.getWidth(), image.getHeight()); 
			
			int width = image.getWidth(); 
			int height = image.getHeight(); 
			
			// get ARGB pixels of image
			int[] data = new int[width * height]; 
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth()); 
			
			// convert ARGB to RGBA
			for (int i = 0; i < data.length / 2; i++) {
				// reverse y
				int j = i % width + (height - 1 - i / width) * width; 
				// swap values
				data[i] ^= data[j]; 
				data[j] ^= data[i]; 
				data[i] ^= data[j]; 
			}
			
			// set pixels of texture
			tex.setPixelsArgb(data);
			
			return tex; 
		} catch (Exception e) {
			log.warning("Could not find image at location \"" + filename + "\", returning defualt image");
			return createDefaultTexture(); 
		} 
	}
	
	private Texture2D createDefaultTexture() {
		Texture2D tex = gd.createTexture2D(Texture.Format.RGBA8, 1, 1); 
		tex.setPixelsRgba(new int[] { 0xFF00FFFF });
		return tex; 
	}
	
}
