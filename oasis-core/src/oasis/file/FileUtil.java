package oasis.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
        return readTextFile(path, PathList.DEFAULT); 
    }
    
    /**
     * Get the data of a text file 
     * 
     * @param path
     * @return
     */
    public static String[] readTextFileLines(String path) {
        return readTextFileLines(path, PathList.DEFAULT); 
    }
    
    /**
     * Get the data of a text file 
     * 
     * @param path
     * @return
     */
    public static String readTextFile(String path, PathList pathList) {
        File file = new File(pathList.find(path)); 
        
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
    
    /**
     * Get the data of a text file 
     * 
     * @param path
     * @return
     */
    public static String[] readTextFileLines(String path, PathList pathList) {
        File file = new File(pathList.find(path)); 
        List<String> lines = new ArrayList<>(); 
        
        if (file.exists() && file.isFile()) {
            try {
                InputStream is = new FileInputStream(file);
            
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                        
                String line = buf.readLine();
                        
                while(line != null){
                   lines.add(line); 
                   line = buf.readLine();
                }
                
                buf.close(); 
            } catch (FileNotFoundException e) {
                log.warning("Could not find file " + path);
            } catch (IOException e) {
                log.warning("Error reading file " + path);
            }
        }
        else {
            log.warning("Could not find file " + path);
        }
        
        return lines.toArray(new String[lines.size()]);
    }
    
}
