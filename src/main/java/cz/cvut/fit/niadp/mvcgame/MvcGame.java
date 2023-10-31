package cz.cvut.fit.niadp.mvcgame;

import java.util.List;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.Position;
// in the future, use Bridge to remove this dependency
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MvcGame {
    private Position logoPos;

    public void init() {
        logoPos = new Position( ((MvcGameConfig.MAX_X/2)-128), ((MvcGameConfig.MAX_Y/2)-128) );
    }

    public void processPressedKeys(List<String> pressedKeysCodes) {
        for(String code : pressedKeysCodes) {
            switch(code) {
                case "UP":
                    logoPos.setY(logoPos.getY() - 10);
                    break;
                case "DOWN":
                    logoPos.setY(logoPos.getY() + 10);
                    break;
                case "LEFT":
                    logoPos.setX(logoPos.getX() - 10);
                    break;
                case "RIGHT":
                    logoPos.setX(logoPos.getX() + 10);
                    break;
                case "ESCAPE":
                    System.exit(0);
                    break;
                default: 
                    //nothing
            }
        }
    }

    public void update() {
        // nothing yet
    }

    public void render(GraphicsContext gr) {
        gr.drawImage(new Image("icons/fit-icon.png"), logoPos.getX(), logoPos.getY());
    }

    public String getWindowTitle() {
        return "The NI-ADP MvcGame";
    }

    public int getWindowWidth() {
        return MvcGameConfig.MAX_X;
    }

    public int getWindowHeight() {
        return  MvcGameConfig.MAX_Y;
    }
}