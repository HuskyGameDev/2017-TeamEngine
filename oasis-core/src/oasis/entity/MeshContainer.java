package oasis.entity;

import oasis.graphics.Material;
import oasis.graphics.Mesh;

public class MeshContainer extends EntityComponent {

    private Mesh mesh; 
    private Material material; 
    
    public MeshContainer() {
        reset(); 
    }
    
    public void reset() {
        mesh = null; 
        material = null; 
    }
    
    public void activate() {
        reset(); 
    }
    
    public void deactivate() {
        reset(); 
    }
    
    public Mesh getMesh() {
        return mesh; 
    }
    
    public Material getMaterial() {
        return material; 
    }
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh; 
    }
    
    public void setMaterial(Material mat) {
        this.material = mat; 
    }
    
}
