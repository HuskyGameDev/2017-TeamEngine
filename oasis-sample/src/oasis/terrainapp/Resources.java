package oasis.terrainapp;

import oasis.audio.AudioClip;
import oasis.audio.AudioSource;
import oasis.core.ResourceManager;
import oasis.graphics.Material;
import oasis.graphics.Mesh;
import oasis.graphics.Shader;
import oasis.math.Vector3;

public class Resources {

    public static Shader bbpShader; 
    
    public static Mesh sphereMesh; 
    public static Mesh terrainMesh; 
    public static Mesh treeMesh; 
    public static Mesh leavesMesh; 
    public static Mesh buildingMesh; 
    public static Mesh houseMesh; 
    
    public static Material stoneMat; 
    public static Material grassMat; 
    public static Material goldMat; 
    public static Material silverMat; 
    public static Material platinumMat; 
    public static Material bluePlasticMat; 
    public static Material pinkRubberMat; 
    public static Material emeraldMat; 
    public static Material sunMat; 
    public static Material leafMat; 
    public static Material woodMat; 
    
    public static AudioClip music; 
    
    public static AudioSource musicSource; 
    
    public static Material[] solidMats; 
    
    public static void load() {
        // shaders 
        
        bbpShader = ResourceManager.loadShader("blinn-phong.glsl");
        
        // meshes
        
        sphereMesh = ResourceManager.loadMesh("texture-sphere.obj");
        terrainMesh = ResourceManager.loadMesh("texture-terrain.obj");
        treeMesh = ResourceManager.loadMesh("realistic-trunk.obj"); 
        leavesMesh = ResourceManager.loadMesh("realistic-leaves.obj"); 
        buildingMesh = ResourceManager.loadMesh("texture-building.obj"); 
        houseMesh = ResourceManager.loadMesh("house.obj"); 
        
        // materials 
        
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
        
        leafMat = new Material(); 
        leafMat.setShader(bbpShader); 
//        leafMat.setDiffuseColor(new Vector3(0.2f, 0.6f, 0.2f));
        leafMat.setDiffuseMap(ResourceManager.loadTexture2D("leaves.jpg"));
        
        woodMat = new Material(); 
        woodMat.setShader(bbpShader); 
        woodMat.setDiffuseMap(ResourceManager.loadTexture2D("bark.jpg"));
        
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
        
        sunMat = new Material(); 
        sunMat.setShader(bbpShader); 
        sunMat.setEmissiveColor(new Vector3(1, 1, 0));
        
        solidMats = new Material[] {
                stoneMat, 
                silverMat, 
                goldMat, 
                platinumMat, 
                emeraldMat, 
        }; 
        
        // sounds 
        
        music = ResourceManager.loadAudioClip("sounds/overworld.wav"); 
        
        musicSource = AudioSource.create(); 
        musicSource.setClip(music);
    }
    
}
