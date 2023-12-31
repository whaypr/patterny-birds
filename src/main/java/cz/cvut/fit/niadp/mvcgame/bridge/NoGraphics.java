package cz.cvut.fit.niadp.mvcgame.bridge;

import cz.cvut.fit.niadp.mvcgame.model.Position;

public class NoGraphics implements  IGameGraphicsImplementor {

    private static NoGraphics INSTANCE;

    private NoGraphics() {
    }

    public static NoGraphics getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NoGraphics();
        }

        return INSTANCE;
    }

    //----------------------------------

    @Override
    public void drawImage(String path, Position position) {
    }

    @Override
    public void drawText(String text, Position position) {
    }

    @Override
    public void drawLine(Position beginPosition, Position endPosition) {
    }

    @Override
    public void clear() {
    }
}
