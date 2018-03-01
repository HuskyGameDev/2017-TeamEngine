package oasis.graphics.sort;

import java.util.List;

import oasis.graphics.RenderData;
import oasis.graphics.RenderLightList;
import oasis.scene.Camera;
import oasis.scene.Transform;

public class BasicMeshSorter implements MeshSorter {

    @Override
    public void sort(Camera camera, Transform tfm, RenderLightList lights, List<RenderData> meshes) {
        // do nothing 
    }

}
