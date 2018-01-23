package oasis.graphics.sort;

import java.util.List;

import oasis.entity.Camera;
import oasis.entity.Transform;
import oasis.graphics.LightList;
import oasis.graphics.RenderData;

public class BasicMeshSorter implements MeshSorter {

    @Override
    public void sort(Camera camera, Transform tfm, LightList lights, List<RenderData> meshes) {
        // do nothing 
    }

}
