//package nhamil.oasis.core.test;
//
//import nhamil.oasis.audio.AudioSystem;
//import nhamil.oasis.core.Engine;
//import nhamil.oasis.core.GameLogger;
//import nhamil.oasis.graphics.GraphicsSystem;
//import nhamil.oasis.input.InputSystem;
//
//public class TestEngine extends Engine {
//
//    private static final GameLogger log = new GameLogger(TestEngine.class);
//    
//    private TestGraphics graphics = new TestGraphics();
//    private TestInput input = new TestInput();
//    private TestAudio audio = new TestAudio();
//    
//    @Override
//    public GraphicsSystem getGraphics() {
//        return graphics;
//    }
//
//    @Override
//    public InputSystem getInput() {
//        return input;
//    }
//
//    @Override
//    public AudioSystem getAudio() {
//        return audio;
//    }
//
//    @Override
//    protected void preInit() {
//        log.info("Initializing TestEngine");
//    }
//
//    @Override
//    protected void postExit() {
//        log.info("Shutting down TestEngine");
//    }
//
//}
