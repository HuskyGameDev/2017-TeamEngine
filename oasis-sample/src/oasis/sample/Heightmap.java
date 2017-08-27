package oasis.sample;

import java.util.ArrayList;
import java.util.Arrays;

import oasis.graphics.ColorRgba;
import oasis.graphics.model.MeshData;
import oasis.graphics.model.Vertex;
import oasis.math.MathUtil;
import oasis.math.Vector3;
import oasis.util.ArrayUtil;
import oasis.util.QuickHash;

public class Heightmap {

    private boolean flat = false;
    private float yVal = 0.5f;
    private ArrayList<Vertex> verts = new ArrayList<>();
    private ArrayList<Integer> inds = new ArrayList<>(); 
    
    private int seed; 
    
    public Heightmap() {
        seed = (int) System.nanoTime(); 
    }
    
    public void setFlat(boolean flat, float yVal) {
        this.flat = flat;
        this.yVal = yVal;
    }
    
    public MeshData genMeshData(Vector3 min, Vector3 max, int width, int height, int octaves, float initialFreq, float pers) {
        verts.clear(); 
        inds.clear();
        
        for (int y = 0; y < height + 1; y++) {
            for (int x = 0; x < width + 1; x++) {
                verts.add(getVertex(x, y, min, max, width, height, octaves, initialFreq, pers));
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                verts.add(getVertexMid(x, y, min, max, width, height, octaves, initialFreq, pers));
            }
        }
        
        int mainSize = (width + 1) * (height + 1); 
        
        int bl, br, tl, tr, mid; 
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bl = getIndex(x, y, width); 
                br = getIndex(x + 1, y, width); 
                tl = getIndex(x, y + 1, width); 
                tr = getIndex(x + 1, y + 1, width); 
                mid = getIndex(mainSize + x, y, width - 1); 
                
                inds.addAll(Arrays.asList(new Integer[] {
                        bl, mid, tl, 
                        tl, mid, tr, 
                        tr, mid, br, 
                        br, mid, bl
                })); 
                
//                if (i % 2 == 0) { 
//                    inds.add(getIndex(x, y, width)); 
//                    inds.add(getIndex(x + 1, y + 1, width)); 
//                    inds.add(getIndex(x, y + 1, width)); 
//                    inds.add(getIndex(x, y, width)); 
//                    inds.add(getIndex(x + 1, y, width)); 
//                    inds.add(getIndex(x + 1, y + 1, width)); 
//                }
//                else { 
//                    inds.add(getIndex(x + 1, y, width)); 
//                    inds.add(getIndex(x + 1, y + 1, width)); 
//                    inds.add(getIndex(x, y + 1, width)); 
//                    inds.add(getIndex(x + 1, y, width)); 
//                    inds.add(getIndex(x, y + 1, width)); 
//                    inds.add(getIndex(x, y, width)); 
//                }
            }
        }
        
        Vertex[] array = verts.toArray(new Vertex[verts.size()]); 
        int[] indices = ArrayUtil.toIntArray(inds.toArray(new Integer[inds.size()])); 
        Vertex.calculateNormals(array, indices);
        return Vertex.createMeshData(array, indices); 
    }
    
//    public MeshData genMeshData(Vector3 min, Vector3 max, int width, int height, int octaves, float initialFreq, float pers) {
//        verts.clear(); 
//        inds.clear();
//        
//        for (int y = 0; y < height + 1; y++) {
//            for (int x = 0; x < width + 1; x++) {
//                verts.add(getVertex(x, y, min, max, width, height, octaves, initialFreq, pers));
//            }
//        }
//        
//        int i = 0; 
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                i++; 
//                
//                if (i % 2 == 0) { 
//                    inds.add(getIndex(x, y, width)); 
//                    inds.add(getIndex(x + 1, y + 1, width)); 
//                    inds.add(getIndex(x, y + 1, width)); 
//                    inds.add(getIndex(x, y, width)); 
//                    inds.add(getIndex(x + 1, y, width)); 
//                    inds.add(getIndex(x + 1, y + 1, width)); 
//                }
//                else { 
//                    inds.add(getIndex(x + 1, y, width)); 
//                    inds.add(getIndex(x + 1, y + 1, width)); 
//                    inds.add(getIndex(x, y + 1, width)); 
//                    inds.add(getIndex(x + 1, y, width)); 
//                    inds.add(getIndex(x, y + 1, width)); 
//                    inds.add(getIndex(x, y, width)); 
//                }
//            }
//        }
//        
//        Vertex[] array = verts.toArray(new Vertex[verts.size()]); 
//        int[] indices = ArrayUtil.toIntArray(inds.toArray(new Integer[inds.size()])); 
//        Vertex.calculateNormals(array, indices);
//        return Vertex.createMeshData(array, indices); 
//    }
    
    private int getIndex(int x, int y, int width) { 
        return x + y * (width + 1); 
    }
    
    private Vertex getVertex(float u, float v, Vector3 min, Vector3 max, int width, int height, int octaves, float initialFreq, float pers) {
        float uFrac = (float) u / width;
        float vFrac = (float) v / height;
        
        float x = MathUtil.lerp(min.getX(), max.getX(), uFrac);
        float z = MathUtil.lerp(min.getZ(), max.getZ(), vFrac);
        
        float f = fractal(x, z, octaves, initialFreq, pers); 
        float y = MathUtil.lerp(min.getY(), max.getY(), flat ? yVal : f);
        
        Vertex vert = new Vertex(); 
        vert.position = new Vector3(x, y, z);
        
        if (flat) {
            vert.color = new ColorRgba(0.45f, 0.55f, 0.85f, 0.95f).toVector4();
        }
        else {
            vert.color = new ColorRgba(0.50f, f * 0.15f + 0.45f, 0.45f, 1.0f).toVector4();
        }
        
        vert.normal = new Vector3(0, 1, 0); 
        
        return vert; 
    }
    
    private Vertex getVertexMid(float u, float v, Vector3 min, Vector3 max, int width, int height, int octaves, float initialFreq, float pers) {
        float uFrac = (float) u / width;
        float vFrac = (float) v / height;
        
        float x0 = MathUtil.lerp(min.getX(), max.getX(), uFrac);
        float z0 = MathUtil.lerp(min.getZ(), max.getZ(), vFrac);
        float x1 = MathUtil.lerp(min.getX(), max.getX(), uFrac + 1f / width);
        float z1 = MathUtil.lerp(min.getZ(), max.getZ(), vFrac + 1f / width);
        
        float f00 = fractal(x0, z0, octaves, initialFreq, pers); 
        float f10 = fractal(x1, z0, octaves, initialFreq, pers); 
        float f01 = fractal(x0, z1, octaves, initialFreq, pers); 
        float f11 = fractal(x1, z1, octaves, initialFreq, pers); 
        float f = MathUtil.lerp(
                MathUtil.lerp(f00, f10, 0.5f), 
                MathUtil.lerp(f01, f11, 0.5f), 
                0.5f); 
        float y = MathUtil.lerp(min.getY(), max.getY(), flat ? yVal : f);
        
        Vertex vert = new Vertex(); 
        vert.position = new Vector3(0.5f * (x0 + x1), y, 0.5f * (z0 + z1));
        
        if (flat) {
            vert.color = new ColorRgba(0.45f, 0.55f, 0.85f, 0.95f).toVector4();
        }
        else {
            vert.color = new ColorRgba(0.50f, f * 0.15f + 0.45f, 0.45f, 1.0f).toVector4();
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
            freq *= 2.0f;
        }
        
        return 1.0f - sum / total;
    }
    
    private float noise(float u, float v, int seed) {
        int u0 = (int) Math.floor(u);
        int u1 = u0 + 1;
        float ua = u - u0;
        ua = 0.5f * (1 - MathUtil.cos(MathUtil.toDegrees(ua * MathUtil.PI)));
        
        int v0 = (int) Math.floor(v);
        int v1 = v0 + 1;
        float va = v - v0;
        va = 0.5f * (1 - MathUtil.cos(MathUtil.toDegrees(va * MathUtil.PI)));
        
        float v00 = noisei(u0, v0, seed);
        float v01 = noisei(u0, v1, seed);
        float v10 = noisei(u1, v0, seed);
        float v11 = noisei(u1, v1, seed);
        
        float i0 = MathUtil.lerp(v00, v10, ua);
        float i1 = MathUtil.lerp(v01, v11, ua);
        
        return MathUtil.lerp(i0, i1, va);
    }
    
    private float noisei(int x, int y, int seed) {
        return (float) Math.abs(QuickHash.compute(x, y, seed)) / Integer.MAX_VALUE * 2.0f - 1.0f;
    }
    
}
