package oasis.terrainapp;

import oasis.graphics.Mesh;
import oasis.math.Mathf;
import oasis.math.Vector2;
import oasis.math.Vector3;

public class TerrainGenerator {

    public static final float SIZE = 64.0f; 
    public static final float HALF_SIZE = SIZE * 0.5f; 
    public static final int ITERATIONS = 10; 
    public static final float PERSISTANCE = 0.45f; 
    public static final float LACUNARITY = 2.0f; 
    public static final float SCALE = 500f; 
    public static final float INV_SCALE = 1.0f / SCALE; 
    
    public static Mesh generate(float x, float y, int res) {
        Mesh mesh = Mesh.create(); 
        
        Vector3[] positions = new Vector3[res * res]; 
        Vector3[] normals = new Vector3[res * res]; 
        Vector2[] texCoords = new Vector2[res * res]; 
        short[] indices = new short[(res - 1) * (res - 1) * 6]; 
        
        Vector3 a; 
        Vector3 b = new Vector3(); 
        Vector3 c = new Vector3(); 
        
        final float d = 1f / (res - 1); 
        
        int index = 0; 
        for (int yy = 0; yy < res; yy++) {
            float yAmt = (float) yy / (res - 1); 
            
            for (int xx = 0; xx < res; xx++) {
                float xAmt = (float) xx / (res - 1); 
                
                float xRelPos = SIZE * xAmt - HALF_SIZE; 
                float yRelPos = SIZE * yAmt - HALF_SIZE; 
                
                float xRelPos2 = SIZE * (xAmt + d) - HALF_SIZE; 
                float yRelPos2 = SIZE * (yAmt + d) - HALF_SIZE; 
                
                texCoords[index] = new Vector2((xAmt * SCALE * 0.02f), (yAmt * SCALE * 0.02f)); 
                positions[index] = new Vector3(
                        xRelPos, 
                        heightAtPosition(x * SIZE + xRelPos, y * SIZE + yRelPos), 
                        yRelPos); 
                
                a = positions[index]; 
                b.set(xRelPos2, heightAtPosition(x * SIZE + xRelPos2, y * SIZE + yRelPos), yRelPos); 
                c.set(xRelPos, heightAtPosition(x * SIZE + xRelPos, y * SIZE + yRelPos2), yRelPos2); 
                
                b.subtractSelf(a).normalizeSelf();
                c.subtractSelf(a).normalizeSelf(); 
                
                c.crossSelf(b).normalizeSelf(); 
                
                normals[index++] = new Vector3(c); 
            }
        }
        
        index = 0; 
        for (int yy = 0; yy < res - 1; yy++) {
            for (int xx = 0; xx < res - 1; xx++) {
                indices[index++] = (short) ((xx + 0) + res * (yy + 0)); 
                indices[index++] = (short) ((xx + 1) + res * (yy + 1)); 
                indices[index++] = (short) ((xx + 1) + res * (yy + 0)); 
                
                indices[index++] = (short) ((xx + 0) + res * (yy + 0)); 
                indices[index++] = (short) ((xx + 0) + res * (yy + 1)); 
                indices[index++] = (short) ((xx + 1) + res * (yy + 1)); 
            }
        }
        
        mesh.setPositions(positions);
        mesh.setTexCoords(texCoords); 
        mesh.setIndices(0, indices); 
        mesh.setNormals(normals); 
        mesh.calculateTangents(); 
        mesh.upload(); 
        return mesh; 
    }
    
    public static float heightAtPosition(float x, float y) {
        float sum = 0; 
        float sumAmp = 0;
        
        float freq = INV_SCALE; 
        float amp = 1.0f; 
        int seed = 1000; 
        
        for (int i = 0; i < ITERATIONS; i++) {
            sumAmp += amp; 
            sum += amp * Mathf.valueNoise2D(x * freq, y * freq, seed); 
            
            freq *= LACUNARITY; 
            amp *= PERSISTANCE; 
            seed++; 
        }
        
        return sum / sumAmp * SCALE * 0.75f; 
    }
    
}
