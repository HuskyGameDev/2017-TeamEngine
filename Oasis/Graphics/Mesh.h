#pragma once

#include "Oasis/Oasis.h"
#include "Oasis/Graphics/Graphics.h"

#include <vector>

namespace Oasis
{

class VertexArray;
class IndexBuffer;
class VertexBuffer;

struct OASIS_API Submesh
{
    Submesh();

    bool update;
    VertexArray* vertexArray;
    IndexBuffer* indexBuffer;
    Primitive primitive;
    std::vector<short> indices;
};

class OASIS_API Mesh
{
public:
    Mesh();
    ~Mesh();

    void Release();

    void Clear();
    void Upload();

    bool CalculateNormals();
    bool CalculateTangents();

    bool HasNormals() const { return m_normals.size(); }
    bool HasTexCoords() const { return m_texCoords.size(); }
    bool HasTangents() const { return m_tangents.size(); }

    // attributes

    void SetPositions(int count, const Vector3* positions);
    void SetNormals(const Vector3* normals);
    void SetTexCoord(const Vector2* texCoords);
    void SetTangents(const Vector3* tangents);

    int GetVertexCount() const;
    void GetPositions(int start, int count, Vector3* in) const;
    void GetNormals(int start, int count, Vector3* in) const;
    void GetTexCoords(int start, int count, Vector2* in) const;
    void GetTangents(int start, int count, Vector3* in) const;

    VertexBuffer* GetVertexBuffer();

    // submeshes

    void SetSubmeshCount(int count);
    void SetIndices(int submesh, int count, const short* indices);

    int GetSubmeshCount() const;
    int GetIndexCount(int submesh) const;
    void GetIndices(int submesh, int start, int count, short* in) const;

    VertexArray* GetVertexArray(int submesh);
    IndexBuffer* GetIndexBuffer(int submesh);

private:
    void ClearAttributes();

    bool m_updateVertices;
    int m_vertexCount;
    std::vector<Vector3> m_positions;
    std::vector<Vector3> m_normals;
    std::vector<Vector2> m_texCoords;
    std::vector<Vector3> m_tangents;
    VertexBuffer* m_vertexBuffer;

    std::vector<Submesh> m_submeshes;
};

}
