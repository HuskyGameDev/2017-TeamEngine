package oasis.entity;

public final class Entity {

    private EntityManager manager; 
    private int id; 
    
    protected Entity(EntityManager manager, int id) {
        this.manager = manager; 
        this.id = id; 
    }
    
    public EntityManager getManager() {
        return manager; 
    }
    
    public int getId() {
        return id; 
    }
    
    public <T extends Component> T add(Class<T> type) {
        return manager.addComponent(id, manager.getComponentId(type)); 
    }
    
    public <T extends Component> T add(ComponentId<T> type) {
        return manager.addComponent(id, type); 
    }
    
    public <T extends Component> T get(Class<T> type) {
        return manager.getComponent(id, manager.getComponentId(type)); 
    }

    public <T extends Component> T get(ComponentId<T> type) {
        return manager.getComponent(id, type); 
    }
    
    public <T extends Component> boolean has(Class<T> type) {
        return manager.hasComponent(id, manager.getComponentId(type)); 
    }

    public <T extends Component> boolean has(ComponentId<T> type) {
        return manager.hasComponent(id, type); 
    }
    
    public <T extends Component> boolean remove(Class<T> type) {
        return manager.removeComponent(id, manager.getComponentId(type)); 
    }

    public <T extends Component> boolean remove(ComponentId<T> type) {
        return manager.removeComponent(id, type); 
    }
    
}
