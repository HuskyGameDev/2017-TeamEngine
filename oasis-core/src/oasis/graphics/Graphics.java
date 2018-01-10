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
    
    private static final int UNIFORM_PROJ_MAT = 0; 
    private static final int UNIFORM_VIEW_MAT = 1; 
    private static final int UNIFORM_MODEL_MAT = 2; 
    private static final int UNIFORM_NORMAL_MAT = 3; 
    private static final int UNIFORM_CAM_POS = 4; 
    private static final int UNIFORM_AMBIENT_COL = 5; 
    private static final int UNIFORM_DIFFUSE_COL = 6; 
    private static final int UNIFORM_SPECULAR_COL = 7; 
    private static final int UNIFORM_EMISSIVE_COL = 8; 
    private static final int UNIFORM_LIGHT_COL = 9; 
    private static final int UNIFORM_LIGHT_POS = 10; 
    private static final int UNIFORM_LIGHT_ATTEN = 11; 
    private static final int UNIFORM_COUNT = 12; 
    
    private static final String[] UNIFORM_NAMES = new String[UNIFORM_COUNT]; 
    
    static {
        ADD_STATE.setSourceBlendMode(GraphicsState.BlendMode.ONE); 
        ADD_STATE.setDestBlendMode(GraphicsState.BlendMode.ONE); 
        
        UNIFORM_NAMES[UNIFORM_PROJ_MAT] = "oasis_ProjectionMatrix"; 
        UNIFORM_NAMES[UNIFORM_VIEW_MAT] = "oasis_ViewMatrix"; 
        UNIFORM_NAMES[UNIFORM_MODEL_MAT] = "oasis_ModelMatrix"; 
        UNIFORM_NAMES[UNIFORM_NORMAL_MAT] = "oasis_NormalMatrix"; 
        UNIFORM_NAMES[UNIFORM_CAM_POS] = "oasis_CameraPosition"; 
        UNIFORM_NAMES[UNIFORM_AMBIENT_COL] = "oasis_AmbientColor"; 
        UNIFORM_NAMES[UNIFORM_DIFFUSE_COL] = "oasis_DiffuseColor"; 
        UNIFORM_NAMES[UNIFORM_SPECULAR_COL] = "oasis_SpecularColor"; 
        UNIFORM_NAMES[UNIFORM_EMISSIVE_COL] = "oasis_EmissiveColor"; 
        UNIFORM_NAMES[UNIFORM_LIGHT_COL] = "oasis_LightColor"; 
        UNIFORM_NAMES[UNIFORM_LIGHT_POS] = "oasis_LightPosition"; 
        UNIFORM_NAMES[UNIFORM_LIGHT_ATTEN] = "oasis_LightAttenuation"; 
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
        
        UniformValue[] uniforms = new UniformValue[UNIFORM_COUNT]; 
        Material lastMat = null; 
        
        Matrix4 projMat = camera.getProjectionMatrix(); 
        Matrix4 viewMat = camera.getViewMatrix(); 
        Vector3 camPos = camera.getPosition(); 
        
        for (RenderData data : meshes) {
            Material mat = data.getMaterial(); 
            Shader shader = mat.getShader(); 
            
            if (lastMat == null || lastMat.getShader() != shader) {
                fillUniforms(shader, uniforms); 
                
                uniforms[UNIFORM_PROJ_MAT].setValue(projMat);
                uniforms[UNIFORM_VIEW_MAT].setValue(viewMat); 
                uniforms[UNIFORM_CAM_POS].setValue(camPos); 
            }
            
            if (lastMat == null || lastMat != mat) {
                applyMaterial(mat, uniforms); 
            }
            
            renderLights.clear(); 
            lightSorter.sort(data, lights, renderLights); 
            
            uniforms[UNIFORM_MODEL_MAT].setValue(data.getModelMatrix()); 
            uniforms[UNIFORM_NORMAL_MAT].setValue(data.getNormalMatrix()); 
            
            Vector3 ambient = renderLights.getAmbient(); 
            Geometry geom = data.getMesh().getGeometry(data.getSubmesh()); 
            
            gd.setState(BASE_STATE); 
            
            {
                Light light = null; 
                
                if (renderLights.getLightCount() > 0) {
                    light = renderLights.get(0); 
                }
                
                uniforms[UNIFORM_AMBIENT_COL].setValue(new Vector4(ambient, 1)); 
                applyLight(light, uniforms); 
                
                renderGeometry(gd, shader, geom); 
            }
            
            gd.setState(ADD_STATE); 
            
            uniforms[UNIFORM_AMBIENT_COL].clear(); 
            
            for (int i = 1; i < renderLights.getLightCount(); i++) {
                Light light = renderLights.get(i); 
                
                applyLight(light, uniforms); 
                
                renderGeometry(gd, shader, geom); 
            }
        }
    }
    
    private void fillUniforms(Shader shader, UniformValue[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = shader.getSafeUniformValue(UNIFORM_NAMES[i]); 
        }
    }
    
    private void renderGeometry(GraphicsDevice gd, Shader shader, Geometry geom) {
        gd.setShader(shader); 
        gd.drawGeometry(geom); 
    }
    
    private void applyMaterial(Material mat, UniformValue[] uniforms) {
        Vector4 diffuse = new Vector4(mat.getDiffuseColor(), mat.getAlpha()); 
        Vector4 specular = new Vector4(mat.getSpecularColor(), mat.getSpecularPower()); 
        Vector3 emissive = new Vector3(mat.getEmissiveColor()); 
        
        uniforms[UNIFORM_DIFFUSE_COL].setValue(diffuse); 
        uniforms[UNIFORM_SPECULAR_COL].setValue(specular); 
        uniforms[UNIFORM_EMISSIVE_COL].setValue(emissive); 
    }
    
    private void applyLight(Light light, UniformValue[] uniforms) {
        if (light != null) {
            uniforms[UNIFORM_LIGHT_COL].setValue(light.getColor()); 
            uniforms[UNIFORM_LIGHT_POS].setValue(light.getPositionUniform()); 
            uniforms[UNIFORM_LIGHT_ATTEN].setValue(light.getAttenuationUniform()); 
        }
        else {
            uniforms[UNIFORM_LIGHT_COL].clear(); 
            uniforms[UNIFORM_LIGHT_POS].clear(); 
            uniforms[UNIFORM_LIGHT_ATTEN].clear(); 
        }
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
