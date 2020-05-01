package flappybird;

import constants.MoFunctions;

import java.awt.*;
import java.util.ArrayList;

public class MoWall {


    //130
    public static final double MIN_DISTANCE_AFTER_WALL = 300;
    public static final double MAX_DISTANCE_AFTER_WALL = 170;

    //0.65
    public static final double MAX_HEIGHT_SCALE = 0.75;
    public static final double MIN_HEIGHT_SCALE = 0.20;
    public static final double CORRECT_HEIGHT_SCALE = 1 - MAX_HEIGHT_SCALE;
    public static final double MAX_WIDTH = 80;
    public static final double MIN_WIDTH = 50;
    public static final int MAX_WALLS = 2;
    public static final int BASE_X_POSITION = 2 * MoBird.INITIAL_X_POSITION;
    public static final int X_VEL = MoConstants.SPEED_Y_BIRD_JUMP + 1;
    public static final int Y_VEL = 0;

    // contains a set of walls


    private ArrayList<MoAnimatableObject> walls;
    private MoAnimatableObject safeGap;

    public MoWall(MoWall previousWall) {
        this.walls = new ArrayList<>();
        this.initWalls(previousWall);
    }





    // MODIFIES: this
    // EFFECT: initializes the walls
    // based on the previous walls x-position
    private void initWalls(MoWall previousWall) {
        double firstHeightScale = MoFunctions.getRandom(MIN_HEIGHT_SCALE,MAX_HEIGHT_SCALE);
        double secondHeightScale = MAX_HEIGHT_SCALE - firstHeightScale;
        double previousPositionX = previousWall == null ? BASE_X_POSITION : previousWall.getPositionX();
        double previousWallWidth = previousWall == null ? 0 : previousWall.getWalls().get(0).getWidth();
        double width = MoFunctions.getRandom(MIN_WIDTH,MAX_WIDTH);
        for (int i = 0; i < MAX_WALLS; i++) {
            int height =  (i == 0) ? (int) (firstHeightScale * MoConstants.GROUND_LEVEL_Y) :
                    (int) (secondHeightScale * MoConstants.GROUND_LEVEL_Y);
            MoAnimatableObject moAnimatableObject = new MoAnimatableObject(
                    (int) (previousPositionX + MIN_DISTANCE_AFTER_WALL + previousWallWidth),
                     i == 0 ? (int) (MoConstants.GROUND_LEVEL_Y - height)
                            : 0,
                    (int) width, height, X_VEL, Y_VEL,
                    0
            );
            this.walls.add(moAnimatableObject);
        }

        this.initSafeGap();
    }


    private void initSafeGap() {
        MoAnimatableObject topWall = this.walls.get(1);


        this.safeGap = new MoAnimatableObject(
                topWall.getPositionX(),
                topWall.getPositionY() + topWall.getHeight(),
                this.walls.get(0).getWidth(),
                (int) (CORRECT_HEIGHT_SCALE * MoConstants.GROUND_LEVEL_Y),
                X_VEL,
                Y_VEL,
                0
        );
    }


    public ArrayList<MoAnimatableObject> getWalls() {
        return walls;
    }

    public void drawWall(Graphics g) {
        g.setColor(Color.lightGray);
        for (MoAnimatableObject wall: this.walls) {
            g.fillRect(
                    wall.getPositionX(),
                    wall.getPositionY(),
                    wall.getWidth(),
                    wall.getHeight()
            );
        }
        g.setColor(Color.RED);
        //int scale = 10;
//        float[][] corners = this.safeGap.getCorners();
//        for (int i = 0;i  < corners.length;i++) {
//            g.fillRect((int) corners[i][0],(int) corners[i][1],scale,scale);
//        }


        //g.fillRect(safeGap.getPositionX(),safeGap.getPositionY(),safeGap.getWidth(),safeGap.getHeight());
//        g.fillRect(safeGap.getPositionX(),safeGap.getPositionY(),scale,scale);
//        g.fillRect(safeGap.getPositionX()  +safeGap.getWidth(),safeGap.getPositionY(),scale,scale);
//        g.fillRect(safeGap.getPositionX(),safeGap.getPositionY() + safeGap.getHeight(),scale,scale);
//        g.fillRect(safeGap.getPositionX()  + safeGap.getWidth(),safeGap.getPositionY()  +safeGap.getHeight(),scale,scale);
    }

    // REQUIRES: walls should not be empty
    public double getPositionX() {
        return this.walls.get(0).getPositionX();
    }

    // REQUIRES: !walls.isEmpty()
    public void moveWalls() {
        if (this.isActive()) {
            for (MoAnimatableObject wall: this.walls) {
                wall.move();
            }
            this.safeGap.move();
        }

    }

    public boolean isActive() {
        return this.walls.get(0).isActive();
    }

    public int getXPosition() {
        return (int) this.walls.get(0).getPositionX();
    }

    public MoAnimatableObject getSafeGap() {
        return safeGap;
    }
}
