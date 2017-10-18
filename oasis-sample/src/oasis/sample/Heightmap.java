package oasis.sample;

import java.util.ArrayList;
import java.util.Arrays;

import oasis.graphics.ColorRgba;
import oasis.graphics.model.MeshData;
import oasis.graphics.model.Vertex;
import oasis.math.Mathf;
import oasis.math.Vector3f;
import oasis.math.Vector4f;
import oasis.util.ArrayUtil;
import oasis.util.QuickHash;

public class Heightmap {

    private boolean flat = false;
    private ArrayList<Vertex> verts = new ArrayList<>();
    private ArrayList<Integer> inds = new ArrayList<>(); 
    
    private int seed; 
    
    public Heightmap() {
        seed = (int) System.nanoTime(); 
    }
    
    public void setFlat(boolean flat) {
        this.flat = flat;
    }
    
    public MeshData genMeshData(Vector3f min, Vector3f max, int width, int height, int octaves, float initialFreq, float pers) {
        verts.clear(); 
        inds.clear();
        
        for (int y = 0; y < height + 1; y++) {
            for (int x = 0; x < width + 1; x++) {
                verts.add(getVertex(x, y, min, max, width, height, octaves, initialFreq, pers));
            }
        }
        
        int bl, br, tl, tr; 
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bl = getIndex(x, y, width); 
                br = getIndex(x + 1, y, width); 
                tl = getIndex(x, y + 1, width); 
                tr = getIndex(x + 1, y + 1, width); 
                
                inds.addAll(Arrays.asList(new Integer[] {
                        bl, br, tr, 
                        bl, tr, tl 
                })); 
            }
        }
        
        Vertex[] array = verts.toArray(new Vertex[verts.size()]); 
        int[] indices = ArrayUtil.toIntArray(inds.toArray(new Integer[inds.size()])); 
        Vertex.calculateNormals(array, indices);
        return Vertex.createMeshData(array, indices); 
    }
    
    private static int getIndex(int x, int y, int width) { 
        return x + y * (width + 1); 
    }
    
    private Vertex getVertex(float u, float v, Vector3f min, Vector3f max, int width, int height, int octaves, float initialFreq, float pers) {
        float uFrac = (float) u / width;
        float vFrac = (float) v / height;
        
        float x = Mathf.lerp(min.getX(), max.getX(), uFrac);
        float z = Mathf.lerp(max.getZ(), min.getZ(), vFrac);
        
        float f = fractal(x, z, octaves, initialFreq, pers, seed); 
        float y = Mathf.lerp(min.getY(), max.getY(), f);
        
        Vertex vert = new Vertex(); 
        vert.position = new Vector3f(x, y, z);
        
        if (flat) {
            vert.color = new ColorRgba(0.45f, 0.55f, 0.85f, 0.8f).toVector4();
        }
        else {
            vert.color = getTerrainColor(f); //new ColorRgba(0.50f, f * 0.15f + 0.45f, 0.45f, 1.0f).toVector4();
        }
        
        vert.normal = new Vector3f(0, 1, 0); 
        
        return vert; 
    }
    
    private static Vector4f getTerrainColor(float height) {
        if (height > 0.89f) {
            return new Vector4f(0.9f, 0.9f, 0.9f, 1.0f);
        }
        else if (height > 0.8f) {
            return new Vector4f(0.6f, 0.6f, 0.6f, 1.0f);
        }
        else if (height > 0.68f) {
            return new Vector4f(0.50f, height * 0.15f + 0.45f, 0.45f, 1.0f);
        }
        else if (height > 0.63f) {
            return new Vector4f(0.85f, 0.79f, 0.75f, 1.0f); 
        }
        else {
            return new Vector4f(0.55f, 0.60f, 0.55f, 1f); 
        }
    }
    
    private float fractal(float x, float y, int it, float freq, float pers, int seed) {
        float amp = 1.0f;
        
        float sum = 0.0f;
        float total = 0.0f;
        
        for (int i = 0; i < it; i++) {
            float noise = amp * noise(x * freq + freq, y * freq + freq, seed + i); 
            sum += flat ? (noise * 0.5f + 0.5f) : Math.abs(noise);
            total += amp;
            
            amp *= pers;
            freq *= 2.0f;
        }
        
        return 1.0f - sum / total;
    }
    
    private static float noise(float u, float v, int seed) {
        int u0 = (int) Math.floor(u);
        int u1 = u0 + 1;
        float ua = u - u0;
        ua = 0.5f * (1 - Mathf.cos(ua * Mathf.PI));
        
        int v0 = (int) Math.floor(v);
        int v1 = v0 + 1;
        float va = v - v0;
        va = 0.5f * (1 - Mathf.cos(va * Mathf.PI));
        
        float v00 = noisei(u0, v0, seed);
        float v01 = noisei(u0, v1, seed);
        float v10 = noisei(u1, v0, seed);
        float v11 = noisei(u1, v1, seed);
        
        float i0 = Mathf.lerp(v00, v10, ua);
        float i1 = Mathf.lerp(v01, v11, ua);
        
        return Mathf.lerp(i0, i1, va);
    }
    
    private static float noisei(int x, int y, int seed) {
        return (float) Math.abs(QuickHash.compute(x, y, seed)) / Integer.MAX_VALUE * 2.0f - 1.0f;
    }
    
}
