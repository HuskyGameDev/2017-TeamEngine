package oasis.graphics.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds meshes and materials 
 * 
 * @author Nicholas Hamilton 
 *
 */
public class Model {

    // holds a mesh and a material
    // TODO add a transform as well 
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
    
    /**
     * how many parts to the model 
     * @return part count 
     */
    public int getPartCount() {
        return meshes.size(); 
    }
    
    /**
     * add a mesh and material to the model 
     * 
     * @param mesh
     * @param mat
     */
    public void add(Mesh mesh, Material mat) {
        meshes.add(new MeshComponent(mesh, mat)); 
    }
    
    /**
     * Get a mesh from the model 
     * 
     * @param index
     * @return
     */
    public Mesh getMesh(int index) {
        return meshes.get(index).mesh; 
    }
    
    /**
     * Set a mesh for the model 
     */
    public void setMesh(int index, Mesh mesh) {
        meshes.get(index).mesh = mesh; 
    }
    
    /**
     * Get a material from the model 
     * 
     * @param index
     * @return
     */
    public Material getMaterial(int index) {
        return meshes.get(index).material; 
    }
    
    /**
     * Set a material for a mesh
     */
    public void setMaterial(int index, Material mat) {
        meshes.get(index).material = mat; 
    }
    
    /**
     * Remove a mesh and material from the model 
     * 
     * @param index
     */
    public void remove(int index) {
        meshes.remove(index); 
    }
    
}
