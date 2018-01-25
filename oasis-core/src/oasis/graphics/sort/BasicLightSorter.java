package oasis.graphics.sort;

import oasis.graphics.RenderData;
import oasis.graphics.RenderLightList;

public class BasicLightSorter implements LightSorter {

    @Override
    public void sort(RenderData mesh, RenderLightList in, RenderLightList out) {
        out.setAmbient(in.getAmbient()); 
        for (int i = 0; i < in.getLightCount(); i++) {
            out.add(in.get(i)); 
        }
    }

}
