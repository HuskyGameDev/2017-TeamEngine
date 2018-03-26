#version 120 

#include <common.glsl> 

// matrices 
uniform mat4 oasis_Projection; 
uniform mat4 oasis_ModelView; 
uniform mat3 oasis_Normal; 

// material 
uniform vec4 oasis_Diffuse; 
uniform vec4 oasis_Specular; 
uniform vec3 oasis_Emissive; 
uniform sampler2D oasis_DiffuseMap; 
uniform sampler2D oasis_NormalMap; 

// light 
uniform vec3 oasis_Ambient; 
uniform vec4 oasis_LightPosition; 
uniform vec3 oasis_LightColor; 
uniform vec3 oasis_LightAttenuation; 
uniform mat4 oasis_ShadowMVP; 
uniform sampler2DShadow oasis_ShadowMap; 

// varying 
varying vec3 v_Position; 
varying vec3 v_Normal; 
varying vec2 v_TexCoord; 
varying mat3 v_TBN; 
varying vec4 v_ShadowCoord; 

#vertexshader 

// atributes 
attribute vec3 a_Position; 
attribute vec3 a_Normal; 
attribute vec2 a_TexCoord; 
attribute vec3 a_Tangent; 

// vertex shader main function 
void main() 
{
    vec4 mvPos = oasis_ModelView * vec4(a_Position, 1.0); 
    
    v_ShadowCoord = oasis_ShadowMVP * vec4(a_Position, 1.0); 
    
    // position 
    v_Position = mvPos.xyz; 
    gl_Position = oasis_Projection * mvPos; 
    
    // normal 
    v_Normal = oasis_Normal * a_Normal; 
    
    // tex coord 
    v_TexCoord = a_TexCoord; 
    
    // TBN 
    vec3 tangent = normalize(oasis_Normal * a_Tangent); 
    vec3 bitangent = normalize(cross(v_Normal, tangent)); 
    v_TBN = mat3(
        tangent, 
        bitangent, 
        v_Normal
    );
}

#fragmentshader 

const int SAMPLE_COUNT = 9; 

const float INV_RADIUS = 1.0 / (1024.0 * 8.0);

const vec2 KERNAL[SAMPLE_COUNT] = vec2[]
(
    vec2(0.95581, -0.18159), vec2(0.50147, -0.35807), vec2(0.69607, 0.35559),
   vec2(-0.0036825, -0.59150),	vec2(0.15930, 0.089750), vec2(-0.65031, 0.058189),
   vec2(0.11915, 0.78449),	vec2(-0.34296, 0.51575), vec2(-0.60380, -0.41527)
); 

const vec3 FOG_COLOR = vec3(0.5, 0.6, 0.8); 
const float FOG_START = 0.3; 
const float FOG_END = 0.5; 

void Sample(in vec3 coords, in vec2 offset, inout float factor, inout float numSamples) 
{
    factor += shadow2D(oasis_ShadowMap, vec3(coords.xy + offset, coords.z)).r; 
    numSamples += 1.0; 
}

// fragment shader main function 
void main() 
{
    vec3 N = normalize(v_TBN * normalize(texture2D(oasis_NormalMap, v_TexCoord).rgb * 2.0 - 1.0));
    
    vec3 L; 
    
    if (oasis_LightPosition.w == 0) 
    {
        // directional 
        L = -oasis_LightPosition.xyz; 
    }
    else 
    {
        // point
        L = oasis_LightPosition.xyz - v_Position; 
    }
    
    vec3 V = normalize(-v_Position); 
    
    vec3 diffuse = oasis_LightColor * Diffuse(N, L); 
    vec3 specular = oasis_LightColor * Specular(N, L, V, oasis_Specular.a); 
    vec3 ambient = oasis_Ambient; 
    
    float amt; 
    vec3 coord = v_ShadowCoord.xyz; 
    coord.z -= 0.0001; 
    if (clamp(coord, 0.0, 1.0) == coord) 
    {
        float numSamples = 0.0; 
        amt = 0.0; 
        for (int i = 0; i < SAMPLE_COUNT; i++) 
        {
            Sample(coord, KERNAL[i] * INV_RADIUS, amt, numSamples); 
        }
        amt /= numSamples; 
        //amt = shadow2D(oasis_ShadowMap, coord).r; 
    }
    else 
    {
        amt = 1.0; 
    }
    diffuse *= amt; 
    specular *= amt; 
    
    vec3 diffuseMat = oasis_Diffuse.rgb * texture2D(oasis_DiffuseMap, v_TexCoord).rgb; 
    vec3 specularMat = oasis_Specular.rgb; 
    
    vec3 fragCol = diffuseMat * (ambient + diffuse) + specular * specularMat + oasis_Emissive; 
    
    float depth = LinearizeDepth(gl_FragCoord.z, 0.1, 1000.0); 
    
    float fogAmt = 1.0 - (FOG_END - depth) / (FOG_END - FOG_START); 
    
    gl_FragColor.rgb = mix(fragCol, FOG_COLOR, clamp(fogAmt, 0.0, 1.0)); 
    gl_FragColor.a = 1.0; 
}
