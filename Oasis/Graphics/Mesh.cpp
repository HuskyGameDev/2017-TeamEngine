#include "Oasis/Graphics/Mesh.h"

#include <iostream>

using namespace std;

namespace Oasis
{

Submesh::Submesh()
    : update(true)
    , vertexArray(NULL)
    , indexBuffer(NULL)
    , primitive(PRIMITIVE_TRIANGLE_LIST)
    , indices() {}

Mesh::Mesh()
    : m_updateVertices(true)
    , m_vertexCount(0)
    , m_positions()
    , m_normals()
    , m_texCoords()
    , m_tangents()
    , m_vertexBuffer(NULL)
    , m_submeshes()
{
    SetSubmeshCount(1);
}

Mesh::~Mesh()
{
    Release();
}

void Mesh::Release()
{
    m_updateVertices = true;

    for (unsigned i = 0; i < m_submeshes.size(); i++)
    {
        Submesh& sm = m_submeshes[i];

        if (sm.vertexArray) sm.vertexArray->Release();
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

    // clear and remove indices
    for (unsigned i = 0; i < m_submeshes.size(); i++)
    {
        Submesh& sm = m_submeshes[i];

        if (sm.vertexArray) sm.vertexArray->Release();
        if (sm.indexBuffer) sm.indexBuffer->Release();
    }

    m_submeshes.clear();
    SetSubmeshCount(1);
}

void Mesh::Upload()
{
    Graphics* g = Engine::GetGraphics();
    // TODO finish

    if (m_updateVertices)
    {
        if (m_positions.size())
        {
            int floatsPerElement = 3;

            VertexFormat format;
            format.AddAttribute(ATTRIBUTE_POSITION);

            if (m_normals.size()) { floatsPerElement += 3; format.AddAttribute(ATTRIBUTE_NORMAL); }
            if (m_texCoords.size()) { floatsPerElement += 2; format.AddAttribute(ATTRIBUTE_TEXTURE); }
            if (m_tangents.size()) { floatsPerElement += 3; format.AddAttribute(ATTRIBUTE_TANGENT); }

            if (!m_vertexBuffer)
            {
                m_vertexBuffer = g->CreateVertexBuffer(m_vertexCount, format);
            }
            else
            {
                m_vertexBuffer->SetElementCount(m_vertexCount);
            }

            float element[floatsPerElement];

            for (int i = 0; i < m_vertexCount; i++)
            {
                int j = 0;

                element[j++] = m_positions[i].x;
                element[j++] = m_positions[i].y;
                element[j++] = m_positions[i].z;

                if (m_normals.size())
                {
                    element[j++] = m_normals[i].x;
                    element[j++] = m_normals[i].y;
                    element[j++] = m_normals[i].z;
                }

                if (m_texCoords.size())
                {
                    element[j++] = m_texCoords[i].x;
                    element[j++] = m_texCoords[i].y;
                }

                if (m_tangents.size())
                {
                    element[j++] = m_tangents[i].x;
                    element[j++] = m_tangents[i].y;
                    element[j++] = m_tangents[i].z;
                }

                m_vertexBuffer->SetData(i, 1, element);
            }

            m_vertexBuffer->Upload();
        }
        else if (m_vertexBuffer)
        {
            m_vertexBuffer->Release();
            delete m_vertexBuffer;
            m_vertexBuffer = NULL;
        }
    }

    // set buffers of all submeshes
    for (unsigned i = 0; i < m_submeshes.size(); i++)
    {
        Submesh& s = m_submeshes[i];

        if (!s.indexBuffer) s.indexBuffer = g->CreateIndexBuffer(s.indices.size());
        s.indexBuffer->SetData(0, s.indices.size(), &s.indices[0]);
        s.indexBuffer->Upload();

        if (!s.vertexArray) s.vertexArray = g->CreateVertexArray();
        s.vertexArray->SetVertexBuffer(m_vertexBuffer);
        s.vertexArray->SetIndexBuffer(s.indexBuffer);
        s.vertexArray->Upload();
    }
}

bool Mesh::CalculateNormals()
{
    // TODO finish
    return false;
}

bool Mesh::CalculateTangents()
{
    // TODO finish
    return false;
}

void Mesh::ClearAttributes()
{
    m_positions.clear();
    m_normals.clear();
    m_texCoords.clear();
    m_tangents.clear();
}

void Mesh::SetPositions(int count, const Vector3* positions)
{
    m_updateVertices = true;

    if (m_vertexCount != count)
    {
        m_vertexCount = count;
        ClearAttributes();
    }

    m_positions.clear();
    for (int i = 0; i < count; i++) m_positions.push_back(positions[i]);
}

void Mesh::SetNormals(const Vector3* normals)
{
    m_updateVertices = true;

    m_normals.clear();
    for (int i = 0; i < m_vertexCount; i++) m_normals.push_back(normals[i]);
}

void Mesh::SetTexCoord(const Vector2* texCoords)
{
    m_updateVertices = true;

    m_texCoords.clear();
    for (int i = 0; i < m_vertexCount; i++) m_texCoords.push_back(texCoords[i]);
}

void Mesh::SetTangents(const Vector3* tangents)
{
    m_updateVertices = true;

    m_tangents.clear();
    for (int i = 0; i < m_vertexCount; i++) m_tangents.push_back(tangents[i]);
}

int Mesh::GetVertexCount() const
{
    return m_vertexCount;
}

void Mesh::GetPositions(int start, int count, Vector3* in) const
{
    for (int i = 0; i < count; i++)
    {
        in[i] = m_positions[i + start];
    }
}

void Mesh::GetNormals(int start, int count, Vector3* in) const
{
    for (int i = 0; i < count; i++)
    {
        in[i] = m_normals[i + start];
    }
}

void Mesh::GetTexCoords(int start, int count, Vector2* in) const
{
    for (int i = 0; i < count; i++)
    {
        in[i] = m_texCoords[i + start];
    }
}

void Mesh::GetTangents(int start, int count, Vector3* in) const
{
    for (int i = 0; i < count; i++)
    {
        in[i] = m_tangents[i + start];
    }
}

VertexBuffer* Mesh::GetVertexBuffer()
{
    return m_vertexBuffer;
}

void Mesh::SetSubmeshCount(int count)
{
    int curSize = (int) m_submeshes.size();

    for (int i = curSize; i > count; i--)
    {
        // remove more than count submeshes
        m_submeshes.pop_back();
    }

    curSize = (int) m_submeshes.size();

    for (int i = curSize; i < count; i++)
    {
        // add submeshes to make count correct
        m_submeshes.push_back(Submesh());
    }
}

void Mesh::SetIndices(int submesh, int count, const short* indices)
{
    Submesh& s = m_submeshes[submesh];
    s.update = true;

    s.indices.clear();

    for (int i = 0; i < count; i++)
    {
        s.indices.push_back(indices[i]);
    }
}

int Mesh::GetSubmeshCount() const
{
    return m_submeshes.size();
}

int Mesh::GetIndexCount(int submesh) const
{
    return m_submeshes[submesh].indices.size();
}

void Mesh::GetIndices(int submesh, int start, int count, short* in) const
{
    const Submesh& s = m_submeshes[submesh];
    for (int i = 0; i < count; i++)
    {
        in[i] = s.indices[i + start];
    }
}

VertexArray* Mesh::GetVertexArray(int submesh)
{
    return m_submeshes[submesh].vertexArray;
}

IndexBuffer* Mesh::GetIndexBuffer(int submesh)
{
    return m_submeshes[submesh].indexBuffer;
}

}
