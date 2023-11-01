package cz.cvut.fit.niadp.mvcgame.view.graphicscontext;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GraphicsContextJavaFXWrapper implements IGraphicsContext {

    private GraphicsContext graphicsContext;

    public GraphicsContextJavaFXWrapper(GraphicsContext gr) {
        graphicsContext = gr;
    }

    @Override
    public void clearRect(double x, double y, double w, double h) {
        graphicsContext.clearRect(x, y, w, h);
    }

    @Override
    public void drawImage(String imgPath, double x, double y) {
        graphicsContext.drawImage(new Image(imgPath), x, y);
    }
}
