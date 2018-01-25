package oasis.graphics.sort;

import java.util.List;

import oasis.entity.Camera;
import oasis.entity.Transform;
import oasis.graphics.RenderData;
import oasis.graphics.RenderLightList;

public interface MeshSorter {

    void sort(Camera camera, Transform trasform, RenderLightList lights, List<RenderData> meshes); 
    
}
