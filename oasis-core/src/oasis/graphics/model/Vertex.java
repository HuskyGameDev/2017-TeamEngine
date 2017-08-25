package oasis.graphics.model;

import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class Vertex {

    public Vector3 position; 
    public Vector3 normal; 
    public Vector4 color; 
    public Vector2 uv; 
    
    public static void setMesh(Vertex[] verts, Mesh mesh) { 
        Vector3[] positions = new Vector3[verts.length]; 
        Vector3[] normals = new Vector3[verts.length]; 
        Vector4[] colors = new Vector4[verts.length]; 
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
    
    public static MeshData createMeshData(Vertex[] verts, int[] inds) {
        Vector3[] positions = new Vector3[verts.length]; 
        Vector3[] normals = new Vector3[verts.length]; 
        Vector4[] colors = new Vector4[verts.length]; 
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
    
    public static void setMesh(Vertex[] verts, int[] inds, Mesh mesh) { 
        setMesh(verts, mesh); 
        mesh.setIndices(inds);
    }
    
    public static void calculateNormals(Vertex[] verts) { 
        for (int i = 0; i < verts.length; i += 3) { 
            Vector3 a = verts[i + 1].position.subtract(verts[i + 0].position); 
            Vector3 b = verts[i + 2].position.subtract(verts[i + 0].position); 
            Vector3 normal = b.cross(a).normalize(); 
            verts[i + 0].normal = normal; 
            verts[i + 1].normal = normal; 
            verts[i + 2].normal = normal; 
        }
    }
    
    public static void calculateNormals(Vertex[] verts, int[] inds) { 
        for (int i = 0; i < verts.length; i++) { 
            verts[i].normal = new Vector3(); 
        }
        
        for (int i = 0; i < inds.length; i += 3) { 
            Vector3 a = verts[inds[i + 1]].position.subtract(verts[inds[i + 0]].position); 
            Vector3 b = verts[inds[i + 2]].position.subtract(verts[inds[i + 0]].position); 
            Vector3 normal = b.cross(a).normalize(); 
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
