package oasis.test;

import java.util.Arrays;

import oasis.math.Mathf;
import oasis.math.Matrix3;
import oasis.math.Matrix4;
import oasis.math.Quaternion;
import oasis.math.Vector2;
import oasis.math.Vector3;

public class MatrixTest {

    public static void main(String[] args) {
        Matrix4 m1 = Matrix4.translation(new Vector3(1f, 2f, 3f)); 
        Matrix4 m2 = Matrix4.scale(new Vector3(1f, 2f, 3f)); 
        
        System.out.println(Arrays.toString(m1.get(new float[16])));
        
        System.out.println(m1);
        System.out.println();
        System.out.println(m2);
        System.out.println();
        System.out.println(m2.multiply(m1));
        
        System.out.println("=========================");
        
        Vector3 v2 = new Vector3(4, 5, 8); 
        Quaternion q1 = Quaternion.axisAngle(new Vector3(1, 3, 4), Mathf.toRadians(45f)); 
        System.out.println(q1);
        System.out.println(q1.toMatrix4f());
        System.out.println(v2.rotate(q1));
        System.out.println(q1.toMatrix4f().multiply(v2, 1f));
        
        System.out.println("=========================");
        
        Matrix3 m3 = Matrix3.translation(new Vector2(2, 3)); 
        Vector2 v1 = new Vector2(); 
        
        System.out.println(m3);
        System.out.println(m3.multiply(v1, 1f));
    }
    
}
