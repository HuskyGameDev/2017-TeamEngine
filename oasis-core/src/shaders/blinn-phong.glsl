#version 120
#include <common.glsl>

varying vec4 vColor;
varying vec3 vNormal;
varying vec3 vModelPos;

uniform mat4 Projection;
uniform mat4 View;
uniform mat4 Model;
uniform mat3 NormalMat;

uniform LightData Light;

uniform vec3 CameraPosition;
uniform vec3 AmbientColor;

uniform MaterialData Material;

#vertexshader

attribute vec3 aPosition; 
attribute vec3 aNormal; 
attribute vec4 aColor; 
attribute vec2 aTexCoord0; 

void main() 
{
	vColor = vec4(1.0); //aColor;
	vNormal = normalize(NormalMat * aNormal); 
	
	gl_Position = Model * vec4(aPosition, 1.0);
	vModelPos = gl_Position.xyz;
	
	gl_Position = Projection * View * gl_Position;
}

#fragmentshader

void main()
{
    vec3 cameraDir = normalize(vModelPos - CameraPosition);
    vec3 normal = normalize(vNormal);
    vec3 lightDir;
    float atten;

    if (Light.Type == 1.0)
    {
        // point light
        lightDir = vModelPos - Light.Position;
        float len = length(lightDir);
        atten = clamp(1.0 - (len * len) / (Light.Radius * Light.Radius), 0.0, 1.0);
        atten *= atten;
        lightDir /= len;
    }
    else
    {
        // directional
        lightDir = normalize(Light.Position);
        atten = 1.0;
    }

    float diffuseAmt = atten * Diffuse(normal, lightDir);
    float specularAmt = atten * Specular(normal, lightDir, cameraDir, Material.SpecularPower);

    // TODO texture colors

    vec4 diffuse = vec4((diffuseAmt * Light.Color.rgb + AmbientColor) * Material.DiffuseColor.rgb * vColor.rgb, Material.DiffuseColor.a * vColor.a);
    vec3 specular = specularAmt * Light.Color * Material.SpecularColor.rgb;
    vec3 emissive = Material.EmissiveColor.rgb * Material.EmissiveColor.a;

    gl_FragColor = vec4(specular + emissive, 0.0) + diffuse;
}
