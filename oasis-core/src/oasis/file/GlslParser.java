package oasis.file;

import java.net.URLDecoder;

import oasis.core.GameLogger;
import oasis.core.Oasis;

@SuppressWarnings("deprecation")
public class GlslParser {

    private static final GameLogger log = new GameLogger(GlslParser.class); 
    
    public static final String ANY_MODE = "#anyshader"; 
    public static final String VERTEX_MODE = "#vertexshader";
    public static final String FRAGMENT_MODE = "#fragmentshader"; 
    
    private static final PathList INTERNAL_PATHS = new PathList(); 
    
    private GlslParser() {} 
    
    private enum Mode {
        ANY, VERTEX, FRAGMENT
    }; 
    
    static {
        INTERNAL_PATHS.add(URLDecoder.decode(GlslParser.class.getResource("/shaders/").getFile()));
    }
    
    public static String getVertexSource(String file, PathList list) {
        return genericParse(file, list, Mode.VERTEX); 
    }
    
    public static String getFragmentSource(String file, PathList list) {
        return genericParse(file, list, Mode.FRAGMENT); 
    }
    
    private static String genericParse(String file, PathList list, Mode mode) {
        String path = list.find(file); 
        if (path == null) {
            path = INTERNAL_PATHS.find(file); 
        }
        if (path == null) {
            log.warning("Could not find GLSL file: " + file);
            return null; 
        }
        String[] lines = Oasis.files.readTextFileLines(path); 
        StringBuilder sb = new StringBuilder(); 
        Mode curMode = mode; 
        
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().startsWith("#include")) {
                if (curMode == mode) {
                    String[] words = lines[i].trim().split(" "); 
                    if (words.length >= 2) {
                        String include = words[1]; 
                        if (include.startsWith("\"") && include.endsWith("\"") || include.startsWith("<") && include.endsWith(">")) {
                            String includeSource = genericParse(include.substring(1, include.length() - 1), list, mode); 
                            if (includeSource == null) {
                                log.warning("Could not load include from " + include); 
                            }
                            else {
                                sb.append(includeSource); 
                            }
                        }
                    }
                }
            }
            else if (lines[i].trim().equals(ANY_MODE)) {
                curMode = mode; 
            }
            else if (lines[i].trim().equals(VERTEX_MODE)) {
                curMode = Mode.VERTEX; 
            }
            else if (lines[i].trim().equals(FRAGMENT_MODE)) {
                curMode = Mode.FRAGMENT; 
            }
            else {
                if (curMode == mode) {
                    sb.append(lines[i]).append("\n"); 
                }
            }
        }
        
        return sb.toString(); 
    }
    
}
