package cz.cvut.fit.niadp.mvcgame.view.graphicscontext;

public interface IGraphicsContext {
    public void clearRect(double x, double y, double w, double h);
    public void drawImage(String imgPath, double x, double y);
}
