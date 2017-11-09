package oasis.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oasis.core.GameLogger;
import oasis.core.Oasis;
import oasis.graphics.model.Mesh;
import oasis.graphics.model.Vertex;
import oasis.math.Vector2f;
import oasis.math.Vector3f;
import oasis.util.ArrayUtil;

public class ObjImporter {

    private static final GameLogger log = new GameLogger(ObjImporter.class); 
    
    private ObjImporter() {} 
    
    private static int getVertexIndex(
            List<Vector3f> positions, 
            List<Vector3f> normals, 
            List<Vector2f> texCoords, 
            List<Vertex> verts, 
            Map<String, Integer> map, 
            String key) {
        Integer val = map.get(key); 
        
        if (val != null) return val.intValue(); 
        
        String[] elems = key.split("/"); 
        Vertex v = new Vertex(); 
        
        v.position = positions.get(Integer.parseInt(elems[0]) - 1); 
        if (elems.length > 1 && !elems[1].equals("")) {
            v.texCoord0 = texCoords.get(Integer.parseInt(elems[1]) - 1); 
        }
        if (elems.length > 2 && !elems[2].equals("")) {
            v.normal = normals.get(Integer.parseInt(elems[2]) - 1); 
        }
        
        verts.add(v); 
        map.put(key, verts.size() - 1); 
        
        return verts.size() - 1; 
    }
    
    public static Mesh load(String file) {
        String path = Oasis.files.getPathList().find(file); 
        
        if (path == null) {
            log.warning("Could not find obj file: " + file);
            return null; 
        }
        
        List<Vector3f> positions = new ArrayList<>(); 
        List<Vector3f> normals = new ArrayList<>(); 
        List<Vector2f> texCoords = new ArrayList<>(); 
        List<Integer> inds = new ArrayList<>(); 
        List<Vertex> verts = new ArrayList<>(); 
        
        Map<String, Integer> vertMap = new HashMap<>(); 
        
        String[] lines = Oasis.files.readTextFileLines(path); 
        
        try {
            for (String s : lines) {
                String words[] = s.split("\\s+"); 
                
                if (words.length == 0) continue; 
                
                if (words[0].equals("v")) {
                    positions.add(new Vector3f(
                            Float.parseFloat(words[1]),
                            Float.parseFloat(words[2]), 
                            Float.parseFloat(words[3])
                    )); 
                }
                else if (words[0].equals("vn")) {
                    normals.add(new Vector3f(
                            Float.parseFloat(words[1]),
                            Float.parseFloat(words[2]), 
                            Float.parseFloat(words[3])
                    )); 
                }
                else if (words[0].equals("vt")) {
                    texCoords.add(new Vector2f(
                            Float.parseFloat(words[1]),
                            Float.parseFloat(words[2])
                    )); 
                }
                else if (words[0].equals("f")) {
                    for (int i = 0; i < words.length - 3; i++) {
                        inds.add(getVertexIndex(
                                positions, 
                                normals, 
                                texCoords, 
                                verts, 
                                vertMap, 
                                words[1]
                        )); 
                        inds.add(getVertexIndex(
                                positions, 
                                normals, 
                                texCoords, 
                                verts, 
                                vertMap, 
                                words[i + 2]
                        )); 
                        inds.add(getVertexIndex(
                                positions, 
                                normals, 
                                texCoords, 
                                verts, 
                                vertMap, 
                                words[i + 3]
                        )); 
                    }
                }
            }
            
            Vertex[] vertArray = verts.toArray(new Vertex[verts.size()]); 
            int[] indArray = ArrayUtil.toIntArray(inds.toArray(new Integer[inds.size()])); 
            
            Mesh mesh = new Mesh(); 
            if (normals.isEmpty()) Vertex.calculateNormals(vertArray, indArray);
            Vertex.setMesh(vertArray, indArray, mesh); 
            
            return mesh; 
        }
        catch (Exception e) {
            log.warning("Problem parsing obj file: " + file + "\n" + e.getMessage());
            throw e; 
//            return null; 
        }
    } 
    
}
