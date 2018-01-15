#version 120 

uniform mat4 oasis_ShadowMVP; 

#vertexshader 

attribute vec3 a_Position; 

void main() 
{
    gl_Position = oasis_ShadowMVP * vec4(a_Position, 1.0); 
}

#fragmentshader 

void main() {} 