package oasis.graphics.sort;

import java.util.List;

import oasis.entity.Camera;
import oasis.entity.Transform;
import oasis.graphics.RenderData;
import oasis.graphics.RenderLightList;

public class BasicMeshSorter implements MeshSorter {

    @Override
    public void sort(Camera camera, Transform tfm, RenderLightList lights, List<RenderData> meshes) {
        // do nothing 
    }

}
