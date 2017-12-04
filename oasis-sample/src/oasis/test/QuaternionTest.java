package oasis.test;

import oasis.math.Mathf;
import oasis.math.Quaternion;
import oasis.math.Vector3;

public class QuaternionTest {

    public static void main(String[] args) {
        Quaternion q1 = Quaternion.axisAngle(new Vector3(0, 1, 0), Mathf.toRadians(45f)); 
        Quaternion q2 = Quaternion.axisAngle(new Vector3(0, 0, 1), Mathf.toRadians(45f)); 
        Quaternion q3 = q2.multiply(q1).normalizeSelf(); 
        Vector3 v1 = new Vector3(1, 0, 0); 
        Vector3 v2 = new Vector3(0, 1, 0); 
        
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
