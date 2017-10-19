package oasis.graphics.model;

import oasis.math.Vector2f;
import oasis.math.Vector3f;
import oasis.math.Vector4f;

/**
 * Helper class to keep track of vertex data
 * 
 * @author Nicholas Hamilton
 *
 */
public class Vertex {

    /**
     * Position 
     */
    public Vector3f position;
    
    /**
     * Normal 
     */
    public Vector3f normal;
    
    /**
     * Color 
     */
    public Vector4f color;
    
    /**
     * Texture coordinates 
     */
    public Vector2f texCoord0; 
    
    /**
     * Set mesh data to an array of vertices 
     * 
     * @param verts Array of vertices 
     * @param mesh Mesh to modify 
     */
    public static void setMesh(Vertex[] verts, Mesh mesh) { 
        Vector3f[] positions = new Vector3f[verts.length]; 
        Vector3f[] normals = new Vector3f[verts.length]; 
        Vector4f[] colors = new Vector4f[verts.length]; 
//        Vector2[] uvs = new Vector2[verts.length]; 
        
        for (int i = 0; i < verts.length; i++) { 
            positions[i] = verts[i].position; 
            normals[i] = verts[i].normal; 
            colors[i] = verts[i].color; 
//            uvs[i] = verts[i].uv; 
        }
        
        mesh.setPositions(positions);
        mesh.setNormals(normals);
        mesh.setColors(colors);
        // TODO UV coords
    }
    
    /**
     * Create mesh data. Useful for separate threads 
     * 
     * @param verts Vertices
     * @param inds Indices 
     * @return MeshData
     */
    public static MeshData createMeshData(Vertex[] verts, int[] inds) {
        Vector3f[] positions = new Vector3f[verts.length]; 
        Vector3f[] normals = new Vector3f[verts.length]; 
        Vector4f[] colors = new Vector4f[verts.length]; 
//        Vector2[] uvs = new Vector2[verts.length]; 
        
        for (int i = 0; i < verts.length; i++) { 
            positions[i] = verts[i].position; 
            normals[i] = verts[i].normal; 
            colors[i] = verts[i].color; 
//            uvs[i] = verts[i].uv; 
        }
        
        MeshData data = new MeshData(); 
        data.positions = positions; 
        data.normals = normals; 
        data.colors = colors; 
        data.inds = inds; 
        // TODO UV coords
        
        return data; 
    }
    
    /**
     * Sets the vertex and index data of a mesh
     * 
     * @param verts Vertices 
     * @param inds Indices 
     * @param mesh Mesh to modify 
     */
    public static void setMesh(Vertex[] verts, int[] inds, Mesh mesh) { 
        setMesh(verts, mesh); 
        mesh.setIndices(inds);
    }
    
    /**
     * Calculates normals of vertices. Assumes triangle list
     * and CCW order. Lighting will be flat shaded since no
     * vertices are shared
     * 
     * @param verts Vertices 
     */
    public static void calculateNormals(Vertex[] verts) { 
        for (int i = 0; i < verts.length; i += 3) { 
            Vector3f a = verts[i + 1].position.subtract(verts[i + 0].position).normalize(); 
            Vector3f b = verts[i + 2].position.subtract(verts[i + 0].position).normalize(); 
            Vector3f normal = a.cross(b).normalize(); 
            verts[i + 0].normal = normal; 
            verts[i + 1].normal = normal; 
            verts[i + 2].normal = normal; 
        }
    }
    
    /**
     * Calculates normals of vertices. Assumes triangle list 
     * and CCW order. Lighting will be smooth shaded since 
     * vertices can be shared 
     * 
     * @param verts Vertices 
     * @param inds Indices 
     */
    public static void calculateNormals(Vertex[] verts, int[] inds) { 
        for (int i = 0; i < verts.length; i++) { 
            verts[i].normal = new Vector3f(); 
        }
        
        for (int i = 0; i < inds.length; i += 3) { 
            Vector3f a = verts[inds[i + 1]].position.subtract(verts[inds[i + 0]].position).normalize(); 
            Vector3f b = verts[inds[i + 2]].position.subtract(verts[inds[i + 0]].position).normalize(); 
            Vector3f normal = a.cross(b).normalize(); 
            verts[inds[i + 0]].normal.addSelf(normal); 
            verts[inds[i + 1]].normal.addSelf(normal); 
            verts[inds[i + 2]].normal.addSelf(normal); 
        }
        
        for (int i = 0; i < verts.length; i++) { 
            verts[i].normal.normalizeSelf(); 
//            System.out.println(verts[i].normal);
        }
    }
}
