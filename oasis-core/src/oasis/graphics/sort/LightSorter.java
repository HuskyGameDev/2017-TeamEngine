package oasis.graphics.sort;

import oasis.graphics.RenderLightList;
import oasis.graphics.RenderData;

public interface LightSorter {

    void sort(RenderData mesh, RenderLightList in, RenderLightList out); 
    
}
