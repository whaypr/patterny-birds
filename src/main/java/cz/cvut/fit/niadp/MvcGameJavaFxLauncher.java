package cz.cvut.fit.niadp;

import cz.cvut.fit.niadp.mvcgame.bridge.GameGraphics;
import cz.cvut.fit.niadp.mvcgame.bridge.IGameGraphics;
import cz.cvut.fit.niadp.mvcgame.bridge.JavaFxGraphics;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;

import cz.cvut.fit.niadp.mvcgame.MvcGame;

public class MvcGameJavaFxLauncher extends Application {

    private static final MvcGame theMvcGame = new MvcGame();

    @Override
    public void init() {
        theMvcGame.init();
    }

    @Override
    public void start(Stage stage) {
        String winTitle = theMvcGame.getWindowTitle();
        int winWidth = theMvcGame.getWindowWidth();
        int winHeight = theMvcGame.getWindowHeight();

        stage.setTitle(winTitle);
        Group root = new Group();
        Scene theScene = new Scene(root);
        stage.setScene(theScene);
        stage.setFullScreen(true);

        ImageView backgroundView = new ImageView(new Image(MvcGameConfig.BACKGROUND_IMAGE_RESOURCE));
        backgroundView.setFitWidth(winWidth);
        backgroundView.setFitHeight(winHeight);
        root.getChildren().add(backgroundView);

        Canvas canvas = new Canvas(winWidth, winHeight);
        root.getChildren().add( canvas );

        IGameGraphics gameGraphics = new GameGraphics(new JavaFxGraphics(canvas.getGraphicsContext2D()));

        ArrayList<String> pressedKeysCodes = new ArrayList<>();
        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    // only add once... prevent duplicates
                    if (!pressedKeysCodes.contains(code))
                        pressedKeysCodes.add(code);
                }
        );
        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    pressedKeysCodes.remove( code );
                }
        );
        // the game-loop
        theMvcGame.setGraphicsContext(gameGraphics);
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                theMvcGame.processPressedKeys(pressedKeysCodes);
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}