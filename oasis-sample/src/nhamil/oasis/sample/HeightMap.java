package nhamil.oasis.sample;

import java.util.ArrayList;
import java.util.Random;

import nhamil.oasis.graphics.ColorRgba;
import nhamil.oasis.graphics.Mesh;
import nhamil.oasis.graphics.Vertex;
import nhamil.oasis.math.FastMath;
import nhamil.oasis.math.Vector3;
import nhamil.oasis.util.QuickHash;

public class HeightMap {

    private int width, height;
    private boolean flat = false;
    private float yVal = 0.5f;
    
    public HeightMap(int uSamples, int vSamples) {
        width = uSamples;
        height = vSamples;
        generate(new Random());
    }
    
    public void setFlat(boolean flat, float yVal) {
        this.flat = flat;
        this.yVal = yVal;
    }
    
    public void generate(Random r) {
    }
    
    public void setMesh(Mesh mesh, Vector3 min, Vector3 max) {
        ArrayList<Vertex> verts = new ArrayList<>();
        
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
        
        mesh.setVertices(verts.toArray(new Vertex[verts.size()]));
    }
    
    private Vertex getVertex(int u, int v, Vector3 min, Vector3 max) {
        float uFrac = (float) u / width;
        float vFrac = (float) v / height;
        
        float x = FastMath.lerp(min.getX(), max.getX(), uFrac);
        float z = FastMath.lerp(min.getZ(), max.getZ(), vFrac);
        float y = FastMath.lerp(min.getY(), max.getY(), flat ? yVal : fractal(x, z, 5, 0.65f));
        
        Vertex vert = new Vertex().setPosition(new Vector3(x, y, z));
        
        if (flat) {
            return vert.setColor(new ColorRgba(0.5f, 0.5f, 1.5f, 1.0f));
        }
        else {
            return vert.setColor(new ColorRgba(0.5f, y, 0.5f, 1.0f));
        }
    }
    
    private float fractal(float x, float y, int it, float pers) {
        float freq = 1.0f;
        float amp = 1.0f;
        
        float sum = 0.0f;
        float total = 0.0f;
        
        for (int i = 0; i < it; i++) {
            sum += amp * noise(x * freq + freq, y * freq + freq);
            total += amp;
            
            amp *= pers;
            freq *= 2;
        }
        
        return sum / total;
    }
    
    private float noise(float u, float v) {
        int u0 = (int) Math.floor(u);
        int u1 = u0 + 1;
        float ua = u - u0;
        ua = 0.5f * (1 - FastMath.cos(FastMath.toDegrees(ua * FastMath.PI)));
        
        int v0 = (int) Math.floor(v);
        int v1 = v0 + 1;
        float va = v - v0;
        va = 0.5f * (1 - FastMath.cos(FastMath.toDegrees(va * FastMath.PI)));
        
        float v00 = noisei(u0, v0);
        float v01 = noisei(u0, v1);
        float v10 = noisei(u1, v0);
        float v11 = noisei(u1, v1);
        
        float i0 = FastMath.lerp(v00, v10, ua);
        float i1 = FastMath.lerp(v01, v11, ua);
        
        return FastMath.lerp(i0, i1, va);
    }
    
    private float noisei(int x, int y) {
        return (float) Math.abs(QuickHash.compute(x, y)) / Integer.MAX_VALUE;
    }
    
}
