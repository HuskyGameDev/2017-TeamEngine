package oasis.test;

import oasis.math.Mathf;
import oasis.math.Quaternionf;
import oasis.math.Vector3f;

public class QuaternionTest {

    public static void main(String[] args) {
        Quaternionf q1 = new Quaternionf(new Vector3f(0, 1, 0), Mathf.toRadians(45f)); 
        Quaternionf q2 = new Quaternionf(new Vector3f(0, 0, 1), Mathf.toRadians(45f)); 
        Quaternionf q3 = q2.multiply(q1).normalizeSelf(); 
        Vector3f v1 = new Vector3f(1, 0, 0); 
        Vector3f v2 = new Vector3f(0, 1, 0); 
        
        System.out.println(v1.cross(v2));
        
        System.out.println(q1);
        System.out.println(q2);
        System.out.println(q3);
        System.out.println(q1.multiply(q1.conjugate()));
        
        System.out.println(v1);
        System.out.println(v1.rotate(q1));
        System.out.println(v1.rotate(q2));
        System.out.println(v1.rotate(q3));
    }
    
}
