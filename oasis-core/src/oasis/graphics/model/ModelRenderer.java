package oasis.graphics.model;

import java.util.ArrayList;
import java.util.List;

import oasis.graphics.BlendMode;
import oasis.graphics.GraphicsDevice;
import oasis.graphics.Shader;
import oasis.math.Matrix3f;
import oasis.math.Matrix4f;
import oasis.math.Quaternionf;
import oasis.math.Vector3f;
import oasis.util.FileSystem;

public class ModelRenderer {

    private class MeshRenderCommand {
        public Mesh mesh; 
        public Material material; 
        public Matrix4f matrix;
        public Matrix3f normalMatrix; 
        
        public MeshRenderCommand(Mesh mesh, Material material, Matrix4f matrix, Matrix3f normalMat) {
            this.mesh = mesh; 
            this.material = material; 
            this.matrix = matrix; 
            this.normalMatrix = normalMat; 
        }
    }
    
    private GraphicsDevice gd; 
    private Camera camera; 
    private List<MeshRenderCommand> opaqueQueue; 
    private List<MeshRenderCommand> translucentQueue;
    private List<MeshRenderCommand> transparentQueue;
    
    private Shader blinnPhongShader; 
    
    public ModelRenderer(GraphicsDevice gd) {
        this.gd = gd; 
        opaqueQueue = new ArrayList<>(); 
        translucentQueue = new ArrayList<>(); 
        transparentQueue = new ArrayList<>();
        
        String blinnPhongVertexSource = FileSystem.readTextFile(ModelRenderer.class.getResource("/shaders/blinn-phong.vert").getFile()); 
        String blinnPhongFragmentSource = FileSystem.readTextFile(ModelRenderer.class.getResource("/shaders/blinn-phong.frag").getFile());
        blinnPhongShader = gd.createShader(blinnPhongVertexSource, blinnPhongFragmentSource); 
    }
    
    public void begin(Camera camera) {
        this.camera = camera; 
    }
    
    public void end() {
        render(); 
        this.camera = null; 
    }
    
    // TODO make more efficient 
    private void render() {
        gd.setBlendMode(BlendMode.ONE, BlendMode.ZERO);
        gd.setRenderTarget(null);
        gd.setDepthTestEnabled(true);
        
        Matrix4f projection = camera.getProjectionMatrix(); 
        Matrix4f view = camera.getViewMatrix(); 
        
        // draw opaque 
        gd.setDepthWriteEnabled(true); 
        drawMeshes(opaqueQueue, blinnPhongShader, projection, view); 
        
        // draw transparent 
        drawMeshes(transparentQueue, blinnPhongShader, projection, view); 
        
        // draw translucent 
        gd.setDepthWriteEnabled(false); 
        gd.setBlendMode(BlendMode.SRC_ALPHA, BlendMode.ONE_MINUS_SRC_ALPHA);
        drawMeshes(translucentQueue, blinnPhongShader, projection, view); 
        
        gd.setDepthWriteEnabled(true); 
        gd.setBlendMode(BlendMode.ONE, BlendMode.ZERO);
        
        opaqueQueue.clear(); 
        transparentQueue.clear(); 
        translucentQueue.clear(); 
    }
    
    private void drawMeshes(List<MeshRenderCommand> meshes, Shader defaultShader, Matrix4f projection, Matrix4f view) {
        Shader shader = null; 
        for (MeshRenderCommand c : meshes) {
            shader = c.material.shader; 
            if (shader == null) shader = defaultShader; 
            gd.setShader(shader);
            
            shader.setMatrix4f("Projection", projection);
            shader.setMatrix4f("View", view);
            shader.setMatrix4f("Model", c.matrix);
            shader.setMatrix3f("NormalMat", c.normalMatrix);
            shader.setVector3f("ViewPos", camera.getPosition());
            
            c.material.apply(gd, shader);
            c.mesh.draw(); 
        }
    }
    
    public void draw(Mesh mesh, Material material, Vector3f position, Quaternionf rotation) {
        Matrix4f modelMat = Matrix4f.translation(position)
                .multiply(Matrix4f.rotation(rotation)); 
        
        addMesh(new MeshRenderCommand(mesh, material, modelMat, new Matrix3f(modelMat)), material.renderQueue); 
    }
    
    // TODO normal matrix 
    public void draw(Mesh mesh, Material material, Matrix4f modelMat) {
        addMesh(new MeshRenderCommand(mesh, material, modelMat, new Matrix3f(modelMat)), material.renderQueue); 
    }
    
    public void draw(Model model, Vector3f position, Quaternionf rotation) {
        for (int i = 0; i < model.getPartCount(); i++) {
            draw(model.getMesh(i), model.getMaterial(i), position, rotation); 
        }
    }
    
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
