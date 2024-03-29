package cz.cvut.fit.niadp.mvcgame.config;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.Vector;

public class MvcGameConfig {

    /* CONSTANTS */
    public static final String GAME_TITLE = "The NI-ADP MvcGame";
    public static final int MAX_X = 1474; //1920;
    public static final int MAX_Y = 829; //1080;
    public static final int MOVE_STEP = 10;
    public static final int CANNON_POS_X = 200;
    public static final int CANNON_POS_Y = MAX_Y / 2;
    public static final double ANGLE_STEP = Math.PI / 18;
    public static final int POWER_STEP = 1;
    public static final int INIT_POWER = 10;
    public static final double INIT_ANGLE = 0;
    public static final double GRAVITY = 9.81;
    public static final int MAX_POWER = 50;
    public static final int MIN_POWER = 1;
    public static final long DEFAULT_MISSILE_LIFETIME = 5_000;
    public static final int DYNAMIC_SHOOTING_MODE_ADD_STEP = 1;
    public static final int DYNAMIC_SHOOTING_MODE_REMOVE_STEP = 1;
    public static final int DYNAMIC_SHOOTING_MODE_DEFAULT_NUMBER_OF_MISSILES = 3;
    public static final boolean MISSILES_DEFAULT_WALL_PIERCING = false;
    public static final boolean MISSILES_DEFAULT_ENEMY_PIERCING = false;
    public static final int POINTS_FOR_HIT = 1;
    public static final Vector CANNON_HITBOX = new Vector(24,68);
    public static final Vector MISSILE_HITBOX = new Vector(29,28);
    public static final Vector ENEMY_HITBOX = new Vector(29,25);
    public static final Vector WALL_HITBOX = new Vector(32,32);

    /* CONTROLS */
    public static final String UP_KEY = "UP";
    public static final String DOWN_KEY = "DOWN";
    public static final String EXIT_KEY = "ESCAPE";
    public static final String SHOOT_KEY = "SPACE";
    public static final String AIM_UP_KEY = "LEFT";
    public static final String AIM_DOWN_KEY = "RIGHT";
    public static final String POWER_UP_KEY = "A";
    public static final String POWER_DOWN_KEY = "Y";
    public static final String MOVING_STRATEGY_KEY = "D";
    public static final String DYNAMIC_SHOOTING_MODE_ADD_MISSILE_KEY = "S";
    public static final String DYNAMIC_SHOOTING_MODE_REMOVE_MISSILE_KEY = "X";
    public static final String TOGGLE_MISSILES_WALL_PIERCING_KEY = "F";
    public static final String TOGGLE_MISSILES_ENEMY_PIERCING_KEY = "V";
    public static final String SHOOTING_MODE_KEY = "C";
    public static final String UNDO_LAST_COMMAND_KEY = "R";

    /* RESOURCES */
    public static final String BACKGROUND_IMAGE_RESOURCE = "images/back.jpg";
    public static final String CANNON_IMAGE_RESOURCE = "images/cannon.png";
    public static final String MISSILE_IMAGE_RESOURCE = "images/missile.png";
    public static final String MISSILE_LAUNCH_SOUND_RESOURCE = "sounds/missileLaunch.wav";
    public static final String MISSILE_WALL_HIT_SOUND_RESOURCE = "sounds/wallHit.wav";
    public static final String MISSILE_ENEMY_HIT_SOUND_RESOURCE = "sounds/enemyHit.wav";
    public static final String ENEMY_BASIC_IMAGE_RESOURCE = "images/enemy_basic.png";
    public static final String ENEMY_WITH_HELMET_IMAGE_RESOURCE = "images/enemy_with_helmet.png";
    public static final String WALL_IMAGE_RESOURCE = "images/wall.png";
}