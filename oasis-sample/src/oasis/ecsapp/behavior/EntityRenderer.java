package oasis.ecsapp.behavior;

import oasis.ecsapp.component.Transform;
import oasis.entity.ComponentId;
import oasis.entity.IterateEntityBehavior;
import oasis.entity.Entity;
import oasis.entity.EntityManager;

public class EntityRenderer extends IterateEntityBehavior {

    private ComponentId<Transform> transformId; 
    
    private char[][] map = new char[32][8]; 
    
    public EntityRenderer() {
        super(Integer.MAX_VALUE, Transform.class); 
    }
    
    @Override
    public void updateIds(EntityManager em) {
        transformId = em.getComponentId(Transform.class); 
    }
    
    @Override
    public void preRender() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = ' '; 
            }
        }
    } 
    
    @Override
    public void render(Entity e) {
        Transform t = e.get(transformId); 
        
        int x = (int) t.x; 
        int y = (int) t.y; 
        
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            map[x][y] = 'X'; 
        }
    } 
    
    @Override
    public void postRender() {
        System.out.print("+");
        for (int i = 0; i < map.length; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        for (int j = 0; j < map[0].length; j++) {
            System.out.print("|");
            for (int i = 0; i < map.length; i++) {
                System.out.print(map[i][j]); 
            }
            System.out.println("|"); 
        }
        System.out.print("+");
        for (int i = 0; i < map.length; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        System.out.println(); 
    }
    
}
