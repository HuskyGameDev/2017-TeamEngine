package oasis.core;

import oasis.core.jogl.Jogl3Engine;

public class Config {

    public Class<? extends Engine> engine = Jogl3Engine.class;
    public float fps = 60.0f;
    public float ups = 60.0f;
    
}
