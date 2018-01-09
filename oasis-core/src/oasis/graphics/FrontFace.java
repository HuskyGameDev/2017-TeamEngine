package oasis.graphics;
/**
     * Polygon face winding, culls the opposite winding
     * 
     * @author Nicholas Hamilton
     *
     */
    public enum FrontFace {
        
        /**
         * No culling 
         */
        BOTH, 
        
        /**
         * Show faces that are in clockwise order 
         */
        CW, 
        
        /**
         * Show faces that are in counter-clockwise order 
         */
        CCW; 
        
    }