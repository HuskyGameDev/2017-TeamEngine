package oasis.test;

import java.util.Arrays;

import oasis.math.Mathf;
import oasis.math.Matrix3f;
import oasis.math.Matrix4f;
import oasis.math.Quaternionf;
import oasis.math.Vector2f;
import oasis.math.Vector3f;

public class MatrixTest {

    public static void main(String[] args) {
        Matrix4f m1 = Matrix4f.translation(new Vector3f(1f, 2f, 3f)); 
        Matrix4f m2 = Matrix4f.scale(new Vector3f(1f, 2f, 3f)); 
        
        System.out.println(Arrays.toString(m1.get(new float[16])));
        
        System.out.println(m1);
        System.out.println();
        System.out.println(m2);
        System.out.println();
        System.out.println(m2.multiply(m1));
        
        System.out.println("=========================");
        
        Vector3f v2 = new Vector3f(4, 5, 8); 
        Quaternionf q1 = new Quaternionf(new Vector3f(1, 3, 4), Mathf.toRadians(45f)); 
        System.out.println(q1);
        System.out.println(q1.toMatrix4f());
        System.out.println(v2.rotate(q1));
        System.out.println(q1.toMatrix4f().multiply(v2, 1f));
        
        System.out.println("=========================");
        
        Matrix3f m3 = Matrix3f.translation(new Vector2f(2, 3)); 
        Vector2f v1 = new Vector2f(); 
        
        System.out.println(m3);
        System.out.println(m3.multiply(v1, 1f));
    }
    
}
