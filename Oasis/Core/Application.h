#pragma once

#include "Oasis/Oasis.h"
#include "Oasis/Core/Config.h"
#include "Oasis/Core/Engine.h"

namespace Oasis
{

class OASIS_API Application
{
public:
    Application();
    virtual ~Application() {}

    int Start();
    void Stop();

    float GetFps() const { return m_fps; }
    float GetUps() const { return m_ups; }

    virtual Config GetConfig() const { return Config(); }

    virtual void Init() = 0;
    virtual void Update(float dt) = 0;
    virtual void Render() = 0;
    virtual void Exit() = 0;

private:
    bool m_running;
    float m_fps, m_ups;
};

}
