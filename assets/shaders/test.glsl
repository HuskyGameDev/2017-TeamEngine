#version 120 

#include <common.glsl> 

varying vec2 v_TexCoord; 
varying vec3 v_Position; 
varying vec3 v_Normal; 

uniform mat4 oasis_ProjectionMatrix; 
uniform mat4 oasis_ViewMatrix; 
uniform mat4 oasis_ModelMatrix; 
uniform mat3 oasis_NormalMatrix; 
uniform vec3 oasis_CameraPosition; 

uniform vec4 oasis_DiffuseColor; 
uniform vec4 oasis_SpecularColor; 
uniform vec3 oasis_EmissiveColor; 

uniform sampler2D oasis_DiffuseTexture; 
uniform int oasis_HasDiffuseTexture; 

uniform vec4 oasis_AmbientColor; 
uniform vec4 oasis_LightPosition; 
uniform vec3 oasis_LightColor; 
uniform vec3 oasis_LightAttenuation; 

#vertexshader 

attribute vec3 a_Position; 
attribute vec3 a_Normal; 
attribute vec2 a_TexCoord; 

void main() 
{
    vec4 tmpPos = oasis_ModelMatrix * vec4(a_Position, 1.0); 

    v_Position = tmpPos.xyz; 
    v_TexCoord = a_TexCoord; 
    v_Normal = normalize(oasis_NormalMatrix * a_Normal); 
    gl_Position = oasis_ProjectionMatrix * oasis_ViewMatrix * tmpPos; 
}

#fragmentshader 

void main() 
{
    vec3 lightDir; 
    
    if (oasis_LightPosition.w == 0) 
    {
        lightDir = -normalize(oasis_LightPosition.xyz); 
    }
    else 
    {
        lightDir = normalize(oasis_LightPosition.xyz - v_Position); 
    } 
    
    vec3 cameraDir = normalize(oasis_CameraPosition - v_Position); 

    float diffuse = Diffuse(v_Normal, lightDir); 
    float specular = Specular(v_Normal, lightDir, cameraDir, oasis_SpecularColor.a); 

    vec3 diffuseInput = oasis_DiffuseColor.rgb; 
    
    if (oasis_HasDiffuseTexture == 1) 
    {
        diffuseInput *= texture2D(oasis_DiffuseTexture, v_TexCoord).rgb; 
    }

    gl_FragColor.rgb = (oasis_AmbientColor.rgb + diffuse * oasis_LightColor) * diffuseInput + specular * oasis_LightColor * oasis_SpecularColor.rgb; 
    gl_FragColor.a = 1.0; 
}