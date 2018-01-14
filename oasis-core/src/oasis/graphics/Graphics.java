package oasis.graphics;

import java.util.ArrayList;
import java.util.List;

import oasis.core.Logger;
import oasis.core.Oasis;
import oasis.core.OasisException;
import oasis.core.ResourceManager;
import oasis.graphics.sort.BasicLightSorter;
import oasis.graphics.sort.BasicMeshSorter;
import oasis.graphics.sort.LightSorter;
import oasis.graphics.sort.MeshSorter;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Vector2;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class Graphics {

    private static final Logger log = new Logger(Graphics.class); 
    
    private static final GraphicsState BLIT_STATE = new GraphicsState(); 
    private static final GraphicsState BASE_STATE = new GraphicsState(); 
    private static final GraphicsState ADD_STATE = new GraphicsState(); 
    
    private static final int UNIFORM_PROJ_MAT = 0; 
    private static final int UNIFORM_VIEW_MAT = 1; 
    private static final int UNIFORM_MODEL_VIEW_MAT = 2; 
    private static final int UNIFORM_NORMAL_MAT = 3; 
    private static final int UNIFORM_CAM_POS = 4; 
    private static final int UNIFORM_AMBIENT_COL = 5; 
    private static final int UNIFORM_DIFFUSE_COL = 6; 
    private static final int UNIFORM_SPECULAR_COL = 7; 
    private static final int UNIFORM_EMISSIVE_COL = 8; 
    private static final int UNIFORM_LIGHT_COL = 9; 
    private static final int UNIFORM_LIGHT_POS = 10; 
    private static final int UNIFORM_LIGHT_ATTEN = 11; 
    private static final int UNIFORM_DIFFUSE_TEX = 12;
    private static final int UNIFORM_HAS_DIFFUSE_TEX = 13; 
    private static final int UNIFORM_NORMAL_TEX = 14; 
    private static final int UNIFORM_COUNT = 15; 
    
    private static final int[] UNIFORM_NAMES = new int[UNIFORM_COUNT]; 
    
    static {
        ADD_STATE.setSourceBlendMode(BlendMode.ONE); 
        ADD_STATE.setDestBlendMode(BlendMode.ONE); 
        
        UNIFORM_NAMES[UNIFORM_PROJ_MAT] = Shader.getUniformId("oasis_Projection"); 
        UNIFORM_NAMES[UNIFORM_VIEW_MAT] = Shader.getUniformId("oasis_View"); 
        UNIFORM_NAMES[UNIFORM_MODEL_VIEW_MAT] = Shader.getUniformId("oasis_ModelView"); 
        UNIFORM_NAMES[UNIFORM_NORMAL_MAT] = Shader.getUniformId("oasis_Normal"); 
        UNIFORM_NAMES[UNIFORM_CAM_POS] = Shader.getUniformId("oasis_ViewPosition"); 
        UNIFORM_NAMES[UNIFORM_AMBIENT_COL] = Shader.getUniformId("oasis_Ambient"); 
        UNIFORM_NAMES[UNIFORM_DIFFUSE_COL] = Shader.getUniformId("oasis_Diffuse"); 
        UNIFORM_NAMES[UNIFORM_SPECULAR_COL] = Shader.getUniformId("oasis_Specular"); 
        UNIFORM_NAMES[UNIFORM_EMISSIVE_COL] = Shader.getUniformId("oasis_Emissive"); 
        UNIFORM_NAMES[UNIFORM_LIGHT_COL] = Shader.getUniformId("oasis_LightColor"); 
        UNIFORM_NAMES[UNIFORM_LIGHT_POS] = Shader.getUniformId("oasis_LightPosition"); 
        UNIFORM_NAMES[UNIFORM_LIGHT_ATTEN] = Shader.getUniformId("oasis_LightAtten"); 
        UNIFORM_NAMES[UNIFORM_DIFFUSE_TEX] = Shader.getUniformId("oasis_DiffuseMap");
        UNIFORM_NAMES[UNIFORM_HAS_DIFFUSE_TEX] = Shader.getUniformId("oasis_HasDiffuseMap"); 
        UNIFORM_NAMES[UNIFORM_NORMAL_TEX] = Shader.getUniformId("oasis_NormalMap"); 
    }
    
    private List<RenderData> opaqueQueue = new ArrayList<>(); 
    private LightList lights = new LightList(); 
    private Camera camera = null; 
    
    private boolean needInit = true; 
    private Texture2D defaultNormalTex; 
    private Texture2D defaultDiffuseTex; 
    private Mesh quad; 
    private Shader blitShader; 
    
    public Graphics() {}
    
    private void init() {
        defaultNormalTex = Texture2D.create(TextureFormat.RGBA8, 1, 1); 
        defaultNormalTex.setPixels(new int[] { 0x7F7FFFFF });
        defaultNormalTex.upload(); 
        
        defaultDiffuseTex = Texture2D.create(TextureFormat.RGBA8, 1, 1); 
        defaultDiffuseTex.setPixels(new int[] { 0xFFFFFFFF });
        defaultDiffuseTex.upload(); 
        
        quad = Mesh.create(); 
        quad.setPositions(new Vector3[] {
                new Vector3(-1, -1, 0), 
                new Vector3( 1, -1, 0), 
                new Vector3( 1,  1, 0), 
                new Vector3(-1,  1, 0)
        });
        quad.setTexCoords(new Vector2[] {
                new Vector2(0, 0), 
                new Vector2(1, 0), 
                new Vector2(1, 1), 
                new Vector2(0, 1)
        });
        quad.setIndices(0, new short[] {
                0, 1, 2, 
                0, 2, 3
        });
        
        blitShader = ResourceManager.loadShader("blit.glsl"); 
        
        needInit = false; 
    }
    
    public void blit(Texture texture, RenderTarget target) {
        GraphicsDevice gd = Oasis.getGraphicsDevice();  
        
        gd.applyState(BLIT_STATE); 
        gd.setRenderTarget(target); 
        
        int id = Shader.getUniformId("oasis_MainTex"); 
        Shader.setTexture(id, texture); 
        
        gd.useShader(blitShader); 
        gd.drawMesh(quad, 0); 
        
        Shader.clearValue(id); 
    }
    
    public void begin() {
        opaqueQueue.clear(); 
        lights.clear(); 
        
        if (needInit) {
            init(); 
        }
    }
    
    public void finish() {
        GraphicsDevice gd = Oasis.getGraphicsDevice();  
        
        gd.setRenderTarget(camera.getRenderTarget()); 
        gd.clearBuffers(new Vector4(0.5f, 0.6f, 0.8f, 1.0f), true);
        
        drawQueue(camera, RenderQueue.OPAQUE); 
    }
    
    private void drawQueue(Camera camera, RenderQueue queue) {
        GraphicsDevice gd = Oasis.getGraphicsDevice(); 
        
        LightSorter lightSorter = new BasicLightSorter(); 
        MeshSorter meshSorter = new BasicMeshSorter(); 
        
        List<RenderData> meshes = getQueue(queue); 
        
        meshSorter.sort(camera, lights, meshes);
        
        LightList renderLights = new LightList(); 
        
        Material lastMat = null; 
        
        Matrix4 projMat = camera.getProjectionMatrix(); 
        Matrix4 viewMat = camera.getViewMatrix(); 
        Vector3 camPos = camera.getPosition(); 
        
        for (RenderData data : meshes) {
            Material mat = data.getMaterial(); 
            Shader shader = mat.getShader(); 
            
            if (shader == null) {
                log.warning("Null shader on material: " + mat); 
                continue; 
            }
            
            if (lastMat == null || lastMat.getShader() != shader) {
                Shader.setMatrix4(UNIFORM_NAMES[UNIFORM_PROJ_MAT], projMat);
                Shader.setMatrix4(UNIFORM_NAMES[UNIFORM_VIEW_MAT], viewMat);
                Shader.setVector3(UNIFORM_NAMES[UNIFORM_CAM_POS], camPos);
            }
            
            if (lastMat == null || lastMat != mat) {
                applyMaterial(mat); 
            }
            
            lastMat = mat; 
            
            renderLights.clear(); 
            lightSorter.sort(data, lights, renderLights); 
            
            Matrix4 modelView = viewMat.multiply(data.getModelMatrix()); 
            
            Shader.setMatrix4(UNIFORM_NAMES[UNIFORM_MODEL_VIEW_MAT], modelView); 
            Shader.setMatrix3(UNIFORM_NAMES[UNIFORM_NORMAL_MAT], modelView.getNormalMatrix()); 
            
            Vector3 ambient = renderLights.getAmbient(); 
            Mesh mesh = data.getMesh(); 
            int submesh = data.getSubmesh(); 
            
            BASE_STATE.setFrontFace(mat.getFrontFace()); 
            gd.applyState(BASE_STATE); 
            
            {
                Light light = null; 
                
                if (renderLights.getLightCount() > 0) {
                    light = renderLights.get(0); 
                }
                
                Shader.setVector3(UNIFORM_NAMES[UNIFORM_AMBIENT_COL], ambient); 
                applyLight(viewMat, light); 
                
                renderGeometry(gd, shader, mesh, submesh); 
            }
            
            ADD_STATE.setFrontFace(mat.getFrontFace()); 
            gd.applyState(ADD_STATE); 
            
            Shader.clearValue(UNIFORM_NAMES[UNIFORM_AMBIENT_COL]); 
            
            for (int i = 1; i < renderLights.getLightCount(); i++) {
                Light light = renderLights.get(i); 
                
                applyLight(viewMat, light); 
                
                renderGeometry(gd, shader, mesh, submesh); 
            }
        }
    }
    
    private void renderGeometry(GraphicsDevice gd, Shader shader, Mesh mesh, int submesh) {
        gd.useShader(shader); 
        gd.drawMesh(mesh, submesh); 
    }
    
    private void applyMaterial(Material mat) {
        Vector4 diffuse = new Vector4(mat.getDiffuseColor(), mat.getAlpha()); 
        Vector4 specular = new Vector4(mat.getSpecularColor(), mat.getSpecularPower()); 
        Vector3 emissive = new Vector3(mat.getEmissiveColor()); 
        Texture diffuseTex = mat.getDiffuseTexture(); 
        Texture normalTex = mat.getNormalTexture(); 
        
        Shader.setVector4(UNIFORM_NAMES[UNIFORM_DIFFUSE_COL], diffuse); 
        Shader.setVector4(UNIFORM_NAMES[UNIFORM_SPECULAR_COL], specular); 
        Shader.setVector3(UNIFORM_NAMES[UNIFORM_EMISSIVE_COL], emissive); 
        Shader.setTexture(UNIFORM_NAMES[UNIFORM_DIFFUSE_TEX], diffuseTex == null ? defaultDiffuseTex : diffuseTex); 
        Shader.setTexture(UNIFORM_NAMES[UNIFORM_NORMAL_TEX], normalTex == null ? defaultNormalTex : normalTex); 
        Shader.setInt(UNIFORM_NAMES[UNIFORM_HAS_DIFFUSE_TEX], diffuseTex == null ? 0 : 1); 
    }
    
    private void applyLight(Matrix4 view, Light light) {
        if (light != null) {
            Shader.setVector3(UNIFORM_NAMES[UNIFORM_LIGHT_COL], light.getColor()); 
            Shader.setVector4(UNIFORM_NAMES[UNIFORM_LIGHT_POS], light.getPositionUniform(view)); 
            Shader.setVector3(UNIFORM_NAMES[UNIFORM_LIGHT_ATTEN], light.getAttenuationUniform()); 
        }
        else {
            Shader.clearValue(UNIFORM_NAMES[UNIFORM_LIGHT_COL]); 
            Shader.clearValue(UNIFORM_NAMES[UNIFORM_LIGHT_POS]); 
            Shader.clearValue(UNIFORM_NAMES[UNIFORM_LIGHT_ATTEN]); 
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
