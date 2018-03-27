#include <Oasis/Oasis.h>
#include <Oasis/Core/Application.h>
#include <Oasis/Core/Window.h>
#include <Oasis/Graphics/Graphics.h>

#include <iostream>

using namespace Oasis;
using namespace std;

class TestApp : public Application
{
public:
    ~TestApp() {}

    void Init();
    void Update(float dt);
    void Render();
    void Exit();

private:
    Geometry* geom;
    Vector3 pos;
    float angle;
};

void TestApp::Init()
{
    Graphics* g = Engine::GetGraphics();

    geom = g->CreateGeometry();

    VertexBuffer* vb = g->CreateVertexBuffer(6, VertexFormat::POSITION);

    float verts[] =
    {
        -0.5, -0.5, 0,
         0.5, -0.5, 0,
         0.5,  0.5, 0,

        -0.5, -0.5, 0,
         0.5,  0.5, 0,
        -0.5,  0.5, 0,
    };

    vb->SetData(6, verts);
    geom->SetVertexBuffer(vb);
}

void TestApp::Update(float dt)
{
    pos.z -= 0.5 * dt;

    angle += 180 * dt * OASIS_TO_RAD;
}

void TestApp::Render()
{
    Graphics* g = Engine::GetGraphics();

    g->SetClearColor({0.6, 0.7, 0.9, 1.0});
    g->Clear();

    cout << pos << " " << angle << endl;

    g->SetUniform("u_Color", Vector3(1, 1, 0));
    g->SetUniform("oa_Model", Matrix4::Translation(pos) * Matrix4::RotationY(angle));
    g->SetUniform("oa_View", Matrix4::Identity());
    g->SetUniform("oa_Proj", Matrix4::Perspective(70 * OASIS_TO_RAD, 1.3333, 0.1, 100.0));

    g->SetGeometry(geom);
    g->DrawArrays(PRIMITIVE_TRIANGLE_LIST, 0, 2);
}

void TestApp::Exit()
{
    geom->GetVertexBuffer(0)->Release();
    geom->Release();

    delete geom->GetVertexBuffer(0);
    delete geom;
}

OASIS_MAIN(TestApp)
