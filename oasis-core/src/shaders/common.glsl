#anyshader

float LinearizeDepth(float depth, float near, float far) 
{
    return (2 * near) / (far + near - depth * (far - near)); 
}

float Diffuse(vec3 normal, vec3 lightDir)
{
    return clamp(dot(lightDir, normal), 0.0, 1.0);
}

vec3 DiffuseColor(vec3 normal, vec3 lightDir, vec3 color)
{
    return color * clamp(dot(lightDir, normal), 0.0, 1.0);
}

float Specular(vec3 normal, vec3 lightDir, vec3 cameraDir, float power)
{
    vec3 halfVector = normalize(cameraDir + lightDir);

    return pow(clamp(dot(halfVector, normal), 0.0, 1.0), power);
}

vec3 SpecularColor(vec3 normal, vec3 lightDir, vec3 cameraDir, float power, vec3 color)
{
    vec3 halfVector = normalize(cameraDir + lightDir);

    return color * pow(clamp(dot(halfVector, normal), 0.0, 1.0), power);
}
