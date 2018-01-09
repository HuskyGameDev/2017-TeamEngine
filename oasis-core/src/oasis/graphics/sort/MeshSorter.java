package oasis.graphics.sort;

import java.util.List;

import oasis.graphics.Camera;
import oasis.graphics.LightList;
import oasis.graphics.RenderData;

public interface MeshSorter {

    void sort(Camera camera, LightList lights, List<RenderData> meshes); 
    
}
