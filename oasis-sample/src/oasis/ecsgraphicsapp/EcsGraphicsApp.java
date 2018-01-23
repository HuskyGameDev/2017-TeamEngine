package oasis.ecsgraphicsapp;

import oasis.core.Application;
import oasis.core.BackendType;
import oasis.core.Config;
import oasis.core.Oasis;
import oasis.entity.EntityManager;

public class EcsGraphicsApp implements Application {

    private EntityManager entityManager; 
    
    public static void main(String[] args) {
        Config conf = new Config(); 
        conf.backend = BackendType.AUTO; 
        
        Oasis.start(conf, new EcsGraphicsApp());
    }
    
    @Override
    public void init() {
        entityManager = new EntityManager(); 
    }

    @Override
    public void update(float dt) {
        entityManager.update(dt); 
    }

    @Override
    public void render() {
        entityManager.render(); 
    }

    @Override
    public void exit() {
        
    }

    @Override
    public boolean closeAttempt() {
        return true; 
    }

}
