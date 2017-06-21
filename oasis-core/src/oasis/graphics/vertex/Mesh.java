package oasis.graphics.vertex;

import oasis.graphics.GraphicsDevice;
import oasis.graphics.Primitive;
import oasis.graphics.internal.NativeMesh;
import oasis.math.Vector3;
import oasis.math.Vector4;

public class Mesh {

    private NativeMesh impl;

    public Mesh(GraphicsDevice graphics) {
        impl = graphics.getResourceManager().createNativeMesh();
    }
    
    public NativeMesh getNativeMesh() { 
        return impl; 
    }

    public Primitive getPrimitive() {
        return impl.getPrimitive();
    }

    public BufferUsage getUsage() {
        return impl.getUsage();
    }

    public int[] getIndices() {
        return impl.getIndices();
    }

    public Vector3[] getPositions() {
        return impl.getPositions();
    }

    public Vector3[] getNormals() {
        return impl.getNormals();
    }

    public Vector4[] getColors() {
        return impl.getColors();
    }

    public void setPrimitive(Primitive primitive) {
        impl.setPrimitive(primitive);
    }

    public void setUsage(BufferUsage usage) {
        impl.setUsage(usage);
    }

    public void setIndices(int[] indices) {
        impl.setIndices(indices);
    }

    public void setPositions(Vector3[] positions) {
        impl.setPositions(positions);
    }

    public void setNormals(Vector3[] normals) {
        impl.setNormals(normals);
    }

    public void setColors(Vector4[] colors) {
        impl.setColors(colors);
    }

}
