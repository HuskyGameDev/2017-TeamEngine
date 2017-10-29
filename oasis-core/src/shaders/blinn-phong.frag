#version 120 

varying vec4 vColor; 
varying vec3 vNormal; 
varying vec3 vModelPos;

struct LightData
{
    vec3 Position;
    vec3 Color;
    float Radius;
    int Type;
};

uniform LightData Light;
uniform vec3 CameraPosition;

uniform vec3 AmbientColor; 

uniform vec4 DiffuseColor; 
uniform int HasDiffuseTexture = 0; 
uniform sampler2D DiffuseTexture; 

uniform vec4 SpecularColor;
uniform float SpecularPower; 
uniform int HasSpecularTexture = 0; 
uniform sampler2D SpecularTexture; 

uniform vec4 EmissiveColor;
uniform int HasEmissiveTexture = 0;
uniform sampler2D EmissiveTexture;

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
    float specularAmt = atten * Specular(normal, lightDir, cameraDir, SpecularPower);

    // TODO texture colors

    vec4 diffuse = vec4((diffuseAmt * Light.Color.rgb + AmbientColor) * DiffuseColor.rgb * vColor.rgb, DiffuseColor.a * vColor.a);
    vec3 specular = specularAmt * Light.Color * SpecularColor.rgb;
    vec3 emissive = EmissiveColor.rgb;

    gl_FragColor = vec4(specular + emissive, 0.0) + diffuse;
}
