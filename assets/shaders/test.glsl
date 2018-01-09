#version 120 

#include <common.glsl> 

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

uniform vec4 oasis_AmbientColor; 
uniform vec4 oasis_LightPosition; 
uniform vec3 oasis_LightColor; 
uniform vec3 oasis_LightAttenuation; 

#vertexshader 

attribute vec3 a_Position; 
attribute vec3 a_Normal; 

void main() 
{
    vec4 tmpPos = oasis_ModelMatrix * vec4(a_Position, 1.0); 

    v_Position = tmpPos.xyz; 
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

    gl_FragColor.rgb = (oasis_AmbientColor.rgb + diffuse * oasis_LightColor) * oasis_DiffuseColor.rgb + specular * oasis_LightColor * oasis_SpecularColor.rgb; 
    gl_FragColor.a = 1.0; 
}