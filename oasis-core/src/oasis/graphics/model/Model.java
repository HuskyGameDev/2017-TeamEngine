package oasis.graphics.model;

import java.util.ArrayList;
import java.util.List;

import oasis.math.Transform;

/**
 * Holds meshes and materials 
 * 
 * @author thinic
 *
 */
public class Model {

    private class MeshComponent {
        public Mesh mesh; 
        public Material material; 
        
        public MeshComponent(Mesh mesh, Material material) {
            this.mesh = mesh; 
            this.material = material; 
        }
    }
    
    private List<MeshComponent> meshes; 
    
    public Model() {
        meshes = new ArrayList<>(); 
    }
    
    public int getPartCount() {
        return meshes.size(); 
    }
    
    public void add(Mesh mesh, Material mat) {
        meshes.add(new MeshComponent(mesh, mat)); 
    }
    
    public Mesh getMesh(int index) {
        return meshes.get(index).mesh; 
    }
    
    public Material getMaterial(int index) {
        return meshes.get(index).material; 
    }
    
    public void remove(int index) {
        meshes.remove(index); 
    }
    
}
