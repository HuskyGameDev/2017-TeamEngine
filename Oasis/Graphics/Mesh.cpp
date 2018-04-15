#include "Oasis/Graphics/Mesh.h"

namespace Oasis
{

Submesh::Submesh()
{

}

Submesh::~Submesh()
{

}

Mesh::Mesh()
    : m_updateVertices(false)
    , m_updateIndices(false)
    , m_vertexCount(0)
    , m_positions()
    , m_normals()
    , m_texCoords()
    , m_tangents()
    , m_vertexBuffer(NULL)
    , m_submeshes()
{

}

Mesh::~Mesh()
{
    Release();
}

void Mesh::Release()
{
    m_vertexBuffer->Release();


}

void Mesh::Clear()
{

}

void Mesh::Upload()
{

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
