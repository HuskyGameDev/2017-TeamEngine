package oasis.core;

import oasis.core.jogl.JoglEngine;

public class Config {

    public Class<? extends Engine> engine = JoglEngine.class;
    public float fps = 60.0f;
    public float ups = 60.0f;
    
}
