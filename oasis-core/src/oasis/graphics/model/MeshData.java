package oasis.graphics.model;

import oasis.math.Vector3f;
import oasis.math.Vector4f;

/**
 * Holds mesh data. Useful for generating mesh data 
 * on a separate thread 
 * 
 * @author Nicholas Hamilton
 *
 */
public class MeshData {

    /**
     * Indices 
     */
    public int[] inds; 
    
    /**
     * Positions 
     */
    public Vector3f[] positions; 
    
    /**
     * Normals 
     */
    public Vector3f[] normals; 
    
    /**
     * Colors 
     */
    public Vector4f[] colors; 
    
    /**
     * Sets the data of [mesh] to the data of this 
     * object. This method should be called from the 
     * main game thread 
     * 
     * @param mesh Mesh to modify 
     */
    public void apply(Mesh mesh) {
        mesh.setIndices(inds);
        mesh.setPositions(positions);
        mesh.setNormals(normals);
        mesh.setColors(colors);
    }
    
}
