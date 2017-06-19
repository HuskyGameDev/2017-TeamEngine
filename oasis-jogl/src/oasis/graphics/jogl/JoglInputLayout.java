package oasis.graphics.jogl;

import java.util.ArrayList;
import java.util.List;

import oasis.graphics.Attribute;

public class JoglInputLayout {

    public List<Attribute> attributes; 
    public List<Integer> indices; 
    public List<Integer> location; 
    
    public JoglInputLayout() { 
        attributes = new ArrayList<>(); 
        indices = new ArrayList<>(); 
        location = new ArrayList<>(); 
    }
    
}
