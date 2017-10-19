package oasis.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import oasis.core.GameLogger;

/**
 * File loading and saving utilities 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class FileUtil {

    private static final GameLogger log = new GameLogger(FileUtil.class); 
    
    /**
     * Get the data of a text file 
     * 
     * @param path
     * @return
     */
    public static String readTextFile(String path) {
        File file = new File(path); 
        
        if (file.exists() && file.isFile()) {
            try {
                InputStream is = new FileInputStream(file);
            
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                        
                String line = buf.readLine();
                StringBuilder sb = new StringBuilder();
                        
                while(line != null){
                   sb.append(line).append("\n");
                   line = buf.readLine();
                }
                
                buf.close(); 
                
                return sb.toString(); 
            
            } catch (FileNotFoundException e) {
                log.warning("Could not find file " + path);
                return null; 
            } catch (IOException e) {
                log.warning("Error reading file " + path);
                return null; 
            }
        }
        else {
            log.warning("Could not find file " + path);
            return null; 
        }
    }
    
}
