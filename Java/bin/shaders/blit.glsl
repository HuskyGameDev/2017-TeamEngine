#version 120 

uniform sampler2D oasis_MainTex; 

varying vec2 v_TexCoord; 

#vertexshader 

attribute vec3 a_Position; 
attribute vec2 a_TexCoord; 

void main() 
{
    gl_Position = vec4(a_Position, 1.0); 
    v_TexCoord = a_TexCoord; 
}

#fragmentshader 

void main() 
{
    gl_FragColor = texture2D(oasis_MainTex, v_TexCoord); 
}