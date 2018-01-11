#version 120 

#include <common.glsl> 

// varying 
varying vec3 v_Position; 
varying vec3 v_Normal; 
varying vec2 v_TexCoord; 
varying mat3 v_TBN; 

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
    v_TBN = (mat3(
        tangent, 
        bitangent, 
        v_Normal
    )); 
}

#fragmentshader 

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
    
    vec3 V = vec3(0, 0, 1); 
    
    vec3 diffuse = oasis_LightColor * Diffuse(N, L); 
    vec3 specular = oasis_LightColor * Specular(N, L, V, oasis_Specular.a); 
    vec3 ambient = oasis_Ambient; 
    
    vec3 diffuseMat = oasis_Diffuse.rgb * texture2D(oasis_DiffuseMap, v_TexCoord).rgb; 
    vec3 specularMat = oasis_Specular.rgb; 
    
    gl_FragColor.rgb = diffuseMat * (ambient + diffuse) + specular * specularMat; 
    gl_FragColor.a = 1.0; 
}