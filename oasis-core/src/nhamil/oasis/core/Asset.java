package nhamil.oasis.core;

public abstract class Asset {

    public static final int ID_INVALID = -1;
    
    protected boolean needsUpdate = true;
    protected int id = ID_INVALID;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public boolean needsUpdate() {
        return needsUpdate;
    }
    
    public void setNeedsUpdate() {
        needsUpdate = true;
    }
    
    public void setUpdated() {
        needsUpdate = false;
    }
    
    public boolean hasInvalidId() {
        return id == ID_INVALID;
    }
    
}
