#pragma once

#include "Oasis/Oasis.h"

#include <vector>

namespace Oasis
{

class Geometry;
class IndexBuffer;
class VertexBuffer;

class OASIS_API Submesh
{
public:
    Submesh();
    ~Submesh();

    Geometry* GetGeometry() { return m_geometry; }
    IndexBuffer* GetIndexBuffer() { return m_indexBuffer; }

private:
    Geometry* m_geometry;
    IndexBuffer* m_indexBuffer;
};

class OASIS_API Mesh
{
public:
    Mesh();
    ~Mesh();

    VertexBuffer* GetVertexBuffer() { return m_vertexBuffer; }

    int GetSubmeshCount() const { return m_submeshes.size(); }

    Submesh& GetSubmesh(int index) { return m_submeshes[index]; }

private:
    std::vector<Submesh> m_submeshes;
    VertexBuffer* m_vertexBuffer;
};

}
