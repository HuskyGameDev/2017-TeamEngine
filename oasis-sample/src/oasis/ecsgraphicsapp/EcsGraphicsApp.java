package oasis.ecsgraphicsapp;

import oasis.audio.AudioClip;
import oasis.audio.AudioSource;
import oasis.core.Application;
import oasis.core.BackendType;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.core.ResourceManager;
import oasis.entity.Camera;
import oasis.entity.Entity;
import oasis.entity.EntityManager;
import oasis.entity.Light;
import oasis.entity.LightRenderer;
import oasis.entity.MeshContainer;
import oasis.entity.MeshRenderer;
import oasis.entity.Transform;
import oasis.graphics.Graphics;
import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.graphics.Shader;
import oasis.input.Keyboard;
import oasis.math.Mathf;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class EcsGraphicsApp implements Application {

    private EntityManager entityManager; 
    
    private Vector3 ambientColor; 
    
    private Shader bbpShader; 
    private Mesh sphereMesh; 
    private Mesh terrainMesh; 
    private Material stoneMat; 
    private Material grassMat; 
    private Material goldMat; 
    private Material silverMat; 
    private Material platinumMat; 
    private Material bluePlasticMat; 
    private Material pinkRubberMat; 
    private Material emeraldMat; 
    
    private AudioClip music; 
//    private AudioClip sfx; 
    private AudioSource source1; 
//    private AudioSource source2; 
    
    public static void main(String[] args) {
        Config conf = new Config(); 
        conf.backend = BackendType.AUTO; 
        conf.ups = 59.97f; 
        conf.fps = 60.0f; 
        
        Oasis.start(conf, new EcsGraphicsApp());
    }
    
    @Override
    public void init() {
        Oasis.getDisplay().setResizable(true); 
        
        loadShaders(); 
        loadMaterials(); 
        loadMeshes(); 
        loadSounds(); 
        
        entityManager = new EntityManager(); 
        
        entityManager.registerComponent(Transform.class); 
        entityManager.registerComponent(MeshContainer.class); 
        entityManager.registerComponent(Spring.class); 
        entityManager.registerComponent(Camera.class);
        entityManager.registerComponent(FpsCamera.class); 
        entityManager.registerComponent(Light.class); 
        entityManager.registerComponent(SunLightTag.class); 
        
        entityManager.addBehavior(new MeshRenderer()); 
        entityManager.addBehavior(new LightRenderer()); 
        entityManager.addBehavior(new SpringBehavior()); 
        entityManager.addBehavior(new FpsCameraController());
        entityManager.addBehavior(new SunLightRotator()); 
        
        createCameraEntity(); 
        
        createSunLightEntity(); 
        
        createMeshEntity(false, new Vector3(0, 0, 0), terrainMesh, grassMat); 
        
        Material[] mats = new Material[] {
                stoneMat, grassMat, goldMat, silverMat, platinumMat, bluePlasticMat, pinkRubberMat, emeraldMat
        }; 
        
        for (int i = -5; i < 5; i++) {
            for (int j = -5; j < 5; j++) {
                createMeshEntity(true, new Vector3(i * 2.2f, 10, j * 2.2f), sphereMesh, mats[(int) (Math.random() * mats.length)]); 
            }
        }
        
        ambientColor = new Vector3(0.2f); 
        
        Oasis.getMouse().setCursorVisible(false); 
    }

    @Override
    public void update(float dt) {
        Keyboard keys = Oasis.getKeyboard(); 
        if (keys.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Oasis.stop();
        }
        
        entityManager.update(dt); 
    }

    @Override
    public void render() {
        Graphics g = Oasis.getGraphics();
        
        g.begin(); 
        
        g.addAmbient(ambientColor); 
        
        entityManager.render(); 
        
        g.finish(); 
    }

    @Override
    public void exit() {
        
    }

    @Override
    public boolean closeAttempt() {
        return true; 
    }
    
    private Entity createSunLightEntity() {
        Entity e = entityManager.createEntity(); 
        
        Transform t = e.add(Transform.class); 
        Light l = e.add(Light.class); 
        e.add(SunLightTag.class); 
        
        t.setRotation(Quaternion.direction(new Vector3(0, -1, -1)));
        l.setColor(new Vector3(0.8f));
        l.setType(Light.Type.DIRECTIONAL); 
        
        return e; 
    }
    
    private Entity createCameraEntity() {
        Entity e = entityManager.createEntity(); 
        
        Camera c = e.add(Camera.class); 
        Transform t = e.add(Transform.class); 
        e.add(FpsCamera.class); 
        
        t.setPosition(new Vector3(0, 3, 10));
        c.setFov(Mathf.toRadians(70.0f)); 
        t.setRotation(Quaternion.direction(new Vector3(0, 0, -1f)));
        
        return e; 
    }
    
    private Entity createMeshEntity(boolean move, Vector3 position, Mesh mesh, Material material) {
        Entity e = entityManager.createEntity(); 
        
        if (move) {
            Spring s = e.add(Spring.class); 
            s.origin.set(position); 
            s.time = (float) Math.random() * 10; 
            s.speed = (float) Math.random(); 
        }
        
        Transform t = e.add(Transform.class); 
        MeshContainer mc = e.add(MeshContainer.class); 
        
        t.setPosition(position); 
        mc.setMesh(mesh); 
        mc.setMaterial(material); 
        
        return e; 
    }
    
    private void loadShaders() {
        bbpShader = ResourceManager.loadShader("blinn-phong.glsl");
    }
    
    private void loadMeshes() {
        sphereMesh = ResourceManager.loadMesh("texture-sphere.obj");
        terrainMesh = ResourceManager.loadMesh("texture-terrain.obj"); 
    }
    
    private void loadMaterials() {
        stoneMat = new Material(); 
        stoneMat.setShader(bbpShader); 
        stoneMat.setDiffuseColor(new Vector3(0.5f, 0.5f, 0.5f));
        stoneMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/164_norm.JPG")); 
        stoneMat.setSpecularColor(new Vector3(0.1f)); 
        stoneMat.setSpecularPower(20); 
        
        goldMat = new Material(); 
        goldMat.setShader(bbpShader); 
        goldMat.setDiffuseColor(new Vector3(0.752f, 0.606f, 0.226f));
        goldMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/160_norm.JPG")); 
        goldMat.setSpecularColor(new Vector3(0.628f, 0.556f, 0.366f)); 
        goldMat.setSpecularPower(128 * 0.4f); 
        
        platinumMat = new Material(); 
        platinumMat.setShader(bbpShader); 
        platinumMat.setDiffuseColor(new Vector3(0.7f, 0.7f, 0.8f));
        platinumMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/163_norm.JPG")); 
        platinumMat.setSpecularColor(new Vector3(0.7f)); 
        platinumMat.setSpecularPower(10); 
        
        silverMat = new Material(); 
        silverMat.setShader(bbpShader); 
        silverMat.setDiffuseColor(new Vector3(0.608f, 0.608f, 0.608f));
        silverMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/170_norm.JPG")); 
        silverMat.setSpecularColor(new Vector3(0.508f, 0.508f, 0.508f)); 
        silverMat.setSpecularPower(128 * 0.4f); 
        
        bluePlasticMat = new Material(); 
        bluePlasticMat.setShader(bbpShader); 
        bluePlasticMat.setDiffuseColor(new Vector3(0.0f, 0.510f, 0.510f));
        bluePlasticMat.setSpecularColor(new Vector3(0.502f));
        bluePlasticMat.setSpecularPower(128 * 0.25f);
        
        pinkRubberMat = new Material(); 
        pinkRubberMat.setShader(bbpShader); 
        pinkRubberMat.setDiffuseColor(new Vector3(0.6f, 0.4f, 0.4f));
        pinkRubberMat.setSpecularColor(new Vector3(0.4f, 0.04f, 0.04f));
        pinkRubberMat.setSpecularPower(128 * 0.078f);
        
        emeraldMat = new Material(); 
        emeraldMat.setShader(bbpShader); 
        emeraldMat.setDiffuseColor(new Vector3(0.076f, 0.614f, 0.076f)); 
        emeraldMat.setNormalMap(ResourceManager.loadTexture2D("diffuse-and-normals/161_norm.JPG")); 
        emeraldMat.setSpecularColor(new Vector3(0.633f, 0.728f, 0.633f));
        emeraldMat.setSpecularPower(128 * 0.6f);
        
        grassMat = new Material(); 
        grassMat.setShader(bbpShader); 
        grassMat.setDiffuseMap(ResourceManager.loadTexture2D("grass.jpg")); 
    }

    private void loadSounds() {
        music = ResourceManager.loadAudioClip("sounds/overworld.wav"); 
//        sfx = ResourceManager.loadAudioClip("sounds/pitfall.wav"); 
        
        source1 = AudioSource.create(); 
        source1.setClip(music);
        source1.play(); 
        
//        source2 = AudioSource.create(); 
//        source2.setClip(sfx); 
    }
    
}
