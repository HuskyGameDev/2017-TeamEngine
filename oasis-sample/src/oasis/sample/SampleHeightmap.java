package oasis.sample;

import java.util.ArrayList;

import oasis.graphics.ColorRgba;
import oasis.graphics.vertex.Mesh;
import oasis.graphics.vertex.Vertex;
import oasis.math.FastMath;
import oasis.math.Vector3;
import oasis.util.ArrayUtil;
import oasis.util.QuickHash;

public class SampleHeightmap {

    private boolean flat = false;
    private float yVal = 0.5f;
    private ArrayList<Vertex> verts = new ArrayList<>();
    private ArrayList<Integer> inds = new ArrayList<>(); 
    
    private int seed = (int) System.nanoTime(); 
    
    public SampleHeightmap() {
        
    }
    
    public void setFlat(boolean flat, float yVal) {
        this.flat = flat;
        this.yVal = yVal;
    }
    
    public Mesh genMesh(Mesh mesh, Vector3 min, Vector3 max, int width, int height, int octaves, float initialFreq, float pers) {
        verts.clear(); 
        inds.clear();
        
        for (int y = 0; y < height + 1; y++) {
            for (int x = 0; x < width + 1; x++) {
                verts.add(getVertex(x, y, min, max, width, height, octaves, initialFreq, pers));
            }
        }
        
        int i = 0; 
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                i++; 
                
                if (i % 2 == 0) { 
                    inds.add(getIndex(x, y, width)); 
                    inds.add(getIndex(x + 1, y + 1, width)); 
                    inds.add(getIndex(x, y + 1, width)); 
                    inds.add(getIndex(x, y, width)); 
                    inds.add(getIndex(x + 1, y, width)); 
                    inds.add(getIndex(x + 1, y + 1, width)); 
                }
                else { 
                    inds.add(getIndex(x + 1, y, width)); 
                    inds.add(getIndex(x + 1, y + 1, width)); 
                    inds.add(getIndex(x, y + 1, width)); 
                    inds.add(getIndex(x + 1, y, width)); 
                    inds.add(getIndex(x, y + 1, width)); 
                    inds.add(getIndex(x, y, width)); 
                }
            }
        }
        
        Vertex[] array = verts.toArray(new Vertex[verts.size()]); 
        int[] indices = ArrayUtil.toIntArray(inds.toArray(new Integer[inds.size()])); 
        Vertex.calculateNormals(array, indices);
        Vertex.setMeshData(array, indices, mesh);
        return mesh; 
    }
    
    private int getIndex(int x, int y, int width) { 
        return x + y * (width + 1); 
    }
    
    private Vertex getVertex(int u, int v, Vector3 min, Vector3 max, int width, int height, int octaves, float initialFreq, float pers) {
        float uFrac = (float) u / width;
        float vFrac = (float) v / height;
        
        float x = FastMath.lerp(min.getX(), max.getX(), uFrac);
        float z = FastMath.lerp(min.getZ(), max.getZ(), vFrac);
        
        float f = fractal(x, z, octaves, initialFreq, pers); 
        float y = FastMath.lerp(min.getY(), max.getY(), flat ? yVal : f);
        
        Vertex vert = new Vertex(); 
        vert.position = new Vector3(x, y, z);
        
        if (flat) {
            vert.color = new ColorRgba(0.35f, 0.35f, 0.9f, 1.0f).toVector4();
        }
        else {
            vert.color = new ColorRgba(0.5f, f * 0.25f + 0.5f, 0.5f, 1.0f).toVector4();
        }
        
        vert.normal = new Vector3(0, 1, 0); 
        
        return vert; 
    }
    
    private float fractal(float x, float y, int it, float freq, float pers) {
        float amp = 1.0f;
        
        float sum = 0.0f;
        float total = 0.0f;
        
        for (int i = 0; i < it; i++) {
            sum += Math.abs(amp * noise(x * freq + freq, y * freq + freq, seed + i));
            total += amp;
            
            amp *= pers;
            freq *= 2.15f;
        }
        
        return 1.0f - sum / total;
    }
    
    private float noise(float u, float v, int seed) {
        int u0 = (int) Math.floor(u);
        int u1 = u0 + 1;
        float ua = u - u0;
        ua = 0.5f * (1 - FastMath.cos(FastMath.toDegrees(ua * FastMath.PI)));
        
        int v0 = (int) Math.floor(v);
        int v1 = v0 + 1;
        float va = v - v0;
        va = 0.5f * (1 - FastMath.cos(FastMath.toDegrees(va * FastMath.PI)));
        
        float v00 = noisei(u0, v0, seed);
        float v01 = noisei(u0, v1, seed);
        float v10 = noisei(u1, v0, seed);
        float v11 = noisei(u1, v1, seed);
        
        float i0 = FastMath.lerp(v00, v10, ua);
        float i1 = FastMath.lerp(v01, v11, ua);
        
        return FastMath.lerp(i0, i1, va);
    }
    
    private float noisei(int x, int y, int seed) {
        return (float) Math.abs(QuickHash.compute(x, y, seed)) / Integer.MAX_VALUE * 2.0f - 1.0f;
    }
    
}
