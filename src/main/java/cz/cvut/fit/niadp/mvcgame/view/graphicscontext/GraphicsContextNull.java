package cz.cvut.fit.niadp.mvcgame.view.graphicscontext;

public final class GraphicsContextNull implements IGraphicsContext {

    private static GraphicsContextNull INSTANCE;

    private GraphicsContextNull() {
    }

    public static GraphicsContextNull getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GraphicsContextNull();
        }

        return INSTANCE;
    }

    public void clearRect(double x, double y, double w, double h) {
    }

    public void drawImage(String imgPath, double x, double y) {

    }
}
