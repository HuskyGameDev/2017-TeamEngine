#pragma once

#include "Oasis/Oasis.h"

#include "Oasis/Math/Matrix3.h"
#include "Oasis/Math/Matrix4.h"

#include <vector>

namespace Oasis
{

class Material;
class Mesh;

struct OASIS_API RenderMeshData
{
    Matrix4 modelMat;
    Matrix3 normalMat;
    Mesh* mesh;
    Material* material;
    int index;
};

class OASIS_API Renderer
{
public:
    Renderer();
    ~Renderer();

    void Begin();
    void Finish();

    void DrawMesh(Mesh* mesh, int index, Material* mat, const Matrix4& modelMat, const Matrix3& normalMat);

private:
    std::vector<RenderMeshData> m_renderMeshData;
};

}
