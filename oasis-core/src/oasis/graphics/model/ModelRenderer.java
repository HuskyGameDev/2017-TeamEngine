package oasis.graphics.model;

import java.util.ArrayList;
import java.util.List;

import oasis.core.EngineException;
import oasis.core.Oasis;
import oasis.file.GlslParser;
import oasis.file.PathList;
import oasis.graphics.BlendMode;
import oasis.graphics.Shader;
import oasis.graphics.light.DirectionalLight;
import oasis.graphics.light.Light;
import oasis.graphics.light.LightList;
import oasis.graphics.light.PointLight;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector3;

/**
 * Renders models and meshes. 
 * 
 * Similar to the sprite batch, but for 3D
 * 
 * @author Nicholas Hamilton 
 *
 */
// TODO allow render targets 
// this class is really inefficient right now!
public class ModelRenderer {
    
    // all the specific data needed to render a mesh 
    private class MeshRenderCommand {
        public Mesh mesh; 
        public Material material; 
        public Matrix4 matrix;
        public Matrix3 normalMatrix; 
        
        public MeshRenderCommand(Mesh mesh, Material material, Matrix4 matrix, Matrix3 normalMat) {
            this.mesh = mesh; 
            this.material = material; 
            this.matrix = matrix; 
            this.normalMatrix = normalMat; 
        }
    }
    
    private Camera camera; 
    private List<MeshRenderCommand> opaqueQueue; 
    private List<MeshRenderCommand> translucentQueue;
    private List<MeshRenderCommand> transparentQueue;
    private LightList lightList = new LightList(); 
    
    // default shader 
    private Shader blinnPhongShader; 
    
    public ModelRenderer() {
        opaqueQueue = new ArrayList<>(); 
        translucentQueue = new ArrayList<>(); 
        transparentQueue = new ArrayList<>();
        
        // initialize the default shader 
        String blinnPhongVertexSource = GlslParser.getVertexSource("blinn-phong.glsl", PathList.DEFAULT); 
        String blinnPhongFragmentSource = GlslParser.getFragmentSource("blinn-phong.glsl", PathList.DEFAULT); 
        blinnPhongShader = Oasis.graphics.createShader(blinnPhongVertexSource, blinnPhongFragmentSource); 
    }
    
    /**
     * Call this before rendering 
     * 
     * @param camera Camera to draw from 
     */
    public void begin(Camera camera) {
        this.camera = camera; 
        camera.setSize(Oasis.graphics.getWidth(), Oasis.graphics.getHeight());
        lightList.clear(); 
    }
    
    /**
     * Add a light to the scene 
     * 
     * @param light to add
     */
    public void addLight(Light light) 
    {
        lightList.add(light);
    }
    
    /**
     * Call this when you are done rendering 
     */
    public void end() {
        render(); 
        this.camera = null; 
    }
    
    // render all queued meshes 
    // TODO make more efficient 
    private void render() {
        Oasis.graphics.setRenderTarget(null);
        Oasis.graphics.setDepthTestEnabled(true);
        
        Matrix4 projection = camera.getProjectionMatrix(); 
        Matrix4 view = camera.getViewMatrix(); 
        
        // draw opaque 
        Oasis.graphics.setDepthWriteEnabled(true); 
        Oasis.graphics.setBlendMode(BlendMode.ONE, BlendMode.ZERO);
        drawMeshes(opaqueQueue, blinnPhongShader, projection, view); 
        
        // draw transparent 
        // no need to disable depth writing, since all fragments are either 
        // opaque or completely transparent 
        Oasis.graphics.setDepthWriteEnabled(true); 
        Oasis.graphics.setBlendMode(BlendMode.ONE, BlendMode.ZERO);
        drawMeshes(transparentQueue, blinnPhongShader, projection, view); 
        
        // draw translucent 
        Oasis.graphics.setDepthWriteEnabled(false); 
        Oasis.graphics.setBlendMode(BlendMode.SRC_ALPHA, BlendMode.ONE_MINUS_SRC_ALPHA);
        drawMeshes(translucentQueue, blinnPhongShader, projection, view); 
        
        Oasis.graphics.setDepthWriteEnabled(true); 
        Oasis.graphics.setBlendMode(BlendMode.ONE, BlendMode.ZERO);
        
        opaqueQueue.clear(); 
        transparentQueue.clear(); 
        translucentQueue.clear(); 
    }
    
    // draw meshes in a queue
    private void drawMeshes(List<MeshRenderCommand> meshes, Shader defaultShader, Matrix4 projection, Matrix4 view) {
        Shader shader = null; 
        
        Vector3 ambient = new Vector3(0.2f); 
        Vector3 zero = new Vector3(0); 
        
        for (int i = 0; i < Math.max(lightList.getLightCount(), 1); i++) {
            if (i != 0) {
                Oasis.graphics.setBlendMode(BlendMode.ONE, BlendMode.ONE);
            }
            
            for (MeshRenderCommand c : meshes) {
                shader = c.material.shader; 
                if (shader == null) shader = defaultShader; 
                Oasis.graphics.setShader(shader);
                
                shader.setMatrix4("Projection", projection);
                shader.setMatrix4("View", view);
                shader.setMatrix4("Model", c.matrix);
                shader.setMatrix3("NormalMat", c.normalMatrix);
                shader.setVector3("CameraPosition", camera.getPosition());
                
                shader.setVector3("AmbientColor", i == 0 ? ambient : zero); 
                applyLight(shader, lightList.getLightCount() == 0 ? null : lightList.get(i)); 
                
                c.material.apply(Oasis.graphics, shader, i == 0);
                c.mesh.draw(); 
            }
        }
    }
    
    private void applyLight(Shader shader, Light light) {
        if (light instanceof DirectionalLight) {
            DirectionalLight dl = (DirectionalLight) light; 
            shader.setInt("Light.Type", 0);
            shader.setFloat("Light.Radius", 0);
            shader.setVector3("Light.Color", light.getColor());
            shader.setVector3("Light.Position", dl.getDirection());
        }
        else if (light instanceof PointLight) {
            PointLight pl = (PointLight) light; 
            shader.setInt("Light.Type", 1);
            shader.setFloat("Light.Radius", pl.getRadius());
            shader.setVector3("Light.Color", light.getColor());
            shader.setVector3("Light.Position", pl.getPosition());
        }
        else if (light == null) {
            shader.setInt("Light.Type", -1);
            shader.setFloat("Light.Radius", 0);
            shader.setVector3("Light.Color", new Vector3(0));
            shader.setVector3("Light.Position", new Vector3(0));
        }
        else {
            throw new EngineException("Unknown light type: " + light); 
        }
    }
    
    public void draw(Mesh mesh, Material material, Vector3 position, Quaternion rotation) {
        Matrix4 modelMat = Matrix4.translation(position)
                .multiply(Matrix4.rotation(rotation)); 
        
        addMesh(new MeshRenderCommand(mesh, material, modelMat, new Matrix3(modelMat)), material.renderQueue); 
    }
    
    // TODO compute a normal matrix 
    public void draw(Mesh mesh, Material material, Matrix4 modelMat) {
        addMesh(new MeshRenderCommand(mesh, material, modelMat, new Matrix3(modelMat)), material.renderQueue); 
    }
    
    // add each part of a model to the render queue
    public void draw(Model model, Vector3 position, Quaternion rotation) {
        for (int i = 0; i < model.getPartCount(); i++) {
            draw(model.getMesh(i), model.getMaterial(i), position, rotation); 
        }
    }
    
    // add the mesh to the right queue 
    private void addMesh(MeshRenderCommand command, RenderQueue queue) {
        switch (queue) {
        case OPAQUE: 
            opaqueQueue.add(command); 
            return; 
        case TRANSPARENT: 
            transparentQueue.add(command); 
            return; 
        case TRANSLUCENT: 
            translucentQueue.add(command); 
            return; 
        }
    }
    
}
