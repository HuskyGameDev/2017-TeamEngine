#version 120 

varying vec2 v_TexCoord; 

uniform mat4 oasis_ProjectionMatrix; 
uniform mat4 oasis_ViewMatrix; 
uniform mat4 oasis_ModelMatrix; 

uniform sampler2D oasis_DiffuseTexture; 

#vertexshader 

attribute vec3 a_Position; 
attribute vec2 a_TexCoord; 

void main() 
{
    gl_Position = oasis_ProjectionMatrix * oasis_ViewMatrix * oasis_ModelMatrix * vec4(a_Position, 1.0); 
    v_TexCoord = a_TexCoord; 
}

#fragmentshader 

void main() 
{
    gl_FragColor = texture2D(oasis_DiffuseTexture, v_TexCoord); 
}