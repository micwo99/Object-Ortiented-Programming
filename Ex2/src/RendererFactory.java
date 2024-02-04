public class RendererFactory {
    private static final String NONE ="none";
    private static final String CONSOLE="console";
    public static Renderer buildRenderer(String type,int size) {
        if(type.equals(NONE)){
                return new VoidRenderer();
            }
        if(type.equals(CONSOLE)){
                return new ConsoleRenderer(size);
            }
        return null;
    }


}
