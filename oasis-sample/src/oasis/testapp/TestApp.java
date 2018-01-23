package oasis.testapp;

import oasis.audio.AudioClip;
import oasis.audio.AudioSource;
import oasis.core.BackendType;
import oasis.core.BasicGame;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.core.ResourceManager;
import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.graphics.Shader;
import oasis.input.Keyboard;
import oasis.math.Vector3;

public class TestApp extends BasicGame {

    private EntityFactory factory; 
    
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
    private AudioSource source1; 
    
    public static void main(String[] args) {
        Config conf = new Config(); 
        conf.backend = BackendType.AUTO; 
        conf.ups = 59.97f; 
        conf.fps = 60.0f; 
        
        Oasis.start(conf, new TestApp());
    }
    
    @Override
    public void initGame() {
        factory = new EntityFactory(entityManager); 
        
        loadShaders(); 
        loadMaterials(); 
        loadMeshes(); 
        loadSounds(); 
        
        entityManager.registerComponent(Spring.class); 
        entityManager.registerComponent(FpsCamera.class); 
        entityManager.registerComponent(SunLightTag.class); 
        
        entityManager.addBehavior(new SpringBehavior()); 
        entityManager.addBehavior(new FpsCameraController());
        entityManager.addBehavior(new SunLightRotator()); 
        
        factory.createCameraEntity(); 
        factory.createSunLightEntity(); 
        factory.createMeshEntity(false, new Vector3(0, 0, 0), terrainMesh, grassMat); 
        
        Material[] mats = new Material[] {
                stoneMat, grassMat, goldMat, silverMat, platinumMat, bluePlasticMat, pinkRubberMat, emeraldMat
        }; 
        
        for (int i = -5; i < 5; i++) {
            for (int j = -5; j < 5; j++) {
                factory.createMeshEntity(true, new Vector3(i * 2.2f, 10, j * 2.2f), sphereMesh, mats[(int) (Math.random() * mats.length)]); 
            }
        }
        
        ambientColor = new Vector3(0.2f); 
        
        Oasis.getMouse().setCursorVisible(false); 
        
        source1.play(); 
    }

    @Override
    public void preUpdateGame(float dt) {
        Keyboard keys = Oasis.getKeyboard(); 
        if (keys.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Oasis.stop();
        }
    }

    @Override
    public void preRenderGame() {
        Oasis.getGraphics().addAmbient(ambientColor); 
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
        
        source1 = AudioSource.create(); 
        source1.setClip(music);
    }
    
}
