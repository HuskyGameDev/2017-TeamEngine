#version 120 

varying vec4 vColor; 
varying vec3 vNormal; 
varying vec3 vModelPos;

uniform vec3 LightDirection = vec3(-1.0, -1.0, 0.0);
uniform vec3 CameraPosition;

uniform vec3 AmbientColor; 

uniform vec4 DiffuseColor; 
uniform int HasDiffuseTexture = 0; 
uniform sampler2D DiffuseTexture; 

uniform vec4 SpecularColor; 
uniform float SpecularPower; 
uniform int HasSpecularTexture = 0; 
uniform sampler2D SpecularTexture; 

float Diffuse(vec3 normal, vec3 lightDir) 
{
	return clamp(dot(-lightDir, normal), 0.0, 1.0);
}

float Specular(vec3 normal, vec3 lightDir, vec3 cameraDir, float power) 
{
	vec3 halfVector = normalize(cameraDir + lightDir);
	
	return pow(clamp(dot(-halfVector, normal), 0.0, 1.0), power);
}

void main() 
{
	vec3 cameraDir = normalize(vModelPos - CameraPosition);
	vec3 normal = normalize(vNormal); 
	vec3 lightDir = normalize(LightDirection); 
	
	float diffuseAmt = Diffuse(normal, lightDir); 
	float specularAmt = Specular(normal, lightDir, cameraDir, SpecularPower); 
	
	// TODO texture colors 
	
	vec4 diffuse = vec4(DiffuseColor.rgb * diffuseAmt * vColor.rgb, DiffuseColor.a * vColor.a); 
	vec3 specular = SpecularColor.rgb * specularAmt * SpecularColor.a; 
	
	gl_FragColor = vec4(AmbientColor + specular, 0.0) + diffuse; 
}
