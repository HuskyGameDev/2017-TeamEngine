package oasis.graphics.model;

import oasis.math.Vector3;
import oasis.math.Vector4;

public class MeshData {

    public int[] inds; 
    public Vector3[] positions; 
    public Vector3[] normals; 
    public Vector4[] colors; 
    
    public void apply(Mesh mesh) {
        mesh.setIndices(inds);
        mesh.setPositions(positions);
        mesh.setNormals(normals);
        mesh.setColors(colors);
    }
    
}
