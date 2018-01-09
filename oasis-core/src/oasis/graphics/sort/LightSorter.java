package oasis.graphics.sort;

import oasis.graphics.LightList;
import oasis.graphics.RenderData;

public interface LightSorter {

    void sort(RenderData mesh, LightList in, LightList out); 
    
}
