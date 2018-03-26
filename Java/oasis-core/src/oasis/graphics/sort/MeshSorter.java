package oasis.graphics.sort;

import java.util.List;

import oasis.graphics.RenderData;
import oasis.graphics.RenderLightList;
import oasis.scene.Camera;
import oasis.scene.Transform;

public interface MeshSorter {

    void sort(Camera camera, Transform trasform, RenderLightList lights, List<RenderData> meshes); 
    
}
