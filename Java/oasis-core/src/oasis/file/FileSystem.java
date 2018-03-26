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

import oasis.core.Logger;
import oasis.core.Oasis;

public class FileSystem {

    private static final Logger log = new Logger(FileSystem.class); 
    
    private PathList pathList; 
    
    public FileSystem() {
        pathList = new PathList(); 
        pathList.add(".");
        pathList.add(Oasis.DEFAULT_ASSET_FOLDER);
        pathList.add(Oasis.DEFAULT_SHADER_FOLDER);
        pathList.add(Oasis.DEFAULT_SOUND_FOLDER);
        pathList.add(Oasis.DEFAULT_TEXTURE_FOLDER);
        pathList.add(Oasis.DEFAULT_MODEL_FOLDER);
        pathList.add(FileSystem.class.getResource("/").getFile()); 
    }
    
    public FileSystem(PathList list) {
        pathList = list; 
    }
    
    public PathList getPathList() {
        return pathList; 
    }
    
    public void addPath(String path) {
        pathList.add(path); 
    }
    
    public void removePath(String path) {
        pathList.remove(path); 
    }
    
    public String find(String path) {
        return pathList.find(path); 
    }
    
    /**
     * Get the data of a text file 
     * 
     * @param path
     * @return
     */
    public String readTextFile(String path) {
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
    public String[] readTextFileLines(String path) {
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
