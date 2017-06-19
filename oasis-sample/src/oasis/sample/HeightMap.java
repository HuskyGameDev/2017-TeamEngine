package oasis.sample;

import java.util.ArrayList;

import oasis.graphics.ColorRgba;
import oasis.graphics.vertex.BasicVertex;
import oasis.math.FastMath;
import oasis.math.Vector3;
import oasis.util.QuickHash;

public class HeightMap {

    private int width, height;
    private boolean flat = false;
    private float yVal = 0.5f;
    
    private int seed = (int)System.nanoTime(); 
    
    public HeightMap(int uSamples, int vSamples) {
        width = uSamples;
        height = vSamples;
    }
    
    public void setFlat(boolean flat, float yVal) {
        this.flat = flat;
        this.yVal = yVal;
    }
    
    public BasicVertex[] genVertices(Vector3 min, Vector3 max) {
        ArrayList<BasicVertex> verts = new ArrayList<>();
        
        for (int x = 0; x < width - 0; x++) {
            for (int y = 0; y < height - 0; y++) {
                verts.add(getVertex(x, y, min, max));
                verts.add(getVertex(x + 1, y + 1, min, max));
                verts.add(getVertex(x + 1, y, min, max));
                
                verts.add(getVertex(x, y, min, max));
                verts.add(getVertex(x, y + 1, min, max));
                verts.add(getVertex(x + 1, y + 1, min, max));
            }
        }
        
        BasicVertex[] array = verts.toArray(new BasicVertex[verts.size()]); 
        BasicVertex.calculateNormals(array);
        return array; 
    }
    
    private BasicVertex getVertex(int u, int v, Vector3 min, Vector3 max) {
        float uFrac = (float) u / width;
        float vFrac = (float) v / height;
        
        float x = FastMath.lerp(min.getX(), max.getX(), uFrac);
        float z = FastMath.lerp(min.getZ(), max.getZ(), vFrac);
        float y = FastMath.lerp(min.getY(), max.getY(), flat ? yVal : fractal(x, z, 15, 0.6f));
        
        BasicVertex vert = new BasicVertex(); 
        vert.position = new Vector3(x, y, z);
        
        if (flat) {
            vert.color = new ColorRgba(0.35f, 0.35f, 0.9f, 1.0f).toVector4();
        }
        else {
            vert.color = new ColorRgba(0.5f, fractal(x, z, 5, 0.65f), 0.5f, 1.0f).toVector4();
        }
        
        vert.normal = new Vector3(0, 1, 0); 
        
        return vert; 
    }
    
    private float fractal(float x, float y, int it, float pers) {
        float freq = 0.1f;
        float amp = 1.0f;
        
        float sum = 0.0f;
        float total = 0.0f;
        
        for (int i = 0; i < it; i++) {
            sum += amp * noise(x * freq + freq, y * freq + freq, seed + i);
            total += amp;
            
            amp *= pers;
            freq *= 2;
        }
        
        return sum / total;
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
        return (float) Math.abs(QuickHash.compute(x, y, seed)) / Integer.MAX_VALUE;
    }
    
}
