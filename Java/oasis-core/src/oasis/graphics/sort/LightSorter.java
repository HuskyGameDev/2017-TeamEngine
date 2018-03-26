package oasis.graphics.sort;

import oasis.graphics.RenderData;
import oasis.graphics.RenderLightList;

public interface LightSorter {

    void sort(RenderData mesh, RenderLightList in, RenderLightList out); 
    
}
