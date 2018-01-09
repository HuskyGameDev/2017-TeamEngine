#anyshader

float Diffuse(vec3 normal, vec3 lightDir)
{
    return clamp(dot(lightDir, normal), 0.0, 1.0);
}

float Specular(vec3 normal, vec3 lightDir, vec3 cameraDir, float power)
{
    vec3 halfVector = normalize(cameraDir + lightDir);

    return pow(clamp(dot(halfVector, normal), 0.0, 1.0), power);
}
