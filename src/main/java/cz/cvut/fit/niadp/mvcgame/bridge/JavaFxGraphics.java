package cz.cvut.fit.niadp.mvcgame.bridge;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class JavaFxGraphics implements  IGameGraphicsImplementor {

    private final GraphicsContext gr;

    public JavaFxGraphics(GraphicsContext gr) {
        this.gr = gr;
    }

    @Override
    public void drawImage(String path, Position position) {
        this.gr.drawImage(new Image(path), position.getX(), position.getY());
    }

    @Override
    public void drawText(String text, Position position) {
        this.gr.fillText(text, position.getX(), position.getY());
    }

    @Override
    public void drawLine(Position beginPosition, Position endPosition) {
        this.gr.strokeLine(beginPosition.getX(), beginPosition.getY(), endPosition.getX(), endPosition.getY());
    }

    @Override
    public void clear() {
        this.gr.clearRect(0, 0, MvcGameConfig.MAX_X, MvcGameConfig.MAX_Y);
    }
}
