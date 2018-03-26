package oasis.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import oasis.core.Oasis;

public class PathList {

    public static final PathList DEFAULT = new PathList(); 
    
    static {
        DEFAULT.add(".");
        DEFAULT.add(Oasis.DEFAULT_ASSET_FOLDER);
        DEFAULT.add(Oasis.DEFAULT_SHADER_FOLDER);
        DEFAULT.add(Oasis.DEFAULT_SOUND_FOLDER);
        DEFAULT.add(Oasis.DEFAULT_TEXTURE_FOLDER);
    }
    
    private List<String> paths = new ArrayList<>(); 
    
    public PathList() {
        
    }
    
    public synchronized void addList(PathList list) {
        paths.addAll(list.paths); 
    }
    
    public synchronized void add(String path) {
        paths.add(path); 
    }
    
    public synchronized void remove(String path) {
        paths.remove(path); 
    }
    
    public synchronized void remove(int index) {
        paths.remove(index); 
    }
    
    public synchronized int getPathCount() {
        return paths.size(); 
    }
    
    public synchronized String getPath(int index) {
        return paths.get(index); 
    }
    
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder(); 
        
        sb.append("PathList: \n"); 
        for (String path : paths) {
            sb.append(path + "\n"); 
        }
        
        return sb.toString(); 
    }
    
    public synchronized String find(String tryPath) {
        File file = new File(tryPath); 
        
        if (file.exists() && file.isFile()) return file.getAbsolutePath(); 
        
        for (String path : paths) {
            file = new File(path + "/" + tryPath); 
            if (file.exists() && file.isFile()) return file.getAbsolutePath(); 
        }
        
        return null; 
    }
    
}
