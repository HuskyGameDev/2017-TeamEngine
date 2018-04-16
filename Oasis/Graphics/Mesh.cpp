#include "Oasis/Graphics/Mesh.h"

namespace Oasis
{

Mesh::Mesh()
    : m_updateVertices(true)
    , m_updateIndices(true)
    , m_vertexCount(0)
    , m_positions()
    , m_normals()
    , m_texCoords()
    , m_tangents()
    , m_vertexBuffer(NULL)
    , m_submeshes() {}

Mesh::~Mesh()
{
    Release();
}

void Mesh::Release()
{
    m_updateIndices = true;
    m_updateVertices = true;

    for (int i = 0; i < m_submeshes.size(); i++)
    {
        Submesh& sm = m_submeshes[i];

        if (sm.geometry) sm.geometry->Release();
        if (sm.indexBuffer) sm.indexBuffer->Release();
    }

    m_submeshes.clear();

    if (m_vertexBuffer)
    {
        m_vertexBuffer->Release();
        delete m_vertexBuffer;
        m_vertexBuffer = NULL;
    }
}

void Mesh::Clear()
{
    // clear vertices
    m_vertexCount = 0;
    m_positions.clear();
    m_normals.clear();
    m_texCoords.clear();
    m_tangents.clear();
    m_updateVertices = true;

    // clear indices
    for (int i = 0; i < m_submeshes.size(); i++)
    {
        Submesh& sm = m_submeshes[i];

        if (sm.geometry) sm.geometry->Release();
        if (sm.indexBuffer) sm.indexBuffer->Release();
    }

    m_submeshes.clear();
    m_updateIndices = true;
}

void Mesh::Upload()
{
    // TODO finish
}

bool Mesh::CalculateNormals()
{

}

bool Mesh::CalculateTangents()
{

}

void Mesh::SetVertexCount(int count)
{

}

void Mesh::SetPositions(const Vector3* positions)
{

}

void Mesh::SetNormals(const Vector3* normals)
{

}

void Mesh::SetTexCoord(const Vector2* texCoords)
{

}

void Mesh::SetTangents(const Vector3* tangents)
{

}

int Mesh::GetVertexCount() const
{

}

void Mesh::GetPositions(int start, int count, Vector3* in) const
{

}

void Mesh::GetNormals(int start, int count, Vector3* in) const
{

}

void Mesh::GetTexCoords(int start, int count, Vector2* in) const
{

}

void Mesh::GetTangents(int start, int count, Vector3* in) const
{

}

VertexBuffer* Mesh::GetVertexBuffer()
{

}

void Mesh::SetSubmeshCount(int count)
{

}

void Mesh::SetIndexCount(int submesh, int count)
{

}

void Mesh::SetIndices(const short* indices)
{

}

int Mesh::GetSubmeshCount() const
{

}

int Mesh::GetIndexCount(int submesh) const
{

}

int Mesh::GetIndices(int start, int count, short* in) const
{

}

Geometry* Mesh::GetGeometry(int submesh)
{

}

IndexBuffer* Mesh::GetIndexBuffer(int submesh)
{

}

}
