package oasis.graphics.sort;

import oasis.graphics.LightList;
import oasis.graphics.RenderData;

public class BasicLightSorter implements LightSorter {

    @Override
    public void sort(RenderData mesh, LightList in, LightList out) {
        out.setAmbient(in.getAmbient()); 
        for (int i = 0; i < in.getLightCount(); i++) {
            out.add(in.get(i)); 
        }
    }

}
