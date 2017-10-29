#version 120 

attribute vec3 aPosition; 
attribute vec3 aNormal; 
attribute vec4 aColor; 
attribute vec2 aTexCoord0; 

uniform mat4 Projection; 
uniform mat4 View; 
uniform mat4 Model; 
uniform mat3 NormalMat; 

varying vec4 vColor; 
varying vec3 vNormal; 
varying vec3 vModelPos;

void main() 
{
	vColor = aColor; 
	vNormal = normalize(NormalMat * aNormal); 
	
	gl_Position = Model * vec4(aPosition, 1.0);
	vModelPos = gl_Position.xyz;
	
	gl_Position = Projection * View * gl_Position;
}
