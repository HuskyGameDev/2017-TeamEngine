package oasis.graphics.sort;

import java.util.List;

import oasis.entity.Camera;
import oasis.entity.Transform;
import oasis.graphics.RenderLightList;
import oasis.graphics.RenderData;

public interface MeshSorter {

    void sort(Camera camera, Transform trasform, RenderLightList lights, List<RenderData> meshes); 
    
}
