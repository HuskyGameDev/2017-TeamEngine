package oasis.core.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.core.ResourceLoader;
import oasis.graphics.Mesh;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.util.ArrayUtil;

public class ObjLoader implements ResourceLoader<Mesh> {

    private static final Logger log = new Logger(ObjLoader.class); 
    
    private static class Vertex {
        public Vector3 position; 
        public Vector3 normal; 
        public Vector2 texCoord; 
    }
    
    private void calculateNormals(Vertex[] verts, int[] inds) {
        for (int i = 0; i < verts.length; i++) { 
            verts[i].normal = new Vector3(); 
        }
        
        for (int i = 0; i < inds.length; i += 3) { 
            Vector3 a = verts[inds[i + 1]].position.subtract(verts[inds[i + 0]].position).normalize(); 
            Vector3 b = verts[inds[i + 2]].position.subtract(verts[inds[i + 0]].position).normalize(); 
            Vector3 normal = a.cross(b).normalize(); 
            verts[inds[i + 0]].normal.addSelf(normal); 
            verts[inds[i + 1]].normal.addSelf(normal); 
            verts[inds[i + 2]].normal.addSelf(normal); 
        }
        
        for (int i = 0; i < verts.length; i++) { 
            verts[i].normal.normalizeSelf(); 
}
    }
    
    private void applyToMesh(Vertex[] verts, int[] inds, Mesh mesh) {
        if (verts.length >= (1 << 16)) {
            throw new OasisException("Model has too many vertices! Try splitting the model into multiple meshes"); 
        }
        
        Vector3[] positions = new Vector3[verts.length]; 
        Vector3[] normals = new Vector3[verts.length]; 
        Vector2[] texCoords = new Vector2[verts.length]; 
        short[] indices = new short[inds.length]; 
        
        for (int i = 0; i < verts.length; i++) {
            positions[i] = verts[i].position; 
            normals[i] = verts[i].normal; 
            texCoords[i] = verts[i].texCoord; 
        }
        
        for (int i = 0; i < inds.length; i++) {
            indices[i] = (short) inds[i]; 
        }
        
        mesh.setPositions(positions);
        if (verts[0].normal != null) mesh.setNormals(normals); 
        if (verts[0].texCoord != null) mesh.setTexCoords(texCoords); 
        mesh.setIndices(0, indices);
        if (verts[0].texCoord != null && verts[0].normal != null) mesh.calculateTangents(); 
        mesh.upload(); 
    }
    
    private static int getVertexIndex(
            List<Vector3> positions, 
            List<Vector3> normals, 
            List<Vector2> texCoords, 
            List<Vertex> verts, 
            Map<String, Integer> map, 
            String key) {
        Integer val = map.get(key); 
        
        if (val != null) return val.intValue(); 
        
        String[] elems = key.split("/"); 
        Vertex v = new Vertex(); 
        
        v.position = positions.get(Integer.parseInt(elems[0]) - 1); 
        if (elems.length > 1 && !elems[1].equals("")) {
            v.texCoord = texCoords.get(Integer.parseInt(elems[1]) - 1); 
        }
        if (elems.length > 2 && !elems[2].equals("")) {
            v.normal = normals.get(Integer.parseInt(elems[2]) - 1); 
        }
        
        verts.add(v); 
        map.put(key, verts.size() - 1); 
        
        return verts.size() - 1; 
    }
    
    public Mesh load(String file) {
        String path = Oasis.getFileSystem().getPathList().find(file); 
        
        if (path == null) {
            log.warning("Could not find obj file: " + file);
            return null; 
        }
        
        List<Vector3> positions = new ArrayList<>(); 
        List<Vector3> normals = new ArrayList<>(); 
        List<Vector2> texCoords = new ArrayList<>(); 
        List<Integer> inds = new ArrayList<>(); 
        List<Vertex> verts = new ArrayList<>(); 
        
        Map<String, Integer> vertMap = new HashMap<>(); 
        
        String[] lines = Oasis.getFileSystem().readTextFileLines(path); 
        
        try {
            for (String s : lines) {
                String words[] = s.split("\\s+"); 
                
                if (words.length == 0) continue; 
                
                if (words[0].equals("v")) {
                    positions.add(new Vector3(
                            Float.parseFloat(words[1]),
                            Float.parseFloat(words[2]), 
                            Float.parseFloat(words[3])
                    )); 
                }
                else if (words[0].equals("vn")) {
                    normals.add(new Vector3(
                            Float.parseFloat(words[1]),
                            Float.parseFloat(words[2]), 
                            Float.parseFloat(words[3])
                    )); 
                }
                else if (words[0].equals("vt")) {
                    texCoords.add(new Vector2(
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
            
            Mesh mesh = Mesh.create(); 
            if (normals.isEmpty()) calculateNormals(vertArray, indArray);
            applyToMesh(vertArray, indArray, mesh); 
            
            return mesh; 
        }
        catch (Exception e) {
            log.warning("Problem parsing obj file: " + file + "\n" + e.getMessage());
            throw e; 
//            return null; 
        }
    } 
    
}