package oasis.graphics.sort;

import java.util.List;

import oasis.entity.Camera;
import oasis.entity.Transform;
import oasis.graphics.LightList;
import oasis.graphics.RenderData;

public interface MeshSorter {

    void sort(Camera camera, Transform trasform, LightList lights, List<RenderData> meshes); 
    
}
