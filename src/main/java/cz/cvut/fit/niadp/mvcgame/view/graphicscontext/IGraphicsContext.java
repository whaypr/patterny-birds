package cz.cvut.fit.niadp.mvcgame.view.graphicscontext;

import javafx.scene.image.Image;

public interface IGraphicsContext {
    public void clearRect(double x, double y, double w, double h);
    public void drawImage(Image img, double x, double y);
}
