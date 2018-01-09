package oasis.graphics;

import java.util.ArrayList;
import java.util.List;

import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.graphics.sort.BasicLightSorter;
import oasis.graphics.sort.BasicMeshSorter;
import oasis.graphics.sort.LightSorter;
import oasis.graphics.sort.MeshSorter;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class Graphics {

    private static final Logger log = new Logger(Graphics.class); 
    
    private static final GraphicsState BASE_STATE = new GraphicsState(); 
    private static final GraphicsState ADD_STATE = new GraphicsState(); 
    
    static {
        ADD_STATE.setSourceBlendMode(GraphicsState.BlendMode.ONE); 
        ADD_STATE.setDestBlendMode(GraphicsState.BlendMode.ONE); 
    }
    
    private List<RenderData> opaqueQueue = new ArrayList<>(); 
    private LightList lights = new LightList(); 
    private Camera camera = null; 
    
    public Graphics() {}
    
    public void begin() {
        opaqueQueue.clear(); 
        lights.clear(); 
    }
    
    public void finish() {
        drawQueue(camera, RenderQueue.OPAQUE); 
    }
    
    private void drawQueue(Camera camera, RenderQueue queue) {
        GraphicsDevice gd = Oasis.getGraphicsDevice(); 
        
        LightSorter lightSorter = new BasicLightSorter(); 
        MeshSorter meshSorter = new BasicMeshSorter(); 
        
        List<RenderData> meshes = getQueue(queue); 
        
        meshSorter.sort(camera, lights, meshes);
        
        LightList renderLights = new LightList(); 
        
        for (RenderData data : meshes) {
            Shader shader = data.getMaterial().getShader(); 
            
            renderLights.clear(); 
            lightSorter.sort(data, lights, renderLights); 
            
            Matrix4 mMatrix = data.getModelMatrix(); 
            Matrix3 nMatrix = data.getNormalMatrix(); 
            Vector3 ambient = renderLights.getAmbient(); 
            Geometry geom = data.getMesh().getGeometry(data.getSubmesh()); 
            Material mat = data.getMaterial(); 
            
            gd.setState(BASE_STATE); 
            
            {
                Light light = null; 
                
                if (renderLights.getLightCount() > 0) {
                    light = renderLights.get(0); 
                }
                
                renderGeometry(gd, camera, shader, geom, mat, mMatrix, nMatrix, light, ambient, true); 
            }
            
            gd.setState(ADD_STATE); 
            
            for (int i = 1; i < renderLights.getLightCount(); i++) {
                Light light = renderLights.get(i); 
                
                renderGeometry(gd, camera, shader, geom, mat, mMatrix, nMatrix, light, ambient, false); 
            }
        }
    }
    
    private void renderGeometry(GraphicsDevice gd, Camera camera, Shader shader, Geometry geom, Material mat, Matrix4 mMatrix, Matrix3 nMatrix, Light light, Vector3 ambient, boolean base) {
        shader.reset(); 
        mat.apply(shader); 
        shader.setMatrix4("oasis_ProjectionMatrix", camera.getProjectionMatrix());
        shader.setMatrix4("oasis_ViewMatrix", camera.getViewMatrix());
        shader.setMatrix4("oasis_ModelMatrix", mMatrix);
        shader.setMatrix3("oasis_NormalMatrix", nMatrix); 
        shader.setVector3("oasis_CameraPosition", camera.getPosition()); 
        
        if (base) {
            shader.setVector4("oasis_AmbientColor", new Vector4(ambient, 1)); 
        }
        else {
            shader.clearUniform("oasis_AmbientColor"); 
        }
        
        if (light != null) light.apply(shader); 
        
        gd.setShader(shader); 
        gd.drawGeometry(geom);
    }
    
    public void setCamera(Camera camera) {
        this.camera = camera; 
    }
    
    public void addLight(Light light) {
        lights.add(light); 
    }
    
    public void addAmbient(Vector3 color) {
        lights.setAmbient(lights.getAmbient().addSelf(color)); 
    }
    
    public void drawMesh(Mesh mesh, int submesh, Material material, Matrix4 modelMatrix, Matrix3 normalMatrix) {
        RenderData c = new RenderData(mesh, submesh, material, modelMatrix, normalMatrix); 
        
        getQueue(material.getRenderQueue()).add(c); 
    }
    
    public void drawMesh(Mesh mesh, int submesh, Material material, Matrix4 modelMatrix) {
        drawMesh(mesh, submesh, material, modelMatrix, modelMatrix.getNormalMatrix()); 
    }
    
    public void drawMesh(Mesh mesh, Material[] materials, Matrix4 modelMatrix, Matrix3 normalMatrix) {
        for (int i = 0; i < mesh.getSubmeshCount(); i++) {
            drawMesh(mesh, i, materials[i], modelMatrix, normalMatrix); 
        }
    }
    
    public void drawMesh(Mesh mesh, Material[] materials, Matrix4 modelMatrix) {
        Matrix3 normalMatrix = modelMatrix.getNormalMatrix(); 
        
        drawMesh(mesh, materials, modelMatrix, normalMatrix); 
    }
    
    private List<RenderData> getQueue(RenderQueue queue) {
        switch (queue) {
        default: 
            throw new OasisException("Unknown RenderQueue: " + queue); 
        case OPAQUE: 
            return opaqueQueue; 
        }
    }
    
}
