#anyshader

struct LightData
{
    vec3 Position;
    vec3 Color;
    float Radius;
    int Type;
};

struct MaterialData
{
    vec4 DiffuseColor;
    vec4 SpecularColor;
    vec4 EmissiveColor;
    float SpecularPower;
    int HasDiffuseTexture;
    int HasSpecularTexture;
    int HasEmissiveTexture;
    sampler2D DiffuseTexture;
    sampler2D SpecularTexture;
    sampler2D EmissiveTexture;
};

float Diffuse(vec3 normal, vec3 lightDir)
{
    return clamp(dot(-lightDir, normal), 0.0, 1.0);
}

float Specular(vec3 normal, vec3 lightDir, vec3 cameraDir, float power)
{
    vec3 halfVector = normalize(cameraDir + lightDir);

    return pow(clamp(dot(-halfVector, normal), 0.0, 1.0), power);
}
