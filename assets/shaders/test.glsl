#version 120 

#include <common.glsl> 

varying vec2 v_TexCoord; 
varying vec3 v_Position; 
varying vec3 v_Normal; 
varying mat3 v_Tbn; 

uniform mat4 oa_ProjectionMatrix; 
uniform mat4 oa_ViewMatrix; 
uniform mat4 oa_ModelMatrix; 
uniform mat3 oa_NormalMatrix; 
uniform vec3 oa_CameraPosition; 

uniform vec4 oa_DiffuseColor; 
uniform vec4 oa_SpecularColor; 
uniform vec3 oa_EmissiveColor; 

uniform sampler2D oa_DiffuseTexture; 
uniform int oa_HasDiffuseTexture; 

uniform vec4 oa_AmbientColor; 
uniform vec4 oa_LightPosition; 
uniform vec3 oa_LightColor; 
uniform vec3 oa_LightAttenuation; 

#vertexshader 

attribute vec3 a_Position; 
attribute vec3 a_Normal; 
attribute vec2 a_TexCoord; 
attribute vec3 a_Tangent; 

void main() 
{
    vec4 tmpPos = oa_ModelMatrix * vec4(a_Position, 1.0); 

    v_Position = tmpPos.xyz; 
    v_TexCoord = a_TexCoord; 
    vec3 v_Normal = normalize(oa_NormalMatrix * a_Normal); 
    gl_Position = oa_ProjectionMatrix * oa_ViewMatrix * tmpPos; 
    
    vec3 tangent = normalize(a_Tangent); 
    vec3 bitangent = normalize(cross(v_Normal, tangent)); 
    
    v_Tbn = transpose(mat3(vec3(0.0), vec3(0.0), v_Normal)); 
}

#fragmentshader 

void main() 
{
    vec3 normal = v_Normal; //v_Tbn * vec3(0, 0, 1); //normalize(texture2D(oa_DiffuseTexture, v_TexCoord).rgb * 2.0 - 1.0); 
    vec3 lightDir; 
    
    if (oa_LightPosition.w == 0) 
    {
        lightDir = -normalize(oa_LightPosition.xyz); 
    }
    else 
    {
        lightDir = normalize(oa_LightPosition.xyz - v_Position); 
    } 
    
    vec3 cameraDir = normalize(oa_CameraPosition - v_Position); 

    float diffuse = Diffuse(normal, lightDir); 
    float specular = Specular(normal, lightDir, cameraDir, oa_SpecularColor.a); 

    vec3 diffuseInput = oa_DiffuseColor.rgb; 
    
    if (oa_HasDiffuseTexture == 1) 
    {
        diffuseInput *= texture2D(oa_DiffuseTexture, v_TexCoord).rgb; 
    }

    gl_FragColor.rgb = (oa_AmbientColor.rgb + diffuse * oa_LightColor) * diffuseInput + specular * oa_LightColor * oa_SpecularColor.rgb; 
    gl_FragColor.a = 1.0; 
}